# Gerenciamento de Usuários - Backend

## Introdução

Este projeto faz parte de um desafio técnico para desenvolver um sistema de gerenciamento de usuários para um grupo de
restaurantes.
O objetivo é criar uma solução backend robusta que permita operações de criação, atualização, exclusão e validação de
login de usuários, suportando dois tipos de usuários: Dono de Restaurante e Cliente.

## Objetivo do Projeto

O principal objetivo deste projeto é melhorar a eficiência operacional dos restaurantes, permitindo que eles gerenciem
seus usuários de forma centralizada. Além disso, o sistema visa facilitar a interação dos clientes com os
estabelecimentos, oferecendo uma plataforma onde eles podem se cadastrar, acessar informações e realizar operações de
forma segura e eficiente.

## Requisitos do Sistema

Para executar este projeto, você precisará dos seguintes requisitos de sistema:

- **Sistema Operacional**: Windows, macOS ou Linux
- **Memória RAM**: Pelo menos 4 GB recomendados
- **Espaço em Disco**: Pelo menos 500 MB de espaço livre
- **Software**:
    - Docker e Docker Compose
    - Java JDK 11 ou superior
    - Maven 3.6 ou superior

## Estrutura do Projeto

A estrutura do projeto é organizada da seguinte forma:
projeto-finalizado/
│
├── src/
│ ├── main/
│ │ ├── java/
│ │ │ ├── com/
│ │ │ │ ├── alex.fiap/
│ │ │ │ │ ├── controller/
│ │ │ │ │ ├── model/
│ │ │ │ │ ├── service/
│ │ │ │ │ ├── repository/
│ │ │ │ │ ├── security/
│ │ │ │ │ ├── exception/
│ │ │ │ │ ├── mapper/
│ │ │ │ │ ├── request/
│ │ │ │ │ ├── response/
│ │ │ │ │ └── error/
│ │ └── resources/
│ │ ├── application.properties
│ │ └── ...
└── docker-compose.yml

## Segurança

A segurança do sistema é tratada com o uso do Spring Security, que fornece autenticação e autorização robustas. As
principais medidas de segurança implementadas incluem:

- **Autenticação**: Os usuários devem fornecer credenciais válidas (login e senha) para acessar o sistema. As senhas são
  armazenadas de forma segura usando hashing.
- **Autorização**: Diferentes níveis de acesso são concedidos com base no tipo de usuário (Dono de Restaurante ou
  Cliente), garantindo que cada usuário só possa acessar as funcionalidades apropriadas.
- **Proteção contra Ataques Comuns**: O sistema é configurado para proteger contra ataques comuns, como CSRF (Cross-Site
  Request Forgery) e XSS (Cross-Site Scripting), através de cabeçalhos de segurança e validação de entrada.

## Visão Geral do Projeto

Este projeto é um backend completo desenvolvido com o framework Spring Boot, utilizando PostgreSQL como banco de dados.
O sistema é projetado para gerenciar usuários, permitindo operações de criação, atualização, exclusão e validação de
login. O sistema suporta dois tipos de usuários: **Dono de Restaurante** e **Cliente**.

## Arquitetura

A arquitetura do projeto segue o padrão MVC (Model-View-Controller) e é organizada em pacotes que separam as
responsabilidades:

- **Model**: Contém as classes que representam as entidades do sistema, como `User`, `Customer`, `RestaurantOwner`, e
  `Endereco`. Essas classes definem a estrutura dos dados que serão manipulados pela aplicação.

- **Controller**: Contém as classes que gerenciam as requisições HTTP e interagem com os serviços, como
  `UserController`. Os controladores são responsáveis por receber as solicitações do cliente, processá-las e retornar as
  respostas apropriadas.

- **Service**: Contém a lógica de negócios, como `UserService`, que manipula as operações relacionadas aos usuários.
  Essa camada é responsável por implementar as regras de negócio e orquestrar as interações entre os repositórios e os
  controladores.

- **Repository**: Contém as interfaces que abstraem a lógica de acesso a dados, como `UserRepository`. Os repositórios
  são responsáveis por realizar operações de CRUD (Create, Read, Update, Delete) nas entidades.

- **Mapper**: Utiliza o MapStruct para converter entre entidades e DTOs (Data Transfer Objects), facilitando a
  transferência de dados entre camadas. Isso ajuda a desacoplar a representação interna das entidades da API.

- **Exception**: Contém classes para tratamento de exceções personalizadas. Essa camada é responsável por capturar e
  gerenciar erros que podem ocorrer durante a execução da aplicação.

- **Security**: Contém configurações e classes relacionadas à segurança da aplicação, como autenticação e autorização.
  Isso pode incluir a configuração do Spring Security para proteger endpoints e gerenciar usuários.

