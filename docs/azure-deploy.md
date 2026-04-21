# Guia de Deploy no Azure

Este projeto foi preparado para deploy automatizado em dois ambientes no Azure:

- `staging` na branch `develop`
- `production` na branch `main`

## Arquitetura sugerida

- 1 `Resource Group`
- 2 `Azure App Service` Linux
- 1 `Azure Database for PostgreSQL Flexible Server`
- 2 bancos no PostgreSQL:
  - `projeto_esg_staging`
  - `projeto_esg_production`

## Recursos a criar no Azure

### App Services

Crie dois apps:

- `ecocidade-esg-api-staging`
- `ecocidade-esg-api-production`

Configuracao recomendada:

- Sistema operacional: Linux
- Runtime stack: Java 21 SE
- Startup Command:

```bash
java -jar /home/site/wwwroot/ecocidade-esg-api-1.0.0.jar
```

### Banco de dados

Crie um `Azure Database for PostgreSQL Flexible Server` e depois dois bancos:

```sql
CREATE DATABASE projeto_esg_staging;
CREATE DATABASE projeto_esg_production;
```

## Secrets do GitHub Actions

Cadastre estes secrets no repositorio:

- `AZURE_CLIENT_ID`
- `AZURE_TENANT_ID`
- `AZURE_SUBSCRIPTION_ID`
- `AZURE_RESOURCE_GROUP`
- `AZURE_WEBAPP_NAME_STAGING`
- `AZURE_WEBAPP_NAME_PRODUCTION`
- `AZURE_APPSETTING_DB_URL_STAGING`
- `AZURE_APPSETTING_DB_USERNAME_STAGING`
- `AZURE_APPSETTING_DB_PASSWORD_STAGING`
- `AZURE_APPSETTING_DB_URL_PRODUCTION`
- `AZURE_APPSETTING_DB_USERNAME_PRODUCTION`
- `AZURE_APPSETTING_DB_PASSWORD_PRODUCTION`

## Como o pipeline funciona

O workflow em `.github/workflows/ci-cd.yml` executa:

1. Build e testes.
2. Gera o `.jar`.
3. Em `develop`, publica no App Service de staging.
4. Em `main`, publica no App Service de producao.

Durante o deploy, o GitHub Actions atualiza os `App Settings` com as credenciais do banco de cada ambiente.

## Fontes oficiais usadas

- Azure App Service com GitHub Actions:
  https://learn.microsoft.com/en-us/azure/app-service/deploy-github-actions
- Java SE no Azure App Service:
  https://learn.microsoft.com/en-us/azure/app-service/configure-language-java-deploy-run
- Azure App Service app settings:
  https://learn.microsoft.com/azure/app-service/reference-app-settings
- Azure Database for PostgreSQL Flexible Server:
  https://learn.microsoft.com/en-us/azure/postgresql/flexible-server/quickstart-create-server-portal
