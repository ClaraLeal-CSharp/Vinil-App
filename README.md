# VinilApp

O VinilApp é um aplicativo Android nativo concebido como um *Now Playing Display*: ele exibirá as informações da música reproduzida por outros aplicativos. Não é um player de música. A futura integração usará exclusivamente APIs oficiais do Android, como `MediaSession` e `NotificationListenerService`.

Nesta primeira etapa, o projeto contém apenas a base técnica, a estrutura de pacotes, a tela inicial estática e a documentação. Nenhuma integração de mídia foi implementada.

## Tecnologias

- Kotlin e Coroutines
- Jetpack Compose com Material 3
- Arquitetura MVVM, com separação inspirada em Clean Architecture
- Coil, preparado para o carregamento futuro das capas
- Gradle Kotlin DSL e Gradle Wrapper
- `compileSdk` e `targetSdk` 36; `minSdk` 24

## Requisitos

- JDK 17 ou superior
- Android SDK Platform 36 instalado
- Dispositivo ou emulador Android 7.0 (API 24) ou superior
- VS Code (opcional; o projeto não requer Android Studio)

## Como executar

1. Abra esta pasta no VS Code.
2. Instale as extensões recomendadas em `.vscode/extensions.json`.
3. Garanta que `ANDROID_HOME` ou `ANDROID_SDK_ROOT` aponte para o SDK Android.
4. Execute:

```bash
./gradlew assembleDebug
./gradlew installDebug
```

O APK de depuração é produzido em `app/build/outputs/apk/debug/app-debug.apk`.

## Arquitetura

O projeto aplica MVVM na interface e separa responsabilidades em camadas:

- `feature`: rotas, telas, estado de interface e ViewModels.
- `domain`: contratos e modelos independentes do Android.
- `data`: implementações futuras das fontes oficiais de mídia.
- `core`: componentes visuais e utilitários compartilhados.
- `app`: composição da aplicação e ponto de entrada Compose.

Consulte [ARCHITECTURE.md](docs/ARCHITECTURE.md) para os detalhes.

## Pastas

A árvore completa e atualizada está em [DIRECTORY_TREE.md](docs/DIRECTORY_TREE.md).

## Funcionalidades atuais

- Projeto Android configurado para API 36.
- Tema Material 3 com suporte ao modo escuro do sistema.
- Tela inicial estática de Now Playing.
- Contrato de domínio e ViewModel de base, sem conexão com dados externos.
- Configuração de extensões recomendadas para VS Code.

## Funcionalidades futuras

- Detecção de sessões de mídia com `MediaSession`.
- Leitura opcional de notificações através de `NotificationListenerService`.
- Exibição de capa, título, artista e estado da reprodução.
- Personalização visual, animação do disco e preferências persistentes.

O planejamento completo está em [ROADMAP.md](docs/ROADMAP.md), e o histórico relevante em [CHANGELOG.md](docs/CHANGELOG.md).
