# Documentacao Tecnica - EcoCidade ESG API

## Titulo do projeto

EcoCidade ESG API

## Integrantes

Preencha os nomes dos participantes do grupo.

## Descricao do pipeline

O pipeline foi implementado com GitHub Actions para automatizar o ciclo completo da aplicacao.

### Etapas

1. Checkout do repositorio.
2. Configuracao do Java 21.
3. Execucao dos testes automatizados com Maven.
4. Empacotamento da aplicacao em `.jar`.
5. Disparo do deploy automatizado para `staging` na branch `develop`.
6. Disparo do deploy automatizado para `production` na branch `main`.

### Logica adotada

- Pull requests validam qualidade com build e testes.
- Push em `develop` publica a aplicacao no Azure App Service de `staging`.
- Push em `main` publica a aplicacao no Azure App Service de `production`.
- Variaveis sensiveis ficam em GitHub Secrets, evitando credenciais no codigo.
- Cada ambiente usa configuracoes e credenciais proprias para reduzir risco de interferencia entre staging e producao.

## Docker

### Arquitetura

- `app`: API Spring Boot
- `db`: PostgreSQL
- `network`: rede bridge isolada
- `volume`: persistencia do banco

### Comandos usados

```bash
docker compose --env-file .env up --build -d
docker compose --env-file .env down
docker compose --env-file .env -f docker-compose.yml -f docker-compose.staging.yml up -d
docker compose --env-file .env -f docker-compose.yml -f docker-compose.production.yml up -d
```

### Estrategia da imagem

- Build multi-stage para reduzir tamanho final.
- Imagem final contem apenas JRE e artefato compilado.
- Portabilidade para ambientes locais e servidores Linux.

## Prints do pipeline

Inserir aqui:

- Print do job `build-test`.
- Print do deploy em staging.
- Print do deploy em producao.

## Prints dos ambientes

Inserir aqui:

- Aplicacao respondendo em staging no Azure.
- Aplicacao respondendo em producao no Azure.
- Banco conectado e containers saudaveis.

## Desafios encontrados e solucoes

### 1. Garantir simplicidade do codigo

Solucao:
Separacao em camadas pequenas, DTOs objetivos e validacoes declarativas com Bean Validation.

### 2. Evitar dependencia excessiva do ambiente local

Solucao:
Uso de Docker Compose para padronizar execucao local, Azure App Service + Azure Database for PostgreSQL para os ambientes externos e GitHub Actions para padronizar build, teste e deploy.

### 3. Reduzir chance de falha em deploy

Solucao:
Uso de separacao por branch (`develop` e `main`), bancos independentes por ambiente, `App Settings` configurados pelo pipeline e deploy automatizado do `.jar` via GitHub Actions no Azure.

## Conclusao

O projeto atende aos requisitos de pipeline CI/CD, conteinerizacao, orquestracao com Docker Compose, documentacao e preparacao para deploy automatizado em staging e producao.
