# Árvore de diretórios

```text
VinilApp/
├── .vscode/
│   └── extensions.json                 # Extensões recomendadas para VS Code
├── .editorconfig                       # Convenções de formatação Kotlin
├── .gitignore
├── app/
│   ├── build.gradle.kts                # Configuração do módulo Android
│   ├── proguard-rules.pro
│   └── src/main/
│       ├── AndroidManifest.xml
│       ├── java/br/com/vinilapp/
│       │   ├── MainActivity.kt
│       │   ├── app/
│       │   │   ├── VinilApp.kt
│       │   │   ├── VinilApplication.kt
│       │   │   └── navigation/
│       │   │       ├── AppDestination.kt
│       │   │       └── VinilNavHost.kt
│       │   ├── core/designsystem/
│       │   │   ├── component/
│       │   │   │   └── VinylDisk.kt
│       │   │   └── theme/
│       │   │       ├── Theme.kt
│       │   │       └── VinilThemeTokens.kt
│       │   ├── data/
│       │   │   ├── mediaplayback/
│       │   │   │   ├── AndroidMediaSessionDataSource.kt
│       │   │   │   └── MediaSessionDataSource.kt
│       │   │   ├── notification/NotificationDataSource.kt
│       │   │   └── repository/NowPlayingRepositoryImpl.kt
│       │   ├── di/
│       │   │   ├── AppModule.kt
│       │   │   └── RepositoryModule.kt
│       │   ├── domain/
│       │   │   ├── model/
│       │   │   │   ├── NowPlayingState.kt
│       │   │   │   └── PlaybackCommand.kt
│       │   │   ├── repository/NowPlayingRepository.kt
│       │   │   └── usecase/
│       │   │       ├── ControlPlaybackUseCase.kt
│       │   │       └── ObserveNowPlayingUseCase.kt
│       │   ├── feature/nowplaying/presentation/
│       │   │   ├── NowPlayingRoute.kt
│       │   │   ├── NowPlayingScreen.kt
│       │   │   ├── NowPlayingUiState.kt
│       │   │   └── NowPlayingViewModel.kt
│       │   └── service/notification/NowPlayingNotificationListenerService.kt
│       └── res/values/
│           ├── strings.xml
│           └── themes.xml
├── docs/
│   ├── ARCHITECTURE.md
│   ├── CHANGELOG.md
│   ├── DIRECTORY_TREE.md
│   └── ROADMAP.md
├── gradle/
│   ├── libs.versions.toml              # Catálogo de versões
│   └── wrapper/
├── build.gradle.kts
├── gradle.properties
├── gradlew
├── gradlew.bat
├── README.md
└── settings.gradle.kts
```

`AndroidMediaSessionDataSource` acessa `MediaSessionManager`, recebe tokens/snapshots do `NowPlayingNotificationListenerService` e encaminha comandos de transporte à sessão ativa. O design system centraliza os tokens visuais no tema; telas e componentes não devem definir cores ou estilos fixos. `VinylDisk` concentra o desenho do disco em Canvas e a animação de rotação, ativada quando há reprodução em andamento. A tela `NowPlayingScreen` exibe metadados reais quando um player compatível expõe sessão ou notificação de mídia; o app não implementa player próprio.