- **Swagger**: Configurações para a documentação da API usando Swagger/OpenAPI. Isso permite que os desenvolvedores
  visualizem e testem os endpoints da API de forma interativa.

- **Request**: Contém classes que representam as solicitações feitas à API, como `UserRequest`. Essas classes são usadas
  para validar e transferir dados de entrada.

- **Response**: Contém classes que representam as respostas da API, como `ApiResponse`. Essas classes são usadas para
  padronizar a saída da API e fornecer informações sobre o resultado das operações.

- **Error**: Contém classes que definem a estrutura das mensagens de erro retornadas pela API. Isso inclui classes como
  `ErrorResponse`, que padronizam a forma como os erros são comunicados aos clientes, facilitando a identificação e
  resolução de problemas.

## Princípios de Design e Padrões de Projeto

### Princípios de Design

1. **Single Responsibility Principle (SRP)**:
    - Cada classe tem uma única responsabilidade. Por exemplo, a classe `UserService` é responsável apenas pela lógica
      de negócios relacionada aos usuários, enquanto o `UserController` lida com as requisições HTTP.

2. **Open/Closed Principle (OCP)**:
    - As classes estão abertas para extensão, mas fechadas para modificação. Por exemplo, você pode adicionar novos
      métodos ao `UserService` ou criar novos controladores sem modificar as classes existentes.

3. **Liskov Substitution Principle (LSP)**:
    - Embora não haja herança explícita em seu código, o uso de interfaces e classes base (como `User`, `Customer`, e
      `RestaurantOwner`) permite que subclasses sejam usadas no lugar da superclasse sem quebrar a funcionalidade.

4. **Interface Segregation Principle (ISP)**:
    - O código não apresenta interfaces grandes e complexas. Em vez disso, as classes são projetadas para implementar
      apenas os métodos que realmente precisam.

5. **Dependency Inversion Principle (DIP)**:
    - O código depende de abstrações (interfaces) em vez de implementações concretas. Por exemplo, o `UserService`
      depende do `UserRepository`, que é uma interface, permitindo que você altere a implementação sem afetar o serviço.

### Padrões de Projeto

1. **MVC (Model-View-Controller)**:
    - A estrutura segue o padrão MVC, onde:
        - **Model**: Representado pelas classes de modelo (`User`, `Endereco`, etc.).
        - **Controller**: As classes de controlador (`UserController`) gerenciam as requisições e interagem com os
          serviços.
        - **View**: Embora não exista uma camada de visualização tradicional, as respostas JSON podem ser vistas como
          uma forma de "view".

2. **Repository Pattern**:
    - O uso do `UserRepository` para abstrair a lógica de acesso a dados é um exemplo clássico desse padrão. Ele permite
      que a lógica de persistência seja separada da lógica de negócios.

3. **Service Layer Pattern**:
    - A classe `UserService` encapsula a lógica de negócios, separando-a da lógica de controle. Isso facilita a
      manutenção e a testabilidade.

4. **Data Transfer Object (DTO)**:
    - O uso de classes como `UserRequest` e `UserResponse` para transferir dados entre camadas é uma boa prática que
      ajuda a desacoplar as representações internas das entidades da API.

5. **Mapper Pattern**:
    - O uso do MapStruct para conversão entre entidades e DTOs é uma implementação do padrão Mapper, que ajuda a manter
      a separação entre as diferentes representações de dados.

6. **Exception Handling Pattern**:
    - O tratamento de exceções usando `@ControllerAdvice` é uma abordagem comum para centralizar o gerenciamento de
      erros em aplicações Spring.

## Interação entre as Partes do Sistema

1. **Requisição do Cliente**: O cliente faz uma requisição HTTP para um endpoint específico (por exemplo,
   `/users/login`).
2. **Controller**: O `UserController` recebe a requisição e chama o método apropriado no `UserService`.
3. **Service**: O `UserService` executa a lógica de negócios necessária, como validar o login ou atualizar os dados do
   usuário.
4. **Repository**: O `UserService` interage com o `UserRepository` para realizar operações no banco de dados.
5. **Resposta ao Cliente**: Após processar a requisição, o `UserController` retorna uma resposta ao cliente, que pode
   incluir dados ou mensagens de erro.

## Tecnologias Utilizadas

- **Spring Boot**: Framework para desenvolvimento de aplicações Java.
- **PostgreSQL**: Sistema de gerenciamento de banco de dados relacional.
- **MapStruct**: Biblioteca para mapeamento de objetos.
- **Lombok**: Biblioteca para reduzir o boilerplate de código em classes Java.

## Configuração do Docker Compose Para configurar e

subir a aplicação Java com o banco de dados PostgreSQL,
siga os passos abaixo:

