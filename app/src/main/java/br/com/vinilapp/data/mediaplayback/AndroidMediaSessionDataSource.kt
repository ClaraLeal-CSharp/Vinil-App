package br.com.vinilapp.data.mediaplayback

import android.content.ComponentName
import android.content.Context
import android.graphics.Bitmap
import android.media.MediaMetadata
import android.media.session.MediaController
import android.media.session.MediaSession
import android.media.session.MediaSessionManager
import android.media.session.PlaybackState
import android.os.Build
import android.os.SystemClock
import android.provider.Settings
import br.com.vinilapp.domain.model.NowPlayingState
import br.com.vinilapp.domain.model.PlaybackCommand
import br.com.vinilapp.service.notification.NowPlayingNotificationListenerService
import dagger.hilt.android.qualifiers.ApplicationContext
import java.util.concurrent.ConcurrentHashMap
import javax.inject.Inject
import javax.inject.Singleton
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.onStart

@Singleton
class AndroidMediaSessionDataSource @Inject constructor(
    @ApplicationContext private val context: Context,
    private val mediaSessionManager: MediaSessionManager
) : MediaSessionDataSource {
    private val notificationTokens = MutableStateFlow<Map<String, MediaSession.Token>>(emptyMap())
    private val notificationSnapshots = MutableStateFlow<List<NotificationMediaSnapshot>>(emptyList())
    private val refreshRequests = MutableStateFlow(0)
    private val appNames = ConcurrentHashMap<String, String>()

    override fun observeNowPlaying(): Flow<NowPlayingState> {
        val activeSessions = observeActiveSessions().onStart { emit(loadActiveSessions()) }
        val ticks = playbackTicker()

        return combine(
            activeSessions,
            notificationTokens,
            notificationSnapshots,
            refreshRequests,
            ticks
        ) { sessions, tokens, snapshots, _, _ ->
            if (!context.hasNotificationListenerAccess()) {
                return@combine NowPlayingState.PermissionRequired
            }

            val notificationControllers = tokens.values.mapNotNull(::controllerForToken)
            val controllers = (sessions + notificationControllers).distinctBy { it.sessionToken }

            controllers
                .sortedWith(controllerComparator())
                .firstNotNullOfOrNull(::mapController)
                ?: snapshots.firstNotNullOfOrNull { snapshot -> snapshot.toNowPlayingState(context) }
                ?: NowPlayingState.Unavailable
        }.distinctUntilChanged()
    }

    override fun sendPlaybackCommand(command: PlaybackCommand) {
        if (!context.hasNotificationListenerAccess()) {
            return
        }

        val controller = activeControllers().firstOrNull() ?: return
        val controls = controller.transportControls

        runCatching {
            when (command) {
                PlaybackCommand.Previous -> controls.skipToPrevious()
                PlaybackCommand.PlayPause -> {
                    if (controller.playbackState?.state == PlaybackState.STATE_PLAYING) {
                        controls.pause()
                    } else {
                        controls.play()
                    }
                }
                PlaybackCommand.Next -> controls.skipToNext()
            }
        }
        refresh()
    }

    fun refreshFromNotificationListener(activeNotifications: List<NotificationMediaSnapshot>) {
        notificationTokens.value = activeNotifications
            .mapNotNull { snapshot -> snapshot.sessionToken }
            .associateBy { token -> token.toString() }
        notificationSnapshots.value = activeNotifications
        refreshRequests.value += 1
    }

    fun refresh() {
        refreshRequests.value += 1
    }

    private fun observeActiveSessions(): Flow<List<MediaController>> = callbackFlow {
        val componentName = ComponentName(context, NowPlayingNotificationListenerService::class.java)
        val observedControllers = mutableMapOf<MediaSession.Token, ObservedMediaController>()

        fun updateControllers(controllers: List<MediaController>) {
            val activeTokens = controllers.map { controller -> controller.sessionToken }.toSet()
            val inactiveTokens = observedControllers.keys.filter { token -> token !in activeTokens }

            inactiveTokens.forEach { token ->
                observedControllers.remove(token)?.let { observedController ->
                    observedController.controller.unregisterCallback(observedController.callback)
                }
            }

            controllers.forEach { controller ->
                if (controller.sessionToken !in observedControllers) {
                    val callback = object : MediaController.Callback() {
                        override fun onMetadataChanged(metadata: MediaMetadata?) {
                            trySend(loadActiveSessions())
                        }

                        override fun onPlaybackStateChanged(state: PlaybackState?) {
                            trySend(loadActiveSessions())
                        }
                    }
                    controller.registerCallback(callback)
                    observedControllers[controller.sessionToken] = ObservedMediaController(
                        controller = controller,
                        callback = callback
                    )
                }
            }

            trySend(controllers)
        }

        val listener = MediaSessionManager.OnActiveSessionsChangedListener { controllers ->
            updateControllers(controllers.orEmpty())
        }
        var isRegistered = false

        updateControllers(loadActiveSessions())
        runCatching {
            mediaSessionManager.addOnActiveSessionsChangedListener(listener, componentName)
            isRegistered = true
        }

        awaitClose {
            observedControllers.values.forEach { observedController ->
                observedController.controller.unregisterCallback(observedController.callback)
            }
            observedControllers.clear()
            if (isRegistered) {
                mediaSessionManager.removeOnActiveSessionsChangedListener(listener)
            }
        }
    }

    private fun loadActiveSessions(): List<MediaController> {
        val componentName = ComponentName(context, NowPlayingNotificationListenerService::class.java)

        return runCatching {
            mediaSessionManager.getActiveSessions(componentName)
        }.getOrDefault(emptyList())
    }

    private fun controllerForToken(token: MediaSession.Token): MediaController? = runCatching {
        MediaController(context, token)
    }.getOrNull()

    private fun activeControllers(): List<MediaController> {
        val notificationControllers = notificationTokens.value.values.mapNotNull(::controllerForToken)

        return (loadActiveSessions() + notificationControllers)
            .distinctBy { controller -> controller.sessionToken }
            .sortedWith(controllerComparator())
    }

    private fun controllerComparator(): Comparator<MediaController> = compareByDescending<MediaController> {
        it.playbackState?.state == PlaybackState.STATE_PLAYING
    }.thenByDescending {
        it.metadata?.hasDisplayableMedia() == true
    }.thenByDescending {
        it.playbackState?.lastPositionUpdateTime ?: 0L
    }

    private fun mapController(controller: MediaController): NowPlayingState.Available? {
        val metadata = controller.metadata ?: return null
        val title = metadata.text(MediaMetadata.METADATA_KEY_TITLE)
            .ifBlank { metadata.text(MediaMetadata.METADATA_KEY_DISPLAY_TITLE) }
        val artist = metadata.text(MediaMetadata.METADATA_KEY_ARTIST)
            .ifBlank { metadata.text(MediaMetadata.METADATA_KEY_ALBUM_ARTIST) }
            .ifBlank { metadata.text(MediaMetadata.METADATA_KEY_DISPLAY_SUBTITLE) }
        val album = metadata.text(MediaMetadata.METADATA_KEY_ALBUM)
        val playbackState = controller.playbackState
        val durationMillis = metadata.long(MediaMetadata.METADATA_KEY_DURATION).coerceAtLeast(0L)
        val positionMillis = playbackState.currentPositionMillis(durationMillis)
        val packageName = controller.packageName.orEmpty()

        if (title.isBlank() && artist.isBlank() && album.isBlank()) {
            return null
        }

        return NowPlayingState.Available(
            title = title,
            artist = artist,
            album = album,
            durationMillis = durationMillis,
            positionMillis = positionMillis,
            albumArt = metadata.albumArt(),
            sourcePackageName = packageName,
            sourceAppName = appName(packageName),
            isPlaying = playbackState?.state == PlaybackState.STATE_PLAYING
        )
    }

    private fun playbackTicker(): Flow<Long> = flow {
        while (true) {
            emit(SystemClock.elapsedRealtime())
            delay(POSITION_REFRESH_INTERVAL_MILLIS)
        }
    }

    private fun appName(packageName: String): String = appNames.getOrPut(packageName) {
        context.appName(packageName)
    }
}

