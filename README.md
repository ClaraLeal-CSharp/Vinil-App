# VinilApp

O VinilApp é um aplicativo Android nativo concebido como um *Now Playing Display*: ele exibe informações da música reproduzida por outros aplicativos. Não é um player de música. A integração usa APIs oficiais do Android: `MediaSessionManager` e `NotificationListenerService`.

Nesta etapa, o projeto contém a base técnica, a interface principal e a comunicação Android para leitura de metadados de mídia quando o player expõe uma sessão compatível ou uma notificação de transporte.

## Tecnologias

- Kotlin e Coroutines
- Jetpack Compose com Material 3
- Arquitetura MVVM, com separação inspirada em Clean Architecture
- Navigation Compose e Hilt, preparados para o crescimento do aplicativo
- Coil, disponível para evolução do carregamento de capas
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

## Permissão de mídia

O Android exige autorização manual para que um app leia notificações e consulte sessões de mídia de outros aplicativos. Após instalar o APK, habilite o VinilApp em:

```text
Configurações do Android → Notificações → Acesso a notificações → VinilApp
```

O caminho pode variar por fabricante. Sem essa permissão, a tela permanece em estado sem mídia ativa.

## Arquitetura

O projeto aplica MVVM na interface e separa responsabilidades em camadas:

- `feature`: rotas, telas, estado de interface e ViewModels.
- `domain`: contratos e modelos independentes do Android.
- `data`: contratos e implementação da fonte de `MediaSessionManager`.
- `core`: design system, sistema de temas e utilitários compartilhados.
- `app`: composição da aplicação, navegação e ponto de entrada Compose.
- `di`: módulos Hilt que ligam contratos e implementações.
- `service`: `NotificationListenerService` que complementa a descoberta de sessões e notificações de mídia.

Consulte [ARCHITECTURE.md](docs/ARCHITECTURE.md) para os detalhes.

## Pastas

A árvore completa e atualizada está em [DIRECTORY_TREE.md](docs/DIRECTORY_TREE.md).

## Funcionalidades atuais

- Projeto Android configurado para API 36.
- Sistema de temas desacoplado com tokens para cores, fontes, tamanhos, animações, discos, fundos e controles.
- Tema padrão aplicado sobre Compose e Material 3, sem cores fixas nos componentes.
- Navegação Compose centralizada com destino inicial de Now Playing.
- Hilt configurado para Activity, Application, ViewModel e módulos futuros.
- Interface principal de Now Playing conectada aos dados reais quando disponíveis, com disco, capa central, metadados, barra de progresso e botões sem ação real.
- Layout responsivo baseado em tokens de tema, com o disco ocupando a área visual dominante da tela.
- ViewModel conectado ao caso de uso de reprodução atual.
- Leitura automática de título, artista, álbum, duração, posição, capa, pacote e nome do aplicativo responsável.
- Priorização de sessões em reprodução e fallback por notificação de transporte quando não houver uma sessão mapeável.
- Animações suaves para reprodução: o disco gira ao tocar, desacelera ao pausar e capa, metadados e progresso transitam quando a música muda.
- Lint Android e ktlint configurados.
- Configuração de extensões recomendadas para VS Code.

## Funcionalidades futuras

- Fluxo de onboarding para abrir diretamente a tela de permissão de acesso a notificações.
- Controles de reprodução conectados a sessões compatíveis.
- Personalização visual e preferências persistentes.

O planejamento completo está em [ROADMAP.md](docs/ROADMAP.md), e o histórico relevante em [CHANGELOG.md](docs/CHANGELOG.md).
