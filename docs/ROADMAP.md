# Roadmap

## Etapa 0 — Fundação

- [x] Configurar Kotlin, Compose, Material 3 e API 36.
- [x] Definir estrutura de pacotes, MVVM e contrato de domínio.
- [x] Preparar documentação e suporte a VS Code.
- [x] Adicionar dependências para Coroutines, ViewModel e Coil.

## Etapa 1 — Fonte de dados de mídia

- [ ] Implementar observação de `MediaSession` com APIs oficiais.
- [ ] Definir tratamento para múltiplas sessões ativas.
- [ ] Adicionar `NotificationListenerService` como fonte complementar, quando necessário.
- [ ] Implementar fluxo de autorização e estados de erro.

## Etapa 2 — Experiência Now Playing

- [ ] Exibir título, artista, aplicativo de origem e capa.
- [ ] Exibir estado de reprodução e ausência de conteúdo.
- [ ] Carregar capas com Coil e cache apropriado.
- [ ] Criar animação fluida do disco de vinil.

## Etapa 3 — Personalização

- [ ] Criar preferências de tema e layout.
- [ ] Permitir seleção de fontes de mídia.
- [ ] Persistir configurações locais.

## Etapa 4 — Qualidade

- [ ] Adicionar testes unitários para ViewModels e mapeadores.
- [ ] Adicionar testes de interface essenciais.
- [ ] Revisar acessibilidade, desempenho e internacionalização.
