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
- Base arquitetural com MVVM e separação entre `app`, `core`, `domain` e `feature`.
- Interface principal mockada de Now Playing, com disco responsivo, capa central, metadados falsos, progresso e botões sem ação real.
- Contrato de repositório e estado de domínio para a futura fonte de reprodução.
- Dependências de Coroutines, Lifecycle ViewModel e Coil.
- Documentação de arquitetura, diretórios e roadmap.
- Recomendações de extensões para VS Code.