### Pré-requisitos

Antes de executar o projeto, certifique-se de ter as seguintes ferramentas instaladas:

- **Docker**
- **Docker Compose**
- **Java JDK 11 ou superior**
- **Maven 3.6 ou superior**

### Configuração do Banco de Dados

1. Crie um banco de dados no PostgreSQL:
   ```sql 
   CREATE DATABASE postgres```;

2. Atualize as credenciais no arquivo src/main/resources/application.properties:
   spring.datasource.url=jdbc:postgresql://localhost:5432/postgres
   spring.datasource.username=postgres
   spring.datasource.password=postgres
   spring.jpa.hibernate.ddl-auto=update
   spring.jpa.properties.hibernate.default_schema=fiap
   spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.PostgreSQLDialect
   logging.level.org.springframework.security=DEBUG
   spring.jpa.open-in-view=false
   spring.jpa.show-sql=true
   logging.level.org.springframework=DEBUG
   springdoc.swagger-ui.path=/swagger-ui.html
   springdoc.api-docs.path=/api-doc

`### Arquivo docker-compose.yml`
version: '3.8'

services:
db:
image: postgres:12
container_name: postgres_db
environment:
POSTGRES_DB: postgres
POSTGRES_USER: postgres
POSTGRES_PASSWORD: postgres
ports:

- "5432:5432"
  volumes:
- pgdata:/var/lib/postgresql/data

app:
image: openjdk:11
container_name: java_app
environment:
SPRING_DATASOURCE_URL: jdbc:postgresql://db:5432/postgres
SPRING_DATASOURCE_USERNAME: postgres
SPRING_DATASOURCE_PASSWORD: postgres
SPRING_JPA_HIBERNATE_DDL_AUTO: update
SPRING_JPA_PROPERTIES_HIBERNATE_DEFAULT_SCHEMA: fiap
SPRING_JPA_PROPERTIES_HIBERNATE_DIALECT: org.hibernate.dialect.PostgreSQLDialect
LOGGING_LEVEL_ORG_SPRINGFRAMEWORK_SECURITY: DEBUG
SPRING_JPA_OPEN_IN_VIEW: false
SPRING_JPA_SHOW_SQL: true
LOGGING_LEVEL_ORG_SPRINGFRAMEWORK: DEBUG
SPRINGDOC_SWAGGER_UI_PATH: /swagger-ui.html
SPRINGDOC_API_DOCS_PATH: /api-doc
ports:

- "8080:8080"
  volumes:
- .:/app
  command: ["sh", "-c", "cd /app && ./mvnw spring-boot:run"]

volumes:
pgdata:

### Passos para Executar o Docker Compose

1 - Certifique-se de que o Docker e o Docker Compose estejam instalados na sua máquina.
2 - Navegue até o diretório onde está localizado o arquivo docker-compose.yml.
3 - Execute o seguinte comando para iniciar os contêineres:
docker-compose up
4 - A aplicação estará disponível em http://localhost:8080
e o banco de dados PostgreSQL estará rodando em localhost:5432.

## Link para a Collection do Postman

