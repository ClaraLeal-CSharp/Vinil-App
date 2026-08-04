package br.com.vinilapp.service.notification

import android.app.Notification
import android.graphics.Bitmap
import android.media.session.MediaSession
import android.service.notification.NotificationListenerService
import android.service.notification.StatusBarNotification
import androidx.core.graphics.drawable.toBitmap
import br.com.vinilapp.data.mediaplayback.AndroidMediaSessionDataSource
import br.com.vinilapp.data.mediaplayback.NotificationMediaSnapshot
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class NowPlayingNotificationListenerService : NotificationListenerService() {
    @Inject
    lateinit var mediaSessionDataSource: AndroidMediaSessionDataSource

    override fun onListenerConnected() {
        refreshActiveMediaNotifications()
    }

    override fun onNotificationPosted(sbn: StatusBarNotification?) {
        refreshActiveMediaNotifications()
    }

    override fun onNotificationRemoved(sbn: StatusBarNotification?) {
        refreshActiveMediaNotifications()
    }

    private fun refreshActiveMediaNotifications() {
        val snapshots = activeNotifications
            ?.mapNotNull { notification -> notification.mediaSnapshot() }
            ?.sortedByDescending { snapshot -> snapshot.postedAtMillis }
            .orEmpty()

        mediaSessionDataSource.refreshFromNotificationListener(snapshots)
    }

    private fun StatusBarNotification.mediaSnapshot(): NotificationMediaSnapshot? {
        val sessionToken = mediaSessionToken()
        val notification = notification

        if (sessionToken == null && notification.category != Notification.CATEGORY_TRANSPORT) {
            return null
        }

        return NotificationMediaSnapshot(
            packageName = packageName,
            title = notification.extras.text(Notification.EXTRA_TITLE),
            artist = notification.extras.text(Notification.EXTRA_TEXT),
            album = notification.extras.text(Notification.EXTRA_SUB_TEXT),
            albumArt = notification.largeIconBitmap(),
            sessionToken = sessionToken,
            postedAtMillis = postTime
        )
    }

    private fun Notification.largeIconBitmap(): Bitmap? = runCatching {
        getLargeIcon()?.loadDrawable(this@NowPlayingNotificationListenerService)?.toBitmap()
    }.getOrNull()
}

@Suppress("DEPRECATION")
private fun StatusBarNotification.mediaSessionToken(): MediaSession.Token? =
    notification.extras.getParcelable(Notification.EXTRA_MEDIA_SESSION)

private fun android.os.Bundle.text(key: String): String = getCharSequence(key)?.toString().orEmpty()
