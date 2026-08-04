# Árvore de diretórios

```text
VinilApp/
├── .vscode/
│   └── extensions.json                 # Extensões recomendadas para VS Code
├── app/
│   ├── build.gradle.kts                # Configuração do módulo Android
│   ├── proguard-rules.pro
│   └── src/main/
│       ├── AndroidManifest.xml
│       ├── java/br/com/vinilapp/
│       │   ├── MainActivity.kt
│       │   ├── app/
│       │   │   └── VinilApp.kt
│       │   ├── core/designsystem/
│       │   │   ├── AppDimensions.kt
│       │   │   └── theme/Theme.kt
│       │   ├── domain/
│       │   │   ├── model/NowPlayingState.kt
│       │   │   └── repository/NowPlayingRepository.kt
│       │   └── feature/nowplaying/presentation/
│       │       ├── NowPlayingRoute.kt
│       │       ├── NowPlayingScreen.kt
│       │       ├── NowPlayingUiState.kt
│       │       └── NowPlayingViewModel.kt
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
├── .gitignore
├── build.gradle.kts
├── gradle.properties
├── gradlew
├── gradlew.bat
├── README.md
└── settings.gradle.kts
```

Os diretórios `data` e `service` serão adicionados com código concreto nas etapas de integração de mídia; não há arquivos vazios apenas para reservar estrutura.
