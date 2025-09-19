GHMAuthMarket API

A GHMAuthMarket é uma API RESTful desenvolvida com Spring Boot e Java 17, que utiliza JWT (JSON Web Token) para autenticação e autorização. O sistema permite o cadastro e login de usuários, gerenciamento de produtos e pedidos, garantindo segurança e acesso restrito a rotas protegidas. O projeto utiliza PostgreSQL como banco de dados e segue a arquitetura em camadas para promover organização, escalabilidade e manutenção do código.

📋 Funcionalidades
Usuários

Cadastro: Registro de novos usuários com senhas criptografadas (BCrypt).
Login: Autenticação com geração de token JWT.
Atualização de Senha: Apenas o próprio usuário pode alterar sua senha.
Exclusão de Usuário: Remoção de usuário com verificação de permissões.

Produtos

Cadastro: Criação de produtos, restrita a usuários autenticados.
Atualização: Edição de informações de produtos existentes.
Listagem: Visualização de todos os produtos cadastrados.

Pedidos (Opcional)

Criação: Registro de pedidos vinculados a usuários autenticados.
Itens do Pedido: Cadastro de itens utilizando DTOs Request/Response.
Listagem: Exibição de pedidos por usuário.


🛠 Tecnologias Utilizadas

Java 17: Linguagem principal.
Spring Boot 3: Framework para construção da API.
Spring Security com JWT: Autenticação e autorização.
Spring Data JPA / Hibernate: Persistência de dados.
PostgreSQL: Banco de dados relacional.
Lombok: Redução de código boilerplate.
Maven: Gerenciamento de dependências e build.
Postman/Insomnia: Ferramentas para testes de API.


📂 Estrutura do Projeto
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

Controller: Recebe requisições HTTP, valida dados e delega à camada Service.
Service: Contém a lógica de negócio.
Repository: Gerencia operações CRUD com o banco via JPA/Hibernate.
Entity: Representa as tabelas no banco de dados.

Benefícios:

Separação clara de responsabilidades.
Código organizado, testável e escalável.


🔒 Autenticação com JWT

Após o login, o usuário recebe um token JWT.
O token deve ser incluído no header de requisições protegidas:Authorization: Bearer <token>


Rotas protegidas (ex.: /api/produtos/register) retornam 401 Unauthorized se o token estiver ausente ou inválido.


📝 Exemplos de DTOs
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


🗄 Configuração do Banco de Dados
No arquivo application.properties ou application.yml:
spring.datasource.url=jdbc:postgresql://localhost:5432/ghmauthmarket
spring.datasource.username=seu_usuario
spring.datasource.password=sua_senha
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true


🚀 Como Executar o Projeto

Clone o repositório:
git clone https://github.com/seu_usuario/ghmauthmarket.git


Configure o PostgreSQL:

Crie um banco de dados chamado ghmauthmarket.
Atualize as credenciais no arquivo application.properties.


Compile e execute:
mvn clean install
mvn spring-boot:run


Teste a API:

Utilize Postman ou Insomnia para testar as rotas:
Cadastro: POST /api/usuarios/register
Login: POST /api/usuarios/login
Cadastrar Produto: POST /api/produtos/register (com token JWT)
Criar Pedido: POST /api/pedidos/register (com token JWT)




Coleção Postman:

Download da coleção Postman (link fictício, adicione o real se disponível).




📊 Diagrama UML (Simplificado)
Usuario 1 --- * Produto
Usuario 1 --- * Pedido
Pedido 1 --- * ItemPedido
Produto