private data class ObservedMediaController(
    val controller: MediaController,
    val callback: MediaController.Callback
)

private fun Context.hasNotificationListenerAccess(): Boolean {
    val enabledListeners = Settings.Secure.getString(
        contentResolver,
        ENABLED_NOTIFICATION_LISTENERS_SETTING
    ).orEmpty()
    val listenerComponent = ComponentName(this, NowPlayingNotificationListenerService::class.java)
    val flattenedListener = listenerComponent.flattenToString()

    return enabledListeners
        .split(ENABLED_NOTIFICATION_LISTENERS_SEPARATOR)
        .any { enabledComponent ->
            enabledComponent.equals(flattenedListener, ignoreCase = true) ||
                enabledComponent.startsWith("$packageName/", ignoreCase = true)
        }
}

private fun MediaMetadata.text(key: String): String = getText(key)?.toString().orEmpty()

private fun MediaMetadata.long(key: String): Long = getLong(key)

private fun MediaMetadata.albumArt(): Bitmap? = getBitmap(MediaMetadata.METADATA_KEY_ALBUM_ART)
    ?: getBitmap(MediaMetadata.METADATA_KEY_ART)
    ?: getBitmap(MediaMetadata.METADATA_KEY_DISPLAY_ICON)
    ?: description?.iconBitmap

