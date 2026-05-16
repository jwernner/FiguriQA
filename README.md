# FiguriQA 2026

Portal web didatico, em Java, para a disciplina de Gestao e Qualidade de Software. A aplicacao simula um sistema ficticio de gestao e troca de figurinhas de um campeonato mundial de futebol em 2026, sem uso de marcas, escudos, jogadores, logotipos ou imagens reais.

O FiguriQA 2026 possui identidade visual propria, logo em SVG local, landing page publica, experiencia responsiva e recursos PWA para instalacao em celular.

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

## Identidade visual

A interface mistura album de figurinhas, futebol ficticio, tecnologia e qualidade de software. A marca usa:

- escudo estilizado;
- figurinha brilhante;
- check de qualidade;
- referencia discreta a investigacao de bugs;
- paleta em azul escuro, ciano, roxo e dourado.

Os assets ficam em `src/main/resources/static/img/`.

## Como executar com Docker

Na raiz do projeto, execute:

```bash
docker compose up --build
```

Acesse:

```text
http://localhost:8080
```

A rota inicial exibe a landing page publica. Para entrar diretamente no sistema, use:

```text
http://localhost:8080/login
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

## Uso como PWA

O projeto inclui `manifest.json`, `service-worker.js`, pagina offline e icones locais.

Em Android/Chrome, ao acessar pelo celular, o sistema pode exibir o banner:

```text
Instale o FiguriQA 2026 no seu celular
```

Clique em **Instalar aplicativo** para adicionar o app a tela inicial. Em iPhone/Safari, quando o prompt automatico nao estiver disponivel, o sistema mostra a orientacao manual: tocar em Compartilhar e depois em Adicionar a Tela de Inicio.

Caso esteja testando alteracoes no PWA, limpe os dados do site `localhost:8080` no navegador ou use uma aba anonima para evitar cache antigo.

## Orientacoes para registro de bugs

Ao encontrar um problema, registre:

- contexto e usuario utilizado;
- passos claros para reproduzir;
- resultado esperado;
- resultado obtido;
- evidencia, como print, video curto, log ou URL;
- severidade e prioridade justificadas.

Use o modelo do arquivo `README_ALUNO.md`.
