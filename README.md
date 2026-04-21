# Projeto - EcoCidade ESG API

API REST em Java Spring Boot para gerenciamento de indicadores ESG de cidades inteligentes.

## Como executar localmente com Docker

1. Copie o arquivo `.env.example` para `.env`.
2. Suba a aplicacao e o banco com Docker Compose:

```bash
docker compose --env-file .env up --build -d
```

3. Acesse os endpoints locais:

- Health: http://localhost:8080/api/health
- Cities: http://localhost:8080/api/cities

4. Para encerrar os containers:

```bash
docker compose --env-file .env down
```

### Evidencia local validada

- Aplicacao em execucao na porta `8080`
- Banco PostgreSQL saudavel na porta `5432`
- `GET /api/health` respondendo:

```json
{
  "status": "UP",
  "servico": "ecocidade-esg-api",
  "dataHora": "2026-04-21T01:17:04.893136268"
}
```

- `GET /api/cities` respondendo:

```json
[
  {
    "id": 2,
    "nomeCidade": "Curitiba",
    "codigoIbge": "4106902",
    "siglaEstado": "PR",
    "populacao": 1963726,
    "percentualEnergiaRenovavel": 73.50,
    "percentualReciclagemResiduos": 68.20,
    "notaInclusaoSocial": 81.00,
    "notaEsg": 74.23,
    "dataReferencia": "2026-04-20"
  }
]
```

## Pipeline CI/CD

Ferramenta utilizada: `GitHub Actions`.

Etapas do pipeline:

1. Checkout do repositorio.
2. Configuracao do Java 21.
3. Execucao dos testes automatizados com Maven.
4. Empacotamento da aplicacao em `.jar`.
5. Deploy automatizado em `staging` quando houver push na branch `develop`.
6. Deploy automatizado em `production` quando houver push na branch `main`.

Funcionamento do pipeline:

- `build-test`: executa build e testes.
- `deploy-staging`: publica no Azure App Service de staging.
- `deploy-production`: publica no Azure App Service de producao.

Links para adicionar apos subir o projeto:

- Repositorio GitHub: `ADICIONAR_LINK_DO_REPOSITORIO`
- Workflow Actions: `ADICIONAR_LINK_DA_ABA_ACTIONS`
- Staging Azure: `ADICIONAR_LINK_DO_STAGING`
- Production Azure: `ADICIONAR_LINK_DO_PRODUCTION`

## Containerizacao

### Conteudo do Dockerfile

```dockerfile
FROM maven:3.9.9-eclipse-temurin-21 AS build
WORKDIR /app

COPY pom.xml .
COPY src ./src

RUN mvn -B clean package -DskipTests

FROM eclipse-temurin:21-jre
WORKDIR /app

COPY --from=build /app/target/ecocidade-esg-api-1.0.0.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]
```

### Estrategias adotadas

- Build multi-stage para reduzir o tamanho da imagem final.
- Separacao entre etapa de compilacao e etapa de execucao.
- Uso de PostgreSQL em container separado.
- Uso de `docker-compose.yml` para orquestrar aplicacao e banco.
- Uso de volume nomeado para persistencia do banco.
- Uso de variaveis de ambiente para credenciais e configuracoes.
- Uso de rede dedicada para comunicacao entre os servicos.
- Uso de `healthcheck` para aguardar o banco antes de subir a API.

## Prints do funcionamento

Inclua nesta secao os prints ou links de execucao, deploy e funcionamento em staging e producao.

### Prints locais que voce pode tirar agora

1. Navegador aberto em `http://localhost:8080/api/health`
2. Navegador aberto em `http://localhost:8080/api/cities`
3. Terminal com:

```bash
docker compose --env-file .env ps
```

Saida validada:

```text
NAME              IMAGE                                  COMMAND             STATUS
projeto-esg-app   ghcr.io/owner/projeto-esg-api:latest   java -jar app.jar   Up
projeto-esg-db    postgres:16-alpine                     postgres            Up (healthy)
```

### Prints para adicionar depois do GitHub e Azure

- Pipeline `build-test` executando
- Pipeline de deploy em `staging`
- Pipeline de deploy em `production`
- Link do ambiente `staging` funcionando
- Link do ambiente `production` funcionando

Observacao:

- Os endpoints locais ja foram ajustados para exibir os campos em portugues, o que facilita os prints da entrega.

## Tecnologias utilizadas

- Java 21
- Spring Boot 3.4.5
- Spring Web
- Spring Data JPA
- Spring Validation
- Spring Boot Actuator
- PostgreSQL
- H2 Database
- Docker
- Docker Compose
- GitHub Actions
- Azure App Service
- Azure Database for PostgreSQL Flexible Server
