# Dementia API

## Descrição
A **Dementia API** é uma API RESTful desenvolvida em Java utilizando o framework Spring Boot. Ela se conecta a um banco de dados PostgreSQL e oferece funcionalidades diversas. Além disso, a API implementa autenticação via JWT e utiliza o Spring Security para proteger as rotas.

## Pré-requisitos
Antes de executar o projeto, certifique-se de ter instalado:

- [Java 17+](https://www.oracle.com/java/technologies/javase-jdk17-downloads.html)
- [Maven 3.8+](https://maven.apache.org/download.cgi)
- Banco de dados PostgreSQL
- [Git](https://git-scm.com/)

## Configuração do banco de dados
1. Instale o PostgreSQL e crie um banco de dados para a aplicação.

2. No arquivo `application.properties` ou `application.yml`, configure as seguintes propriedades:

```properties
# application.properties
spring.datasource.url=jdbc:postgresql://localhost:5432/seu_banco_de_dados
spring.datasource.username=seu_usuario
spring.datasource.password=sua_senha

spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
````

## Passos para executar o projeto

### 1. Clonar o repositório
Se você ainda não clonou o repositório do projeto, faça isso utilizando o comando Git:

```bash
git clone https://seu-repositorio-url.git
cd demencia-api

mvn clean install

mvn spring-boot:run
````

## 4. Acessar a API
Após a execução, a aplicação estará disponível em:
http://localhost:8080

Você pode acessar a documentação da API gerada automaticamente pelo SpringDoc OpenAPI na seguinte URL:
http://localhost:8080/swagger-ui.html

## 5. Testes
Para executar os testes, utilize o comando:
mvn test

## 6. JWT e Autenticação
A API utiliza JWT (JSON Web Token) para autenticação. O fluxo de autenticação padrão envolve a geração de um token na rota de login, que deve ser incluído no cabeçalho das requisições subsequentes.

Exemplo de envio de token em uma requisição:
Authorization: Bearer <seu_token_jwt>

## Dependências utilizadas
O projeto utiliza as seguintes dependências principais:

Spring Boot Starter Web: Para construir APIs RESTful.
Spring Boot Starter Data JPA: Para persistência e comunicação com o banco de dados.
PostgreSQL Driver: Para conexão com o banco de dados PostgreSQL.
Spring Security: Para segurança e autenticação via JWT.
SpringDoc OpenAPI: Para gerar e exibir a documentação da API no Swagger.
Spring Boot DevTools: Para facilitar o desenvolvimento com hot reload.
Java JWT (Auth0): Para criação e validação de tokens JWT.

## Considerações finais
### Certifique-se de que o PostgreSQL está rodando e que o usuário/credenciais fornecidos estão corretos no arquivo application.properties. A API foi configurada para rodar em localhost:8080, mas você pode alterar essa porta nas configurações do Spring Boot.