private fun MediaMetadata.hasDisplayableMedia(): Boolean = text(MediaMetadata.METADATA_KEY_TITLE).isNotBlank() ||
    text(MediaMetadata.METADATA_KEY_DISPLAY_TITLE).isNotBlank() ||
    text(MediaMetadata.METADATA_KEY_ARTIST).isNotBlank()

private fun PlaybackState?.currentPositionMillis(durationMillis: Long): Long {
    if (this == null) {
        return 0L
    }

    val basePosition = position.coerceAtLeast(0L)
    val position = if (state == PlaybackState.STATE_PLAYING) {
        val elapsed = (SystemClock.elapsedRealtime() - lastPositionUpdateTime).coerceAtLeast(0L)
        basePosition + (elapsed * playbackSpeed).toLong()
    } else {
        basePosition
    }

    return if (durationMillis > 0L) {
        position.coerceIn(0L, durationMillis)
    } else {
        position
    }
}

data class NotificationMediaSnapshot(
    val packageName: String,
    val title: String,
    val artist: String,
    val album: String,
    val albumArt: Bitmap?,
    val sessionToken: MediaSession.Token?,
    val postedAtMillis: Long
)

private fun NotificationMediaSnapshot.toNowPlayingState(context: Context): NowPlayingState.Available? {
    if (title.isBlank() && artist.isBlank() && album.isBlank()) {
        return null
    }

    return NowPlayingState.Available(
        title = title,
        artist = artist,
        album = album,
        durationMillis = 0L,
        positionMillis = 0L,
        albumArt = albumArt,
        sourcePackageName = packageName,
        sourceAppName = context.appName(packageName),
        isPlaying = false
    )
}

private fun Context.appName(packageName: String): String {
    if (packageName.isBlank()) {
        return ""
    }

    return runCatching {
        val applicationInfo = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            packageManager.getApplicationInfo(packageName, android.content.pm.PackageManager.ApplicationInfoFlags.of(0))
        } else {
            @Suppress("DEPRECATION")
            packageManager.getApplicationInfo(packageName, 0)
        }
        packageManager.getApplicationLabel(applicationInfo).toString()
    }.getOrDefault(packageName)
}

private const val POSITION_REFRESH_INTERVAL_MILLIS = 1_000L
private const val ENABLED_NOTIFICATION_LISTENERS_SETTING = "enabled_notification_listeners"
private const val ENABLED_NOTIFICATION_LISTENERS_SEPARATOR = ':'
