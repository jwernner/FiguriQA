# BUG_MAP_PROFESSOR.md

**Arquivo exclusivo do professor. Nao entregar aos alunos.**

Este mapa lista os bugs intencionais do FiguriQA 2026 apos as etapas de melhoria visual, PWA e migracao para PostgreSQL/Flyway. A auditoria confirmou que os defeitos planejados continuam presentes no comportamento ou no desenho atual da aplicacao. Nao ha comentarios no codigo indicando os locais dos bugs.

## Visao Geral

| ID | Bug intencional | Status | Evidencia tecnica atual | Observacao de validacao |
| --- | --- | --- | --- | --- |
| B01 | Percentual do dashboard pode ultrapassar 100% quando ha muitas repetidas. | Presente | `CollectionService.stats` calcula `((unique + repeated) * 100.0) / total`. | A barra visual limita a largura em 100%, mas o numero textual pode passar de 100%. |
| B02 | Filtro "faltantes" tambem mostra algumas figurinhas obtidas. | Presente | `CollectionService.matchesFilter` inclui `stickerId % 13 == 0` no filtro de faltantes. | Filtrar por faltantes e procurar itens com quantidade positiva. |
| B03 | Busca diferencia maiusculas e minusculas. | Presente | `CollectionService.stickerViews` usa `contains` direto em `code` e `title`. | Buscar `figurinha` e `Figurinha` retorna resultados diferentes. |
| B04 | Sistema aceita quantidade negativa de figurinhas. | Presente | `CollectionController.update` repassa `quantity`; `CollectionService.updateQuantity` salva sem minimo. | Inserir `-1` em Minha colecao. |
| B05 | Sistema aceita quantidade exagerada, como 9999. | Presente | Nao ha limite maximo no controller, service, entidade ou migration. | Inserir `9999` em Minha colecao. |
| B06 | Figurinha com quantidade 1 aparece indevidamente como repetida em alguns lugares. | Presente | `CollectionService.statusFor` retorna `repetida` para `quantity >= 1`. | Itens com quantidade 1 podem aparecer com status repetida. |
| B07 | Sugestao de troca considera figurinha inexistente ou nao possuida pelo usuario. | Presente | `TradeService.suggestions` usa fallback com uma figurinha do catalogo quando nao encontra par ideal. | Comparar sugestao com a colecao real do usuario. |
| B08 | Ao aceitar uma troca, o status muda, mas os inventarios nao sao atualizados. | Presente | `TradeService.accept` altera apenas `status` para `ACEITA`. | Aceitar troca e comparar quantidades antes/depois. |
| B09 | Troca cancelada continua aparecendo como pendente no dashboard. | Presente | `TradeService.dashboardTrades` inclui `PENDENTE` ou `CANCELADA`. | Dashboard exibe canceladas no bloco de ultimas propostas. |
| B10 | Codigo de confirmacao aceita qualquer valor iniciado por `TRD-`. | Presente | `TradeService.complete` valida somente `code.startsWith("TRD-")`. | Usar `TRD-QUALQUERCOISA` para concluir. |
| B11 | Ranking soma repetidas como se fossem figurinhas unicas. | Presente | `RankingService.ranking` ordena por `unique + repeated`. | Usuario com muitas repetidas pode superar outro com mais unicas reais. |
| B12 | Excluir figurinha no admin remove do catalogo sem validacao de impacto. | Presente | `AdminController.deleteSticker` remove `trade_items`, `user_stickers` e depois a figurinha. | Excluir figurinha usada em colecoes/trocas e observar perda no catalogo. |
| B13 | Usuario comum consegue acessar uma URL administrativa digitando manualmente. | Presente | `AuthInterceptor` valida apenas usuario logado, nao perfil; `AdminController` nao checa role. | Login como `ana` e acessar `/admin`. |
| B14 | Mensagem de login e imprecisa. | Presente | `AuthController.doLogin` mostra "Nao foi possivel abrir o album agora..." para credenciais invalidas. | Tentar senha errada. |
| B15 | PWA mostra "dados sincronizados" mesmo offline. | Presente | `app.js` exibe toast `Dados sincronizados` no evento `offline`. | Simular offline no navegador. |
| B16 | Dashboard pode ficar visivel em cache apos logout. | Presente com comportamento dependente de PWA/cache | `service-worker.js` cacheia navegacoes GET, inclusive `/dashboard`, e usa cache da URL quando a rede falha. | Apos visitar dashboard e sair, testar offline ou rede indisponivel; cache pode revelar a ultima tela. |
| B17 | Botao "Propor troca" fica ruim em tela pequena. | Presente como problema de UX responsivo | `.propose-button` nao possui tratamento especifico mobile; dentro de cards estreitos herda o layout generico do botao. | Em viewport estreita, verificar largura/alinhamento/toque do botao nos cards de sugestao. |
| B18 | Alguns botoes nao possuem rotulo acessivel. | Presente | Botao de exclusao no admin usa apenas `x`, classe `icon-button`, sem `aria-label`. | Inspecionar admin ou usar leitor/validador de acessibilidade. |
| B19 | Exportacao CSV inverte colunas de faltantes e repetidas. | Presente | `RankingService.exportCsv` declara `repetidas,faltantes`, mas escreve `missing,repeated`. | Baixar `/ranking.csv` e comparar cabecalho/valores. |
| B20 | Campo apelido aceita HTML sem sanitizacao. | Presente | Templates usam `th:utext` para nickname; seed da Carla contem `Carla <b>Craque</b>` via migration e reset. | Ranking/header renderizam HTML no apelido. |

## Observacoes Pos-Migracao

- A migracao para PostgreSQL preservou a massa didatica: 4 usuarios, 8 selecoes, 96 figurinhas, colecoes variadas, pontos de troca e propostas com status diversos.
- O seed inicial principal agora esta em `src/main/resources/db/migration/V2__insert_seed_data.sql`.
- O reset administrativo ainda usa `SeedData.reset()` e mantem os mesmos comportamentos didaticos.
- O bug B16 mudou de evidencia tecnica em relacao a versoes anteriores: o service worker agora usa rede primeiro para navegacao, mas ainda grava paginas autenticadas no cache e pode servi-las quando a rede falha.
- O bug B17 deve ser validado visualmente em tela estreita, pois a manifestacao depende do tamanho da tela, zoom e navegador.

## Uso Em Aula

Entregue aos alunos apenas:

- `README.md`
- `README_ALUNO.md`
- acesso ao sistema em execucao

Mantenha este arquivo e `CHECKLIST_PROFESSOR.md` apenas para correcao, discussao e rubrica.
