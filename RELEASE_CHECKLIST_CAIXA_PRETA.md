# RELEASE_CHECKLIST_CAIXA_PRETA.md

Checklist de conferência da release caixa preta do FiguriQA 2026.

- [x] ZIP gerado.
- [x] `src/` não está no ZIP.
- [x] `pom.xml` não está no ZIP.
- [x] `BUG_MAP_PROFESSOR.md` não está no ZIP.
- [x] `CHECKLIST_PROFESSOR.md` não está no ZIP.
- [x] Nenhum gabarito está no ZIP.
- [x] `app.jar` está no ZIP.
- [x] `docker-compose.yml` está no ZIP.
- [x] `README.md` está claro.
- [x] `README_ALUNO.md` está claro.
- [x] Aplicação sobe com `docker compose up --build`.
- [x] Aplicação abre em `http://localhost:8080`.
- [x] Usuários de teste funcionam.
- [x] PostgreSQL sobe corretamente.
- [x] PWA está disponível.
- [x] O sistema pode ser testado sem código-fonte.

Validação realizada em 17/05/2026 a partir de `release-caixa-preta/figuriqa-2026-caixa-preta`:

- `docker compose up --build -d` executado com sucesso.
- PostgreSQL ficou `healthy`.
- Flyway validou e aplicou 2 migrations internas do `app.jar`.
- `GET http://localhost:8080` retornou `200`.
- `GET http://localhost:8080/manifest.json` retornou `200`.
- Login com `ana / 123456` retornou redirecionamento para `/dashboard`.
- Auditoria do ZIP confirmou ausência de `src/`, `pom.xml`, arquivos Java, templates editáveis, arquivos SQL, gabaritos e documentos do professor.
