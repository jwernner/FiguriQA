# Atividade Prática — Testes e Qualidade de Software no FiguriQA 2026

## Contexto

Vocês fazem parte de uma equipe de QA responsável por testar um portal de gestão e troca de figurinhas em ambiente de homologação.

O FiguriQA 2026 simula um produto web com funcionalidades de coleção, álbum, trocas, ranking, pontos de troca, administração e experiência PWA.

## Missão dos alunos

A missão da equipe é:

- explorar o sistema;
- identificar comportamentos incorretos;
- registrar bugs;
- classificar severidade e prioridade;
- anexar evidências;
- sugerir melhorias;
- criar casos de teste de regressão.

## Áreas que devem ser testadas

- Login
- Dashboard
- Álbum
- Minha coleção
- Trocas
- Ranking
- Pontos de troca
- Administração
- Responsividade mobile
- PWA
- Comportamento offline
- Validação de campos
- Controle de acesso
- Relatórios ou exportações, se existirem

## Usuários disponíveis

- `ana / 123456`
- `bruno / 123456`
- `carla / 123456`
- `admin / admin123`

O usuário `admin` existe apenas para fins de teste do ambiente.

## Modelo de relatório de bug

Cada bug deve seguir este formato:

```text
ID:
Título:
Área:
Tipo:
Severidade:
Prioridade:
Ambiente:
Usuário utilizado:
Passos para reproduzir:
Resultado esperado:
Resultado obtido:
Evidência:
Sugestão de correção:
Caso de teste de regressão:
```

## Critérios de classificação

### Severidade

- Baixa
- Média
- Alta
- Crítica

### Prioridade

- Baixa
- Média
- Alta
- Urgente

### Tipos de defeito

- Funcional
- Usabilidade
- Validação
- Segurança
- Responsividade
- PWA
- Dados
- Regra de negócio
- Acessibilidade
- Performance

## Entregáveis

A equipe deve entregar:

- relatório de bugs;
- evidências em imagem ou vídeo curto;
- lista dos principais riscos encontrados;
- sugestão de melhorias;
- pelo menos um caso de teste de regressão para cada bug crítico ou de alta severidade.

## Orientações

- Teste com mais de um usuário.
- Compare dados entre telas diferentes.
- Tente valores limites e entradas inesperadas.
- Verifique se uma ação altera o estado esperado do sistema.
- Teste em tela pequena, no navegador do celular e como PWA instalado quando possível.
- Em iPhone, avalie a orientação manual para adicionar à tela de início.
- Teste em modo offline e observe se as mensagens fazem sentido.
- Registre evidências claras para facilitar a reprodução.
