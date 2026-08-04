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
│       │   │   ├── AppDimensions.kt
│       │   │   └── theme/
│       │   │       ├── Color.kt
│       │   │       ├── Theme.kt
│       │   │       └── Type.kt
│       │   ├── data/
│       │   │   ├── mediaplayback/MediaSessionDataSource.kt
│       │   │   ├── notification/NotificationDataSource.kt
│       │   │   └── repository/NowPlayingRepositoryImpl.kt
│       │   ├── di/
│       │   │   ├── AppModule.kt
│       │   │   └── RepositoryModule.kt
│       │   ├── domain/
│       │   │   ├── model/NowPlayingState.kt
│       │   │   ├── repository/NowPlayingRepository.kt
│       │   │   └── usecase/ObserveNowPlayingUseCase.kt
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

Os contratos e marcadores de `data` e `service` não acessam APIs Android nesta etapa. Eles existem para estabilizar as fronteiras entre as camadas antes da implementação da detecção de mídia.
