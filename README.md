# API de Pessoas - Teste Técnico Geospatial

Esta é uma API RESTful desenvolvida em Java 21 com Spring Boot 3, criada como solução para a avaliação técnica "Avaliação Java: SCCON - Geospatial".

A aplicação gerencia um cadastro de pessoas em memória, implementando um conjunto completo de endpoints CRUD, além de lógicas de negócio específicas para cálculo de idade e salário, conforme solicitado.

## ✨ Features Implementadas

Todos os requisitos da avaliação foram implementados, incluindo:

-   [x] **`GET /person`**: Retorna a lista de todas as pessoas, ordenada alfabeticamente por nome.
-   [x] **`POST /person`**: Inclui uma nova pessoa.
-   [x] **`GET /person/{id}`**: Retorna uma pessoa específica pelo seu ID.
-   [x] **`PUT /person/{id}`**: Atualiza uma pessoa existente (substituição total).
-   [x] **`PATCH /person/{id}`**: Atualiza um ou mais atributos de uma pessoa (atualização parcial).
-   [x] **`DELETE /person/{id}`**: Remove uma pessoa do sistema.
-   [x] **`GET /person/{id}/age`**: Calcula a idade de uma pessoa em anos, meses ou dias completos.
-   [x] **`GET /person/{id}/salary`**: Calcula o salário de uma pessoa em Reais (R$) ou em salários mínimos.
-   [x] **Tratamento de Erros Profissional**: A API retorna os códigos de status HTTP corretos para cada cenário (ex: `404 Not Found`, `409 Conflict`, `400 Bad Request`) com um corpo de resposta JSON padronizado para os erros.

## 🛠️ Tecnologias Utilizadas

-   **Java 21**
-   **Spring Boot 3**
-   **Maven**
-   **Lombok**
-   **MapStruct** para mapeamento de DTOs
-   **SpringDoc OpenAPI (Swagger)** para documentação da API

## 🚀 Como Executar o Projeto

### Pré-requisitos

-   JDK 21 ou superior
-   Maven 3.8+

### Passos

1.  **Clone o repositório:**
    ```bash
    git clone [URL_DO_SEU_REPOSITORIO]
    ```

2.  **Navegue até a pasta do projeto:**
    ```bash
    cd sccon-geospatial-test
    ```

3.  **Construa o projeto com o Maven:**
    ```bash
    mvn clean package
    ```

4.  **Execute a aplicação:**
    ```bash
    java -jar target/sccon-geospatial-test-0.0.1-SNAPSHOT.jar
    ```

A API estará disponível em `http://localhost:8080`.

## 📖 Documentação da API (Swagger UI)

Este projeto inclui uma documentação interativa da API gerada automaticamente com o SpringDoc OpenAPI. Após iniciar a aplicação, acesse ao seguinte endereço no seu navegador:

▶️ **[http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html)**

Na interface do Swagger, você poderá ver todos os endpoints em detalhe, os seus parâmetros, os DTOs de entrada e saída, e até mesmo testar a API diretamente pelo navegador.

## ⚙️ Exemplos de Requisições com cURL

Aqui estão alguns exemplos de como interagir com a API usando o cURL.

**1. Listar todas as pessoas:**
```bash
curl -X GET http://localhost:8080/person
```

**2. Buscar a pessoa com ID 1:**
```bash
curl -X GET http://localhost:8080/person/1
```

**3. Criar uma nova pessoa (sem ID):**
```bash
curl -X POST http://localhost:8080/person \
-H "Content-Type: application/json" \
-d '{
    "nome": "Ana Clara",
    "dataNascimento": "1995-07-30",
    "dataAdmissao": "2023-01-15"
}'
```

**4. Calcular a idade de José da Silva (ID 1) em anos:**
```bash
curl -X GET "http://localhost:8080/person/1/age?output=years"
```

**5. Calcular o salário de José da Silva (ID 1) em formato completo (R$):**
```bash
curl -X GET "http://localhost:8080/person/1/salary?output=full"
```

**6. Atualizar parcialmente (PATCH) o nome da pessoa com ID 2:**
```bash
curl -X PATCH http://localhost:8080/person/2 \
-H "Content-Type: application/json" \
-d '{
    "nome": "Maria de Souza"
}'
```

## 🏗️ Estrutura do Projeto

O projeto segue uma arquitetura em camadas para uma clara separação de responsabilidades:

-   **`api`**: Contém os Controllers, DTOs, Mappers e o Exception Handler. É a camada de fronteira que lida com o mundo HTTP.
-   **`domain`**: O coração da aplicação. Contém os Modelos de domínio (com a lógica de negócio), Repositórios e os Serviços que orquestram as operações.