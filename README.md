# FiguriQA 2026 — Portal Didático de Gestão e Troca de Figurinhas

## Sobre o projeto

O FiguriQA 2026 é um portal web didático para gestão e troca de figurinhas fictícias de futebol. O sistema foi criado para uma atividade prática da disciplina de Gestão e Qualidade de Software.

Todo o tema visual é fictício. O projeto não usa marcas oficiais, escudos reais, jogadores reais, logotipos protegidos ou imagens externas.

## Objetivo da atividade

Os alunos devem explorar o sistema como uma equipe de QA em ambiente de homologação, investigando funcionalidades, usabilidade, responsividade, PWA, controle de acesso, validações e consistência de dados.

O foco da atividade é praticar:

- testes exploratórios;
- testes funcionais;
- análise de usabilidade;
- testes em experiência mobile/PWA;
- classificação de severidade e prioridade;
- registro de evidências;
- criação de casos de teste de regressão.

## Tecnologias utilizadas

- Java 21
- Spring Boot 3
- Maven
- Spring MVC
- Thymeleaf
- Spring Data JPA
- PostgreSQL
- Flyway
- HTML, CSS e JavaScript
- PWA
- Docker
- Docker Compose

## Pré-requisitos

Para o caminho principal da atividade, instale:

- Docker
- Docker Compose

Para abrir e analisar o código no NetBeans, instale também:

- NetBeans com suporte a Maven

Java 21 e Maven locais são opcionais se você for executar somente via Docker.

## Como abrir o projeto no NetBeans

1. Abra o NetBeans.
2. Clique em **File > Open Project**.
3. Selecione a pasta do projeto `figuriqa-2026-alunos`.
4. Aguarde o NetBeans reconhecer o projeto Maven.
5. Confirme se o arquivo `pom.xml` aparece na raiz.
6. Para executar com Docker, abra o terminal na pasta do projeto e rode:

```bash
docker compose up --build
```

7. Acesse no navegador:

```text
http://localhost:8080
```

O NetBeans será usado principalmente para:

- visualizar o código-fonte;
- navegar pelas classes Java;
- analisar a estrutura do projeto;
- consultar templates HTML;
- observar configurações;
- apoiar a investigação dos bugs.

## Como executar com Docker

Na pasta raiz do projeto, execute:

```bash
docker compose up --build
```

Esse comando sobe:

- aplicação Spring Boot na porta `8080`;
- banco PostgreSQL na porta `5432`;
- migrations Flyway para criar schema e dados iniciais.

## Como acessar o sistema

Abra:

```text
http://localhost:8080
```

A página inicial exibe a landing page pública. Para acessar o login diretamente:

```text
http://localhost:8080/login
```

## Usuários de teste

| Usuário | Senha | Perfil |
| --- | --- | --- |
| ana | 123456 | COLECIONADOR |
| bruno | 123456 | COLECIONADOR |
| carla | 123456 | COLECIONADOR |
| admin | admin123 | ADMIN |

O usuário `admin` existe apenas para fins de teste do ambiente.

## Como parar o ambiente

```bash
docker compose down
```

Esse comando para os containers, mantendo o volume do banco.

## Como limpar volumes e reiniciar banco

Para apagar o banco local e recriar tudo do zero:

```bash
docker compose down -v
docker compose up --build
```

Use esse procedimento quando quiser voltar ao estado inicial da atividade.

## Banco de dados local

O PostgreSQL fica disponível localmente em:

```text
localhost:5432
```

Configuração padrão:

| Campo | Valor |
| --- | --- |
| Banco | figuriqa |
| Usuário | figuriqa_user |
| Senha | figuriqa_pass |

As migrations ficam em:

```text
src/main/resources/db/migration
```

## Como testar como PWA no celular

1. Suba a aplicação com Docker.
2. Acesse o sistema pelo navegador do celular usando o endereço da máquina na rede local.
3. Em navegadores compatíveis, observe a sugestão de instalação do app.
4. No Android/Chrome, use a opção de instalar aplicativo quando disponível.
5. No iPhone/Safari, use **Compartilhar > Adicionar à Tela de Início**.
6. Teste navegação, login, layout mobile e comportamento offline.

Se estiver testando cache ou service worker, limpe os dados do site no navegador antes de repetir os testes.

## Como registrar bugs

Cada bug deve ser registrado com:

- título claro;
- área afetada;
- tipo do defeito;
- severidade;
- prioridade;
- ambiente;
- usuário utilizado;
- passos para reproduzir;
- resultado esperado;
- resultado obtido;
- evidência;
- sugestão de correção;
- caso de teste de regressão.

Use o modelo completo em `README_ALUNO.md`.

## Regras da atividade

- Teste o sistema como se ele estivesse em homologação.
- Não altere o código antes de registrar os bugs.
- Registre evidências sempre que possível.
- Classifique severidade e prioridade com justificativa.
- Teste mais de um usuário.
- Teste desktop e mobile.
- Teste experiência PWA e comportamento offline.
- Teste controle de acesso e validações de campos.
- Não use ferramentas destrutivas contra o ambiente de colegas.

## Critérios de entrega

Cada equipe deve entregar:

- relatório de bugs;
- evidências em imagem ou vídeo curto;
- lista dos principais riscos encontrados;
- sugestões de melhoria;
- pelo menos um caso de teste de regressão para cada bug crítico ou de alta severidade.

O professor poderá solicitar uma breve apresentação dos principais achados.
