# Roadmap

## Etapa 0 — Fundação (concluída)

- [x] Configurar Kotlin, Compose, Material 3 e API 36.
- [x] Definir pacotes `app`, `core`, `data`, `di`, `domain`, `feature` e `service`.
- [x] Preparar MVVM, Navigation Compose, Hilt, catálogo de versões e temas.
- [x] Configurar Android Lint, ktlint, `.editorconfig`, `.gitignore`, documentação e suporte a VS Code.

## Etapa 0.5 — Interface principal mockada (concluída)

- [x] Criar layout principal responsivo de Now Playing.
- [x] Exibir disco em destaque, capa central, metadados falsos, progresso e botões.
- [x] Manter a tela sem player, sem `MediaSession` e sem leitura de dados externos.
- [x] Consumir estilos visuais através do sistema de temas.

## Etapa 1 — Fonte de dados de mídia

- [x] Implementar observação de `MediaSession` com APIs oficiais.
- [x] Definir tratamento para múltiplas sessões ativas.
- [x] Adicionar `NotificationListenerService` como fonte complementar.
- [x] Exibir estado sem mídia ativa quando a autorização ainda não permite leitura.
- [x] Implementar fluxo de onboarding para abrir a configuração de acesso a notificações.

## Etapa 2 — Experiência Now Playing

- [x] Exibir título, artista, álbum, aplicativo de origem, capa, duração e posição.
- [x] Exibir estado de reprodução e ausência de conteúdo.
- [x] Conectar controles de faixa anterior, tocar/pausar e próxima faixa a sessões compatíveis.
- [ ] Avaliar cache específico para capas quando houver URLs ou imagens externas estáveis.
- [x] Criar animação fluida do disco de vinil.

## Etapa 3 — Personalização

- [ ] Criar preferências de tema e layout.
- [ ] Permitir seleção de fontes de mídia.
- [ ] Persistir configurações locais.

## Etapa 4 — Qualidade

- [x] Revisar código duplicado, arquivos grandes, funções grandes, nomes, bugs prováveis, performance, memória, organização, documentação, comentários, warnings e TODOs.
- [ ] Adicionar testes unitários para ViewModels, mapeadores e seleção de sessão.
- [ ] Adicionar testes de interface essenciais.
- [ ] Revisar acessibilidade, desempenho e internacionalização.
