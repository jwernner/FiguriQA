# CHECKLIST_PROFESSOR.md

**Arquivo exclusivo do professor. Nao entregar aos alunos.**

Checklist manual para validar se os bugs intencionais do FiguriQA 2026 continuam presentes apos alteracoes de UI, PWA, PostgreSQL ou arquitetura.

## Preparacao

1. Subir o ambiente:

```bash
docker compose up --build
```

2. Acessar:

```text
http://localhost:8080
```

3. Confirmar usuarios disponiveis:

- `ana / 123456`
- `bruno / 123456`
- `carla / 123456`
- `admin / admin123`

4. Para um teste totalmente limpo, executar antes:

```bash
docker compose down -v
docker compose up --build
```

## Checklist Dos Bugs

### B01 - Percentual pode ultrapassar 100%

1. Login como `ana`.
2. Abrir **Minha colecao**.
3. Alterar varias figurinhas para uma quantidade alta, por exemplo `9999`.
4. Abrir **Dashboard**.
5. Verificar se o percentual textual pode passar de `100%`.

Resultado esperado para o bug: o numero de progresso fica acima de 100%.

### B02 - Filtro faltantes mostra obtidas

1. Login como `ana`.
2. Abrir **Album**.
3. Selecionar filtro **Faltantes**.
4. Procurar cards com quantidade positiva ou estado inconsistente.

Resultado esperado para o bug: algumas figurinhas obtidas aparecem junto das faltantes.

### B03 - Busca diferencia maiusculas e minusculas

1. Login como `ana`.
2. Abrir **Album**.
3. Buscar `Figurinha`.
4. Buscar `figurinha`.
5. Comparar os resultados.

Resultado esperado para o bug: as buscas retornam resultados diferentes.

### B04 - Quantidade negativa aceita

1. Login como `ana`.
2. Abrir **Minha colecao**.
3. Informar `-1` em uma figurinha.
4. Clicar em **Salvar**.
5. Reabrir a tela ou observar o valor persistido.

Resultado esperado para o bug: o valor negativo e aceito.

### B05 - Quantidade exagerada aceita

1. Login como `ana`.
2. Abrir **Minha colecao**.
3. Informar `9999` em uma figurinha.
4. Clicar em **Salvar**.

Resultado esperado para o bug: o valor exagerado e aceito.

### B06 - Quantidade 1 aparece como repetida

1. Login como `ana`.
2. Abrir **Minha colecao**.
3. Definir uma figurinha com quantidade `1`.
4. Salvar.
5. Observar o status visual da figurinha.

Resultado esperado para o bug: quantidade `1` pode aparecer como `repetida`.

### B07 - Sugestao de troca usa figurinha nao possuida

1. Login como `ana`.
2. Abrir **Trocas** > **Trocas sugeridas**.
3. Anotar a figurinha em **Voce oferece**.
4. Abrir **Minha colecao** e procurar a mesma figurinha.
5. Comparar a quantidade real.

Resultado esperado para o bug: a sugestao pode indicar figurinha que o usuario nao possui ou nao tem repetida.

### B08 - Aceitar troca nao atualiza inventarios

1. Login como usuario envolvido em uma troca pendente, por exemplo `ana`.
2. Abrir uma troca pendente.
3. Anotar as figurinhas e quantidades atuais na colecao.
4. Clicar em **Aceitar**.
5. Voltar para **Minha colecao** e comparar quantidades.

Resultado esperado para o bug: status muda, mas inventario nao muda.

### B09 - Cancelada aparece no dashboard

1. Login como `ana` ou `carla`.
2. Abrir **Dashboard**.
3. Verificar ultimas propostas.
4. Procurar status `CANCELADA`.

Resultado esperado para o bug: troca cancelada aparece na lista de propostas recentes do dashboard.

### B10 - Confirmacao aceita qualquer codigo TRD-

