# RELEASE_CHECKLIST.md

Checklist final da versao para alunos do FiguriQA 2026.

- [x] Projeto compila.
- [x] Projeto abre no NetBeans como Maven.
- [x] `pom.xml` esta na raiz.
- [x] Estrutura Maven esta correta.
- [x] Docker sobe corretamente.
- [x] Aplicacao abre em `http://localhost:8080`.
- [x] Login funciona com usuarios de teste.
- [x] PostgreSQL sobe corretamente.
- [x] Flyway executa migrations.
- [x] Landing page aparece antes do login.
- [x] PWA possui manifest.
- [x] Service worker registra corretamente.
- [x] `README.md` atualizado.
- [x] `README_ALUNO.md` atualizado.
- [x] Instrucoes do NetBeans estao no `README.md`.
- [x] `BUG_MAP_PROFESSOR.md` nao esta no ZIP.
- [x] `CHECKLIST_PROFESSOR.md` nao esta no ZIP.
- [x] `target/` nao esta no ZIP.
- [x] `.env` real nao esta no ZIP.
- [x] `nbproject/private/` nao esta no ZIP.
- [x] Nenhum arquivo revela o gabarito dos bugs.

Validacao realizada a partir do conteudo extraido de `figuriqa-2026-alunos.zip`:

- `docker compose up --build -d` executado com sucesso.
- PostgreSQL ficou `healthy`.
- Flyway validou e aplicou 2 migrations.
- `GET http://localhost:8080` retornou `200`.
- Login com `ana / 123456` retornou redirecionamento para `/dashboard`.
