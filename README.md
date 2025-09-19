GHMAuthMarket API
Descrição

O GHMAuthMarket é uma API REST
desenvolvida com Spring Boot, que utiliza JWT (JSON Web Token) para autenticação e autorização. O sistema permite que usuários se cadastrem, façam login e realizem operações de cadastro de produtos e pedidos, garantindo que apenas usuários autenticados possam acessar determinadas rotas.

O projeto integra PostgreSQL como banco de dados e segue a arquitetura em camadas (Controller → Service → Repository → Database) para facilitar manutenção e organização do código.

Funcionalidades
Usuários

Cadastro de novos usuários com senha criptografada (BCrypt).

Login com geração de token JWT.

Atualização de senha apenas pelo próprio usuário.

Exclusão de usuário com verificação de permissões.

Produtos

Cadastro de produtos somente para usuários autenticados.

Atualização de produtos.

Listagem de todos os produtos.

Pedidos (Opcional)

Criação de pedidos vinculados ao usuário autenticado.

Cadastro de itens do pedido utilizando DTO Request/Response.

Listagem de pedidos por usuário.

Tecnologias Utilizadas

Java 17

Spring Boot 3

Spring Security com JWT

Spring Data JPA / Hibernate

PostgreSQL

Lombok

Maven

Postman/Insomnia para testes de API

Estrutura do Projeto
src/main/java/br/com/judev/ghmauthmarket
│
├── controller/          # Endpoints da API
├── dto/                 # DTOs de Request e Response
│   ├── Usuario
│   ├── Produto
│   └── Pedido
├── entity/              # Entidades JPA (Usuario, Produto, Pedido, ItemPedido)
├── repository/          # Repositórios Spring Data
├── service/             # Lógica de negócio
└── infra/
    ├── config/          # Configurações de segurança, JWT e Spring Security
    └── security/        # Filtros, TokenService

Arquitetura

O projeto segue a Arquitetura em Camadas:

Controller: Recebe as requisições HTTP, valida dados e chama a camada Service.

Service: Contém regras de negócio e lógica principal da aplicação.

Repository: Faz a comunicação com o banco de dados (CRUD com JPA/Hibernate).

Entity: Representa os dados persistidos no PostgreSQL.

Benefícios:

Separação clara de responsabilidades.

Facilita manutenção e escalabilidade.

Código organizado e testável.

JWT (JSON Web Token)

Ao realizar login, o usuário recebe um token JWT.

Esse token deve ser enviado no header Authorization das requisições protegidas:

Authorization: Bearer <token>


Rotas protegidas (como cadastro de produtos) retornam 401 Unauthorized se o token estiver ausente ou inválido.

DTOs

Exemplos de DTOs:

CreateProdutoRequest

{
  "nome": "Produto Exemplo",
  "preco": 120.50
}


CreateProdutoResponse

{
  "id": 1,
  "nome": "Produto Exemplo",
  "preco": 120.50,
  "usuarioId": 1
}


CreatePedidoRequest

{
  "itens": [
    {
      "produtoId": 1,
      "quantidade": 2
    },
    {
      "produtoId": 2,
      "quantidade": 1
    }
  ]
}


CreatePedidoResponse

{
  "id": 1,
  "usuarioId": 1,
  "itens": [
    {
      "produtoId": 1,
      "quantidade": 2
    },
    {
      "produtoId": 2,
      "quantidade": 1
    }
  ],
  "valorTotal": 360.00
}

Configuração do Banco de Dados

No application.properties ou application.yml:

spring.datasource.url=jdbc:postgresql://localhost:5432/ghmauthmarket
spring.datasource.username=seu_usuario
spring.datasource.password=sua_senha
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true

Executando o Projeto

Clone o repositório:

git clone https://github.com/seu_usuario/ghmauthmarket.git


Configure o banco PostgreSQL.

Compile e rode o projeto:

mvn clean install
mvn spring-boot:run


Teste a API no Postman ou Insomnia:

Cadastro: POST /api/usuarios/register

Login: POST /api/usuarios/login

Cadastrar produto: POST /api/produtos/register (com token JWT no header)

Criar pedido: POST /api/pedidos/register (com token JWT)

UML Classes

Exemplo Simplificado:

Usuario 1 --- * Produto
Usuario 1 --- * Pedido
Pedido 1 --- * ItemPedido
Produto 1 --- * ItemPedido


Usuario → contém id, nome, email, senha

Produto → contém id, nome, preço, referência ao usuário

Pedido → contém id, usuário, lista de itens e valor total

ItemPedido → contém id, produto, quantidade

Colaboração do Grupo

Membro 1: Implementação de autenticação JWT e cadastro/login de usuários.

Membro 2: CRUD de produtos e associação com usuário.

Membro 3: CRUD de pedidos e itens do pedido, criação de DTOs.

Observações Finais

Código limpo, organizado e comentado.

Arquitetura em camadas facilita escalabilidade.

JWT garante que apenas usuários autenticados realizem operações sensíveis.

A API está pronta para ser testada via Postman ou Insomnia.

Se você quiser, posso criar uma versão ainda mais “top” com badges, links para Postman e instruções de testes de JWT, estilo README profissional de mercado.

