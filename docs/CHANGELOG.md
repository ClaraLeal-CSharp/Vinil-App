# Changelog

Todas as mudanças relevantes deste projeto serão registradas neste arquivo.

## [Não lançado]

### Adicionado

- Navegação central com Navigation Compose e destino inicial de Now Playing.
- Hilt configurado com `VinilApplication`, `MainActivity`, ViewModel e módulos de extensão.
- Pacotes e contratos iniciais para dados de sessões, notificações, repositório, caso de uso e serviço futuro.
- Android Lint, ktlint e regras de formatação em `.editorconfig`.
- Arquivos de tema separados em cores, tipografia e composição do tema.
- Sistema de temas desacoplado com tema padrão e tokens para cores, fontes, tamanhos, animações, discos, fundos e controles.
- Tokens adicionais de tema para a interface principal, incluindo disco, capa, progresso e controles.
- Componente `VinylDisk` em Canvas, com ranhuras, iluminação, profundidade, sombra, textura, furo central, capa centralizada e rotação contínua.
- Base arquitetural com MVVM e separação entre `app`, `core`, `domain` e `feature`.
- Interface principal mockada de Now Playing, com disco responsivo, capa central, metadados falsos, progresso e botões sem ação real.
- Contrato de repositório e estado de domínio para a futura fonte de reprodução.
- Dependências de Coroutines, Lifecycle ViewModel e Coil.
- Documentação de arquitetura, diretórios e roadmap.
- Recomendações de extensões para VS Code.
- Comunicação Android com `NotificationListenerService` e `MediaSessionManager`.
- Detecção de título, artista, álbum, duração, posição, capa e aplicativo responsável a partir de sessões de mídia compatíveis.
- Fallback por notificação de transporte quando o player não entrega uma sessão mapeável.
- Estado visual sem mídia ativa no lugar dos dados mockados.
- Interface ligada aos dados reais com transições suaves de capa, metadados e progresso.
- Rotação do disco sincronizada com o estado de reprodução, incluindo desaceleração suave ao pausar.
