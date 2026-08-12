# 📝 Projeto Blog Pessoal - API REST

![Banner Projeto Blog Pessoal](image_e87f5e.jpg)

## 💻 Sobre o Projeto
Esta é uma API RESTful desenvolvida para gerenciar um Blog Pessoal. O sistema permite o cadastro de usuários, criação de temas e publicação de postagens, garantindo a segurança das informações através de autenticação JWT e Spring Security.

## ⚙️ Principais Funcionalidades
- **Usuários:** Cadastro, consulta, atualização e autenticação de usuários.
- **Temas:** Criação e gerenciamento de temas (categorias) para estruturar o blog.
- **Postagens:** Criação, edição, listagem e exclusão de publicações.
- **Relacionamentos:** Associação entre usuários (quem cria), postagens (o conteúdo) e temas (a classificação).
- **Segurança:** Autenticação utilizando JWT (JSON Web Token) e Spring Security para proteger as requisições.

## 🛠️ Tecnologias Utilizadas
- **Linguagem:** Java
- **Framework:** Spring Boot
- **Segurança:** Spring Security e JWT
- **Persistência de Dados:** JPA + Hibernate
- **Banco de Dados:** MySQL
- **Testes:** JUnit
- **Documentação da API:** SpringDoc / Swagger
- **Servidor Web:** Tomcat

## 📊 Diagrama de Classes
A estrutura de dados e relacionamentos do projeto foi desenhada da seguinte forma:

![Diagrama de Classes](image_e87f3c.png)

## 📚 Documentação da API
A documentação completa dos endpoints da API foi gerada de forma automatizada com o Swagger. Através dela, é possível explorar e testar todas as rotas de Usuários, Temas e Postagens.

![Swagger UI](image_e87f42.png)

**Link do Deploy:** [https://blogpessoal-7t26.onrender.com](https://blogpessoal-7t26.onrender.com)

## 🚀 Como Executar o Projeto

1. **Clone o repositório:**
   ```bash
   git clone https://github.com/kauedota/BlogPessoal
   ```

2. **Configure o Banco de Dados:**
   Abra o arquivo `src/main/resources/application.properties` e atualize as credenciais de acesso ao seu banco de dados MySQL local.

3. **Inicie a aplicação:**
   Abra o projeto na sua IDE favorita (IntelliJ, Eclipse, VS Code) e rode a classe principal, ou utilize o Maven pelo terminal:
   ```bash
   mvn spring-boot:run
   ```

4. **Acesse a Documentação:**
   Com a aplicação em execução, acesse o link abaixo no seu navegador para visualizar e testar os endpoints através da interface do Swagger:
   ```text
   http://localhost:8080/swagger-ui/index.html
   ```
