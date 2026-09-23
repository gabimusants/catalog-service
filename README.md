# Catalog Service

Microsserviço de catálogo de produtos, usando MongoDB. Desenvolvido como parte
de um projeto de estudo em Java + Spring Boot, complementando o `stock-manager`
(que usa PostgreSQL para o domínio transacional de pedidos).

## Por que MongoDB aqui

Diferente de um catálogo com schema fixo, produtos de categorias diferentes
frequentemente têm atributos completamente diferentes entre si (uma cerveja
tem "teor alcoólico"; um parafuso tem "diâmetro"). O MongoDB permite modelar
isso com um campo de atributos flexível (`Map<String, Object>`), sem forçar
um schema relacional rígido com várias colunas vazias.

Esse projeto é um exemplo de **persistência poliglota**: cada serviço usa o
banco de dados mais adequado ao seu próprio domínio.

## Stack

- Java 21
- Spring Boot 4.1 (Web, Data MongoDB, Validation, Actuator)
- MongoDB
- Lombok
- JUnit 5 + Mockito + AssertJ + Testcontainers
- Maven

## Pré-requisitos

- Java 21+
- Docker Desktop rodando

## Configuração local

### 1. Clone e configure as variáveis de ambiente

```bash
cp .env.example .env
```

Edite o `.env` e defina uma senha:

```
DB_NAME=catalog
DB_USERNAME=catalog_user
DB_PASSWORD=escolha_uma_senha
```


### 2. Suba o MongoDB

```bash
docker compose up -d
```

### 3. Exporte as variáveis para a aplicação

O Spring lê variáveis de ambiente do sistema, não o `.env` diretamente. Exporte
as mesmas variáveis na sessão do terminal (ou use o script `dev.ps1`/`dev.sh`,
se você tiver criado um):

**PowerShell:**
```powershell
$env:DB_NAME="catalog"
$env:DB_USERNAME="catalog_user"
$env:DB_PASSWORD="a_mesma_senha_do_.env"
```

### 4. Rode a aplicação

```bash
.\mvnw.cmd spring-boot:run
```

A aplicação sobe em `http://localhost:8081` (porta diferente do `stock-manager`,
que usa 8080, já que os dois serviços rodam simultaneamente).

### 5. Valide

```bash
curl http://localhost:8081/actuator/health
```


## Rodando os testes

```bash
.\mvnw.cmd test
```

Testes de repository usam Testcontainers (sobe um MongoDB real via Docker
automaticamente); testes de Service usam Mockito (sem infraestrutura).

## Endpoints disponíveis

| Método | Rota | Descrição |
|---|---|---|
| POST | `/api/catalog-items` | Cria um item de catálogo |
| GET | `/api/catalog-items` | Lista todos os itens (aceita `?category=` opcional) |
| GET | `/api/catalog-items/{id}` | Busca um item por id |
| PUT | `/api/catalog-items/{id}` | Atualiza um item |
| DELETE | `/api/catalog-items/{id}` | Remove um item |

## Roteiro de evolução

- [x] Setup do projeto e infraestrutura (Docker/MongoDB)
- [x] Modelagem de documento com atributos flexíveis
- [x] CRUD completo (Controller/Service/DTO/Mapper)
- [x] Testes unitários (Mockito) e de integração (Testcontainers)
- [ ] Comunicação HTTP com o `stock-manager` (arquitetura de microsserviços)