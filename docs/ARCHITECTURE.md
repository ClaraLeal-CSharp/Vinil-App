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

Agrupa cada recurso por contexto. Hoje contém `nowplaying/presentation`, com rota, tela, estado de UI e `ViewModel`. As telas consomem estados expostos pelo ViewModel e não conhecem APIs Android de mídia.

### `domain`

Contém regras e contratos independentes de implementação. `NowPlayingRepository` será consumido pelo caso de uso ou ViewModel quando houver uma fonte de dados concreta. `NowPlayingState` representa o estado de domínio atual.

### `data`

Contém contratos vazios para fontes de `MediaSession` e de notificações, além do local reservado para a implementação de repositório. Nas próximas etapas deverá concentrar os adaptadores das APIs oficiais. Nenhuma coleta existe ainda.

### `di`

Contém os módulos Hilt de escopo de processo. `AppModule` receberá dependências compartilhadas e `RepositoryModule` ligará interfaces de `domain` às implementações de `data` assim que elas existirem. Os módulos não fornecem objetos nesta etapa.

### `service`

Contém o marcador para o futuro listener de notificações. Ele não estende `NotificationListenerService` nem aparece no manifest; logo, não declara permissão, não é iniciado e não lê notificações.

### `core`

Reúne componentes transversais. Neste momento contém o tema Material 3 e dimensões compartilhadas. Recursos reutilizáveis futuros devem entrar aqui somente quando forem realmente comuns a mais de uma feature.

## Fluxo de dados planejado

1. Uma fonte Android observa sessões ou notificações autorizadas.
2. Um repositório em `data` transforma o resultado em `NowPlayingState`.
3. O contrato em `domain` expõe um `Flow`.
4. O ViewModel converte o estado de domínio em estado de apresentação.
5. A tela Compose coleta o estado de modo ciente do ciclo de vida.

## Decisões atuais

- Coroutines e `Flow` serão o mecanismo de atualização assíncrona.
- Coil está disponível para carregar futuras imagens de capa.
- Navigation Compose concentra os destinos em `app/navigation`.
- Hilt está configurado para a composição futura das dependências, sem fontes de dados ativas.
- Android Lint e ktlint protegem a consistência técnica e de estilo.
- Strings de interface ficam em recursos Android para permitir localização.
- Não há serviço, listener, permissão especial ou leitura de dados nesta etapa.
