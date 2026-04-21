# Guia de Deploy no Render

Este projeto foi preparado para deploy automatizado em dois ambientes no Render:

- `staging` na branch `develop`
- `production` na branch `main`

Repositorio GitHub:

- `https://github.com/Nathan-Campelo/EcoCidade-ESG-API`

## Estrategia adotada

O pipeline continua no `GitHub Actions`, mas o deploy final dos ambientes sera feito no `Render` via `Deploy Hooks`.

Fluxo:

1. O push em `develop` ou `main` dispara o workflow.
2. O workflow executa `build` e `test`.
3. Se tudo passar, o GitHub Actions chama o `Deploy Hook` do Render.
4. O Render faz o deploy do commit correspondente no ambiente certo.

## Arquitetura recomendada para a entrega

Para simplificar a configuracao academica e evitar custo inicial, o projeto foi preparado com:

- 2 Web Services no Render:
  - `ecocidade-esg-api-staging`
  - `ecocidade-esg-api-production`
- 1 banco `Render Postgres` compartilhado:
  - `ecocidade-esg-db`

Observacao importante:

- O uso de um banco compartilhado foi uma escolha pratica para o modo gratuito.
- Isso acontece porque a documentacao oficial do Render informa que apenas um `Free Render Postgres` pode ficar ativo por workspace.
- Se voce quiser isolamento total entre `staging` e `production`, a adaptacao natural e subir dois bancos pagos.

## Arquivos do projeto ligados ao Render

- `render.yaml`
- `.github/workflows/ci-cd.yml`
- `src/main/resources/application.yml`

## Como criar no Render

1. Entre em `https://dashboard.render.com`.
2. Clique em `New +`.
3. Escolha `Blueprint`.
4. Conecte sua conta GitHub ao Render, se ele pedir.
5. Selecione o repositorio `Nathan-Campelo/EcoCidade-ESG-API`.
6. Confirme o uso do arquivo `render.yaml`.

O Render deve identificar automaticamente:

- `ecocidade-esg-api-staging`
- `ecocidade-esg-api-production`
- `ecocidade-esg-db`

## O que conferir na tela do Blueprint

- Os dois web services devem usar `Docker`.
- O banco deve aparecer como `Postgres`.
- A branch de `staging` deve ser `develop`.
- A branch de `production` deve ser `main`.
- O `health check` deve ser `/api/health`.

## Variaveis e banco

O `render.yaml` ja injeta:

- `APP_ENVIRONMENT=staging` no ambiente de staging
- `APP_ENVIRONMENT=production` no ambiente de production
- `DATABASE_URL` usando a `connectionString` interna do banco

O codigo foi ajustado para aceitar a `connectionString` padrao do Render e convertela automaticamente para o formato JDBC usado pelo Spring Boot.

## Deploy Hooks no GitHub

Depois que o Blueprint for criado, voce precisa pegar um `Deploy Hook` para cada web service:

1. Abra o service `ecocidade-esg-api-staging`.
2. Va em `Settings`.
3. Copie o `Deploy Hook`.
4. Repita no `ecocidade-esg-api-production`.

No GitHub, cadastre estes `Secrets`:

- `RENDER_STAGING_DEPLOY_HOOK_URL`
- `RENDER_PRODUCTION_DEPLOY_HOOK_URL`

## Como o pipeline funciona

O workflow em `.github/workflows/ci-cd.yml` executa:

1. Checkout do repositorio
2. Configuracao do Java 21
3. Testes automatizados com Maven
4. Chamada do `Deploy Hook` de `staging` quando houver push em `develop`
5. Chamada do `Deploy Hook` de `production` quando houver push em `main`

O workflow usa o `commit SHA` atual ao chamar o hook, para o Render implantar exatamente o commit validado pelo CI.

## Validacao final

Depois de configurar tudo:

1. Fazer um commit pequeno na branch `develop`.
2. Confirmar no GitHub Actions que `Build and Test` e `Deploy to Render Staging` passaram.
3. Abrir a URL do ambiente de `staging`.
4. Testar `GET /api/health`.
5. Fazer merge ou novo commit na branch `main`.
6. Confirmar no GitHub Actions que `Build and Test` e `Deploy to Render Production` passaram.
7. Abrir a URL do ambiente de `production`.
8. Testar `GET /api/health`.

O endpoint `/api/health` agora informa o ambiente atual, o que ajuda nos prints da entrega.

## Fontes oficiais usadas

- Blueprint YAML:
  https://render.com/docs/blueprint-spec
- Deploy Hooks:
  https://render.com/docs/deploy-hooks
- Deploys:
  https://render.com/docs/deploys/
- Render Postgres:
  https://render.com/docs/postgresql-creating-connecting
- Free instances:
  https://render.com/docs/free
