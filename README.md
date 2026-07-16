2. ```bash
   # 📖 Blog Pessoal - Banco de Dados
   
   Projeto feito com **Spring Boot** e **MySQL** para gerenciar postagens de um blog.
   
   ---
   
   ## ⚙️ Configuração
   
   Arquivo `application.properties`:
   
   ```properties
   spring.datasource.url=jdbc:mysql://localhost/db_blogpessoal?createDatabaseIfNotExist=true&serverTimezone=America/Sao_Paulo&useSSL=false
   spring.datasource.username=root
   spring.datasource.password=root
   spring.jpa.hibernate.ddl-auto=updatexxxxxxxxxx  http://localhost:8080/hellobash



## 🗂️ Estrutura da Tabela

Tabela criada: `tb_postagens`

CREATE TABLE tb_postagens (
  id BIGINT AUTO_INCREMENT PRIMARY KEY,
  titulo VARCHAR(100) NOT NULL,
  texto VARCHAR(255),
  data DATETIME
);



## 🔎 Endpoints

- **GET /postagens** → retorna todas as postagens *(equivalente ao* `SELECT * FROM tb_postagens;`*)*



## 🚀 Como rodar

1. Instale o **MySQL** e crie o schema `db_blogpessoal`.
2. Rode o projeto com `mvn spring-boot:run` ou direto pelo IDE.
3. Acesse os endpoints via navegador ou Postman.

---