[Baixe a Collection do Postman aqui]
(https://github.com/amouraorr/projeto-fiap-v4/tree/projeto-validacoes/postman-collections/pos-fiap-2025.postman_collection.json)

### Como importar a Collection do Postman

1. Baixe o arquivo JSON da collection usando o link acima.
2. Abra o Postman.
3. Clique no ícone de importar (Import) no canto superior esquerdo.
4. Selecione "Upload Files".
5. Escolha o arquivo JSON baixado e clique em "Open".
6. A coleção será importada para o Postman e estará disponível para uso.

## Como Executar o Projeto

1. Clone o repositório:
   bash
   git clone https://github.com/amouraorr/projeto-fiap-v4.git
   cd projeto-finalizado

2. Configure o banco de dados PostgreSQL e atualize as credenciais no arquivo application.properties.
   spring.datasource.url=jdbc:postgresql://localhost:5432/postgres
   spring.datasource.username=postgres
   spring.datasource.password=postgres

3. Execute a aplicação:
   ./mvnw spring-boot:run
4. Acesse a documentação da API em http://localhost:8080/swagger-ui.html.

### Endpoints da API, abaixo estão os principais endpoints disponíveis na API:

1. Login do Usuário
   Endpoint: POST /users/login
   Descrição: Valida as credenciais do usuário e retorna verdadeiro se forem válidas.
   Request Body:
   {
   "login": "usuario_exemplo",
   "senha": "senha_exemplo"
   }
   Response:
   200 OK: Retorna um objeto com o status da validação.
   {
   "data": true,
   }
   401 Unauthorized: Retorna um erro se as credenciais forem inválidas.   
   {
   "error": "Credenciais inválidas."
   }
2. Criar Usuário
   Endpoint: POST /users
   Descrição: Cria um novo usuário.
   Request Body:
   {
   "nome": "Nome do Usuário",
   "email": "usuario@example.com",
   "login": "usuario_exemplo",
   "senha": "senha_exemplo",
   "address": {
   "rua": "Rua Exemplo",
   "cidade": "Cidade Exemplo",
   "estado": "Estado Exemplo",
   "cep": "00000-000"
   }
   }
   Response:
   201 Created: Retorna o usuário criado.

   {
   "data": {
   "id": 1,
   "nome": "Nome do Usuário",
   "email": "usuario@example.com",
   "login": "usuario_exemplo",
   "address": {
   "rua": "Rua Exemplo",
   "cidade": "Cidade Exemplo",
   "estado": "Estado Exemplo",
   "cep": "00000-000"
   }
   }

3. Atualizar Usuário
   Endpoint: PUT /users/{id}
   Descrição: Atualiza os dados de um usuário existente.
   Request Body:
   {
   "nome": "Novo Nome",
   "email": "novo_email@example.com",
   "login": "novo_login",
   "senha": "nova_senha",
   "address": {
   "rua": "Nova Rua",
   "cidade": "Nova Cidade",
   "estado": "Novo Estado",
   "cep": "11111-111"
   }
   }
   Response:
   200 OK: Retorna o usuário atualizado.
   404 Not Found: Retorna um erro se o usuário não for encontrado.

4. Trocar Senha
   Endpoint: PATCH /users/{id}/change-password
   Descrição: Altera a senha de um usuário.
   Request Body:
   {
   "newPassword": "nova_senha"
   }
   Response:
   200 OK: Retorna o usuário com a senha alterada.
   404 Not Found: Retorna um erro se o usuário não for encontrado.

5. Excluir Usuário
   Endpoint: DELETE /users/{id}
   Descrição: Exclui um usuário com base no ID fornecido.
   Response:
   204 No Content: Usuário excluído com sucesso.
   404 Not Found: Retorna um erro se o usuário não for encontrado.

## Contribuição

### Contribuições são bem-vindas! Siga estas etapas para contribuir:

1. Faça um fork do repositório.
2. Crie uma nova branch (`git checkout -b feature/nova-funcionalidade`).
3. Faça suas alterações e commit (`git commit -m 'Adiciona nova funcionalidade'`).
4. Envie para o repositório remoto (`git push origin feature/nova-funcionalidade`).
5. Abra um Pull Request.

## Licença

O projeto é privado ou não está sob uma licença específica.

## Referências e Recursos

- [Spring Boot Documentation](https://spring.io/projects/spring-boot)
- [PostgreSQL Documentation](https://www.postgresql.org/docs/)
- [MapStruct Documentation](https://mapstruct.org/documentation/stable/reference/html/)
- [Lombok Documentation](https://projectlombok.org/)

## Exemplo de Uso

Exemplos de uso da API, mostrando como fazer requisições com ferramentas como curl ou Postman

### Criar um Usuário

```bash
curl -X POST http://localhost:8080/users \
-H "Content-Type: application/json" \
-d '{
  "nome": "Nome do Usuário",
  "email": "usuario@example.com",
  "login": "usuario_exemplo",
  "senha": "senha_exemplo",
  "address": {
    "rua": "Rua Exemplo",
    "cidade": "Cidade Exemplo",
    "estado": "Estado Exemplo",
    "cep": "00000-000"
  }
}'

### Login do Usuário
curl -X POST http://localhost:8080/users/login \
-H "Content-Type: application/json" \
-d '{
  "login": "usuario_exemplo",
  "senha": "senha_exemplo"
}'

### Atualizar Usuário
curl -X PUT http://localhost:8080/users/1 \
-H "Content-Type: application/json" \
-d '{
  "nome": "Novo Nome",
  "email": "novo_email@example.com",
  "login": "novo_login",
  "senha": "nova_senha",
  "address": {
    "rua": "Nova Rua",
    "cidade": "Nova Cidade",
    "estado": "Novo Estado",
    "cep": "11111-111"
  }
}'

### Trocar Senha
curl -X PATCH http://localhost:8080/users/1/change-password \
-H "Content-Type: application/json" \
-d '{
  "newPassword": "nova_senha"
}'

### Excluir Usuário 
curl -X DELETE http://localhost:8080/users/1

## Conclusão

Aplicação incorpora vários princípios de design e padrões de projeto que promovem a manutenibilidade, extensibilidade e clareza do código. 
Esses princípios e padrões ajudam a garantir que a aplicação possa evoluir ao longo do tempo sem se tornar difícil de entender ou modificar.