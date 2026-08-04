# Arquitetura

## Visão geral

O VinilApp usa MVVM na camada de apresentação e uma separação leve de responsabilidades inspirada em Clean Architecture. A intenção é manter a interface independente da forma como o Android fornece metadados de mídia, sem introduzir abstrações desnecessárias nesta fase.

```text
Android APIs → service/data → domain ← feature/presentation → Compose UI
                      ↑               ↑
                    di/Hilt       app/navigation
                      ↑
                    core
```

## Camadas

### `app`

Centraliza a composição da aplicação. `VinilApplication` inicializa Hilt, `MainActivity` hospeda o Compose, `VinilApp` cria o controlador de navegação e `navigation` declara o grafo central.

### `feature`

Agrupa cada recurso por contexto. Hoje contém `nowplaying/presentation`, com rota, tela principal, estado de UI e `ViewModel`. A interface exibe disco, capa central, metadados de mídia, progresso e controles de reprodução. A tela recebe callbacks da rota e não conhece diretamente APIs Android de mídia.

### `domain`

Contém regras e contratos independentes da implementação concreta. `NowPlayingRepository` é consumido pelos casos de uso e pelo ViewModel. `NowPlayingState` representa o estado atual, com variação para permissão necessária, mídia indisponível ou metadados detectados. `PlaybackCommand` modela os comandos de faixa anterior, tocar/pausar e próxima faixa sem expor tipos Android à camada de apresentação.

### `data`

Contém o contrato de `MediaSessionDataSource`, a implementação `AndroidMediaSessionDataSource` e o repositório. A fonte usa `MediaSessionManager` para observar sessões ativas e recebe tokens/snapshots do listener de notificações como complemento. O mapeamento extrai título, artista, álbum, duração, posição calculada, capa em `Bitmap`, pacote e nome do aplicativo responsável. A mesma fonte escolhe a sessão ativa mais relevante e envia comandos por `MediaController.TransportControls` quando o player de origem aceita transporte externo.

### `di`

Contém os módulos Hilt de escopo de processo. `AppModule` fornece `MediaSessionManager` e `RepositoryModule` liga interfaces de `domain` e `data` às implementações concretas.

### `service`

Contém `NowPlayingNotificationListenerService`, declarado no manifest com `android.permission.BIND_NOTIFICATION_LISTENER_SERVICE`. O serviço é ativado pelo Android quando o usuário concede acesso a notificações. Ele coleta notificações de transporte, extrai tokens de sessão e fornece fallback básico por extras de notificação.

### `core`

Reúne componentes transversais. O design system expõe `VinilTheme`, um contrato visual próprio baseado em tokens e desacoplado das features. O tema padrão cobre cores, fontes, tamanhos, animações, discos, fundos e controles; componentes de tela devem consumir esses tokens em vez de declarar cores, medidas ou estilos locais.

O pacote `core/designsystem/component` contém componentes visuais reutilizáveis. `VinylDisk` desenha o disco exclusivamente com Canvas do Jetpack Compose, incluindo ranhuras, iluminação, profundidade, sombra, textura e furo central. A capa do álbum entra como slot Compose centralizado e o componente aplica rotação contínua por tokens de animação enquanto há reprodução, mantendo o ângulo atual e desacelerando suavemente quando a mídia pausa.

A interface principal usa esses tokens para desenhar o disco, a capa real quando disponível, a barra de progresso e os botões. Trocas de capa, metadados e progresso são animadas com APIs nativas do Compose para manter a transição fluida sem bloquear a interface. Assim, a futura personalização de temas pode alterar aparência, movimento e proporções sem reescrever a feature.

O Material 3 permanece como biblioteca de componentes, mas recebe `ColorScheme`, `Typography` e `Shapes` derivados do tema do VinilApp. Assim, futuros temas podem trocar aparência sem alterar as telas.

## Fluxo de dados planejado

1. `AndroidMediaSessionDataSource` verifica se o app tem acesso a notificações e observa sessões ativas via `MediaSessionManager`.
2. Um repositório em `data` transforma o resultado em `NowPlayingState`.
3. O contrato em `domain` expõe um `Flow`.
4. O ViewModel converte o estado de domínio em estado de apresentação.
5. A tela Compose coleta o estado de modo ciente do ciclo de vida.
6. Ações de anterior, tocar/pausar e próxima faixa voltam pelo ViewModel até a sessão de mídia ativa, sem acoplar Compose às APIs Android de mídia.

O `NotificationListenerService` complementa esse fluxo enviando tokens e snapshots de notificações de mídia ao data source. Quando múltiplas sessões existem, a fonte prioriza sessões em reprodução, depois sessões com metadados exibíveis e, por fim, a sessão com atualização de posição mais recente.

## Decisões atuais

- Coroutines e `Flow` serão o mecanismo de atualização assíncrona.
- Capas são lidas de `MediaMetadata` ou do ícone da notificação quando disponíveis.
- Navigation Compose concentra os destinos em `app/navigation`.
- Hilt injeta a fonte Android de mídia, repositório, casos de uso e ViewModel.
- Android Lint e ktlint protegem a consistência técnica e de estilo.
- Strings de interface ficam em recursos Android para permitir localização.
- Estilos visuais entram pelo `VinilTheme`; features não devem manter cores fixas ou dimensões próprias.
- A integração depende da permissão manual de acesso a notificações; sem ela, o Android não permite consultar sessões de outros apps e a interface mostra uma ação para abrir essa configuração.
- O app não usa APIs experimentais nem implementa player próprio.
- A interface principal usa dados reais quando disponíveis, anima mudanças de mídia e envia comandos de reprodução para sessões compatíveis.
- Os controles ficam desabilitados quando não há mídia ativa, evitando comandos sem sessão alvo.
