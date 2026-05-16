# BUG_MAP_PROFESSOR.md

**Arquivo exclusivo do professor. Nao entregar aos alunos.**

Este mapa lista os bugs intencionais implementados no FiguriQA 2026 para a atividade de Gestao e Qualidade de Software.

| ID | Bug intencional | Evidencia esperada |
| --- | --- | --- |
| B01 | Percentual do dashboard pode ultrapassar 100% quando ha muitas repetidas. | `CollectionService.stats` soma unicas e repetidas no percentual. |
| B02 | Filtro "faltantes" tambem mostra algumas figurinhas obtidas. | `CollectionService.matchesFilter` inclui IDs multiplos de 13. |
| B03 | Busca diferencia maiusculas e minusculas. | Busca usa `contains` direto em codigo/titulo. |
| B04 | Sistema aceita quantidade negativa de figurinhas. | `CollectionController.update` e `CollectionService.updateQuantity` nao validam minimo. |
| B05 | Sistema aceita quantidade exagerada, como 9999. | Campo numerico e servico nao aplicam limite maximo. |
| B06 | Figurinha com quantidade 1 aparece indevidamente como repetida em alguns lugares. | `CollectionService.statusFor` retorna repetida para quantidade >= 1. |
| B07 | Sugestao de troca considera figurinha inexistente ou nao possuida pelo usuario. | `TradeService.suggestions` usa fallback sem validar posse/repeticao real. |
| B08 | Ao aceitar uma troca, status muda, mas inventarios nao sao atualizados. | `TradeService.accept` altera apenas o status. |
| B09 | Troca cancelada continua aparecendo como pendente no dashboard. | `TradeService.dashboardTrades` inclui `CANCELADA` junto com `PENDENTE`. |
| B10 | Codigo de confirmacao aceita qualquer valor iniciado por `TRD-`. | `TradeService.complete` usa apenas `startsWith("TRD-")`. |
| B11 | Ranking soma repetidas como se fossem figurinhas unicas. | `RankingService.ranking` ordena por unicas + repetidas. |
| B12 | Excluir figurinha no admin remove do catalogo sem validacao de impacto. | `AdminController.deleteSticker` remove vinculos e catalogo diretamente. |
| B13 | Usuario comum consegue acessar URL administrativa digitando manualmente. | `AuthInterceptor` valida login, mas nao perfil ADMIN. |
| B14 | Mensagem de login e imprecisa. | Login invalido mostra mensagem generica sobre abrir album. |
| B15 | PWA mostra "dados sincronizados" mesmo offline. | `app.js` exibe toast no evento `offline`. |
| B16 | Dashboard pode ficar visivel em cache apos logout. | `service-worker.js` cacheia `/dashboard` e responde cache primeiro. |
| B17 | Botao "Propor troca" fica ruim em tela pequena. | CSS mobile aplica largura/transform inadequado em `.propose-button`. |
| B18 | Alguns botoes nao possuem rotulo acessivel. | Botao de exclusao no admin usa apenas `x` sem `aria-label`. |
| B19 | Exportacao CSV inverte colunas de faltantes e repetidas. | Cabecalho declara repetidas/faltantes, mas valores saem faltantes/repetidas. |
| B20 | Campo apelido aceita HTML sem sanitizacao. | Templates usam `th:utext` para nickname; seed da Carla contem HTML. |

Sugestao de uso em aula: entregue apenas `README.md` e `README_ALUNO.md`. Guarde este arquivo para correcao, discussao e rubrica.
