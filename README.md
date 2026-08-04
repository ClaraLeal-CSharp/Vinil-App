# VinilApp

O VinilApp é um aplicativo Android nativo concebido como um *Now Playing Display*: ele exibirá as informações da música reproduzida por outros aplicativos. Não é um player de música. A futura integração usará exclusivamente APIs oficiais do Android, como `MediaSession` e `NotificationListenerService`.

Nesta etapa, o projeto contém a base técnica e a interface principal mockada: estrutura de pacotes, navegação, DI, sistema de temas, qualidade de código, tela Now Playing responsiva e documentação. Nenhuma integração de mídia ou player foi implementado.

## Tecnologias

- Kotlin e Coroutines
- Jetpack Compose com Material 3
- Arquitetura MVVM, com separação inspirada em Clean Architecture
- Navigation Compose e Hilt, preparados para o crescimento do aplicativo
- Coil, preparado para o carregamento futuro das capas
- Gradle Kotlin DSL e Gradle Wrapper
- Android Lint e ktlint
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
./gradlew lintDebug
./gradlew ktlintCheck
```

O APK de depuração é produzido em `app/build/outputs/apk/debug/app-debug.apk`.

## Arquitetura

O projeto aplica MVVM na interface e separa responsabilidades em camadas:

- `feature`: rotas, telas, estado de interface e ViewModels.
- `domain`: contratos e modelos independentes do Android.
- `data`: contratos e implementações futuras das fontes oficiais de mídia.
- `core`: design system, sistema de temas e utilitários compartilhados.
- `app`: composição da aplicação, navegação e ponto de entrada Compose.
- `di`: módulos Hilt que ligarão contratos e implementações.
- `service`: pontos de extensão para serviços Android futuros, ainda inativos.

Consulte [ARCHITECTURE.md](docs/ARCHITECTURE.md) para os detalhes.

## Pastas

A árvore completa e atualizada está em [DIRECTORY_TREE.md](docs/DIRECTORY_TREE.md).

## Funcionalidades atuais

- Projeto Android configurado para API 36.
- Sistema de temas desacoplado com tokens para cores, fontes, tamanhos, animações, discos, fundos e controles.
- Tema padrão aplicado sobre Compose e Material 3, sem cores fixas nos componentes.
- Navegação Compose centralizada com destino inicial de Now Playing.
- Hilt configurado para Activity, Application, ViewModel e módulos futuros.
- Interface principal de Now Playing com disco, capa central, metadados mockados, barra de progresso e botões sem ação real.
- Layout responsivo baseado em tokens de tema, com o disco ocupando a área visual dominante da tela.
- ViewModel de base sem conexão com dados externos.
- Contratos de dados, domínio e serviço criados sem acesso às APIs Android.
- Lint Android e ktlint configurados.
- Configuração de extensões recomendadas para VS Code.

## Funcionalidades futuras

- Detecção de sessões de mídia com `MediaSession`.
- Leitura opcional de notificações através de `NotificationListenerService`.
- Substituição dos dados mockados por capa, título, artista e estado de reprodução vindos das APIs oficiais.
- Personalização visual, animação do disco e preferências persistentes.

O planejamento completo está em [ROADMAP.md](docs/ROADMAP.md), e o histórico relevante em [CHANGELOG.md](docs/CHANGELOG.md).