1. Login como `ana`.
2. Abrir uma troca.
3. No campo de confirmacao, informar `TRD-QUALQUER`.
4. Clicar em **Concluir**.

Resultado esperado para o bug: a troca e concluida com codigo invalido, desde que comece por `TRD-`.

### B11 - Ranking soma repetidas como unicas

1. Login como `ana`.
2. Ajustar algumas figurinhas para quantidades altas.
3. Abrir **Ranking**.
4. Comparar posicao do usuario com totais de unicas/repetidas.

Resultado esperado para o bug: repetidas influenciam a ordenacao como se aumentassem progresso real.

### B12 - Admin exclui figurinha sem validacao de impacto

1. Login como `admin`.
2. Abrir **Admin**.
3. Excluir uma figurinha ja existente.
4. Abrir **Album** ou **Ranking**.

Resultado esperado para o bug: figurinha desaparece do catalogo e vinculos sao removidos sem alerta de impacto.

### B13 - Usuario comum acessa admin por URL

1. Login como `ana`.
2. Digitar manualmente:

```text
http://localhost:8080/admin
```

Resultado esperado para o bug: usuario colecionador consegue acessar o painel administrativo.

### B14 - Mensagem de login imprecisa

1. Abrir `/login`.
2. Informar usuario valido e senha errada, por exemplo `ana / errado`.
3. Observar a mensagem.

Resultado esperado para o bug: mensagem fala em abrir album, nao em credenciais invalidas.

### B15 - PWA mostra sincronizado mesmo offline

1. Abrir a aplicacao no navegador.
2. Simular offline nas ferramentas do navegador ou desconectar a rede.
3. Observar o toast.

Resultado esperado para o bug: aparece `Dados sincronizados` mesmo estando offline.

### B16 - Dashboard visivel em cache apos logout

1. Abrir a aplicacao em navegador com service worker habilitado.
2. Login como `ana`.
3. Acessar **Dashboard**.
4. Fazer logout.
5. Simular offline.
6. Tentar acessar novamente `/dashboard` ou voltar pelo historico.

Resultado esperado para o bug: conteudo do dashboard pode aparecer a partir do cache.

### B17 - Botao Propor troca ruim em tela pequena

1. Abrir ferramentas do navegador em modo responsivo.
2. Usar largura proxima de `360px`.
3. Login como `ana`.
4. Abrir **Trocas sugeridas**.
5. Observar tamanho, alinhamento e area de toque do botao **Propor troca**.

Resultado esperado para o bug: o botao apresenta experiencia visual/ergonomica ruim em tela pequena.

### B18 - Botoes sem rotulo acessivel

1. Login como `admin`.
2. Abrir **Admin**.
3. Inspecionar os botoes de exclusao de figurinhas.
4. Verificar atributos acessiveis.

Resultado esperado para o bug: botao mostra apenas `x` e nao possui `aria-label`.

### B19 - CSV inverte faltantes e repetidas

1. Login como qualquer usuario.
2. Abrir **Ranking**.
3. Clicar em **Exportar CSV** ou acessar:

```text
http://localhost:8080/ranking.csv
```

4. Comparar cabecalho e valores das colunas `repetidas` e `faltantes`.

Resultado esperado para o bug: os valores saem invertidos em relacao ao cabecalho.

### B20 - Apelido aceita HTML

1. Login como `carla`.
2. Observar o nome/apelido no topo ou no ranking.
3. Procurar renderizacao em negrito do trecho `Craque`.

Resultado esperado para o bug: HTML do apelido e renderizado.

## Checklist De Sanidade Pos-Auditoria

1. Confirmar que `README_ALUNO.md` nao revela os bugs.
2. Confirmar que `BUG_MAP_PROFESSOR.md` e `CHECKLIST_PROFESSOR.md` nao serao entregues aos alunos.
3. Confirmar que a aplicacao ainda sobe com Docker Compose.
4. Confirmar que Flyway aplica migrations em banco limpo.
5. Confirmar que as telas principais seguem navegaveis.
