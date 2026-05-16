# FiguriQA 2026

Portal web didatico, em Java, para a disciplina de Gestao e Qualidade de Software. A aplicacao simula um sistema ficticio de gestao e troca de figurinhas de um campeonato mundial de futebol em 2026, sem uso de marcas, escudos, jogadores, logotipos ou imagens reais.

## Objetivo didatico

O projeto foi criado para uma aula pratica de exploracao, teste e registro de defeitos. Os alunos devem navegar pelo sistema como colecionadores, validar regras de negocio, usabilidade, PWA, controle de acesso e fluxos de troca.

O sistema contem bugs intencionais. O arquivo `BUG_MAP_PROFESSOR.md` e exclusivo do professor e nao deve ser entregue aos alunos.

## Stack

- Java 21
- Spring Boot 3
- Maven
- Spring MVC
- Thymeleaf
- Spring Data JPA
- H2 Database
- HTML, CSS e JavaScript simples
- PWA
- Docker e Docker Compose

## Como executar com Docker

Na raiz do projeto, execute:

```bash
docker compose up --build
```

Acesse:

```text
http://localhost:8080
```

## Usuarios de teste

| Usuario | Senha | Perfil |
| --- | --- | --- |
| ana | 123456 | COLECIONADOR |
| bruno | 123456 | COLECIONADOR |
| carla | 123456 | COLECIONADOR |
| admin | admin123 | ADMIN |

## Como parar os containers

```bash
docker compose down
```

## Como resetar o ambiente

Como o banco H2 esta em memoria, reiniciar a aplicacao recria a base inicial:

```bash
docker compose down
docker compose up --build
```

Tambem existe uma opcao de reset na tela de Administracao.

## Orientacoes para registro de bugs

Ao encontrar um problema, registre:

- contexto e usuario utilizado;
- passos claros para reproduzir;
- resultado esperado;
- resultado obtido;
- evidencia, como print, video curto, log ou URL;
- severidade e prioridade justificadas.

Use o modelo do arquivo `README_ALUNO.md`.
