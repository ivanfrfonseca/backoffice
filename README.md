# Backoffice

Uma aplicação web desenvolvida para facilitar a gestão do conhecimento dentro da equipe. Permite a criação e organização de uma base de conhecimento, incluindo o cadastro de scripts e post-its para auxiliar nas tarefas operacionais.

## Estrutura do Projeto

- **Dockerfile:** Configura a imagem Docker para a aplicação.
- **docker-compose.yaml:** Orquestra os serviços (app e banco de dados).
- **.env:** Arquivo com variáveis de ambiente sensíveis para a aplicação. Este arquivo **não** é versionado no repositório.
- **.env.example:** Modelo de arquivo `.env` para configurar o ambiente.
- **.database.env:** Arquivo com variáveis de ambiente específicas do banco de dados. Este arquivo **não** é versionado no repositório.
- **.database.env.example:** Modelo de arquivo `.database.env` para configurar o banco de dados.
- **last-db.sh:** Responsável por conectar ao banco de dados e criar a estrutura e os constraints.

## Configuração do Ambiente

1. Copie os arquivos de exemplo para seus respectivos arquivos de ambiente:

   ```bash
   cp settings/.env.example .env 
   cp settings/.database.env.example .database.env
   ```

2. Edite os arquivos `.env` e `.database.env` para preencher os valores apropriados para o seu ambiente.

   Exemplo de `.env`:

   ```env
   # ==========================================================
   # Configurações para a Aplicação (Spring Boot)
   # ==========================================================
   # URL de conexão com o banco de dados PostgreSQL. "db" refere-se ao nome do serviço do banco de dados no Docker Compose.
   SPRING_DATASOURCE_URL=jdbc:postgresql://localhost:5432/<nome-do-banco>       
   
   # Nome de usuário que a aplicação usará para se conectar ao banco de dados.
   SPRING_DATASOURCE_USERNAME=<usuario-do-banco>                         
   
   # Senha correspondente ao usuário definido acima para acessar o banco.
   SPRING_DATASOURCE_PASSWORD=<senha-do-banco>                           

   # Estratégia de gerenciamento do esquema de banco de dados pelo Hibernate.
   # Opções comuns: "none", "validate", "update", "create", "create-drop".
   # "update" ajusta o esquema conforme as entidades.
   SPRING_JPA_HIBERNATE_DDL_AUTO=update                                  
   
   # Define se as consultas SQL executadas pelo Hibernate devem ser exibidas no console.
   SPRING_JPA_SHOW_SQL=true                                              
   
   # Dialeto do Hibernate para PostgreSQL, garantindo compatibilidade com o banco.
   SPRING_JPA_DATABASE_PLATFORM=org.hibernate.dialect.PostgreSQLDialect  
   ```

   Exemplo de `.database.env`:

   ```env
   #-------------------------------------------------------------------------------------
   # Postgres Database Local Environment - Example
   #-------------------------------------------------------------------------------------

   # Nome do usuário principal do banco de dados. Geralmente, é o administrador ou uma conta configurada para acesso geral.
   POSTGRES_USER=postgres        

   # Senha associada ao `POSTGRES_USER`. Deve ser segura e, em ambientes de produção, seguir boas práticas de segurança.
   POSTGRES_PASSWORD=<SUA_SENHA> 
   
   # Fuso horário do PostgreSQL. É importante garantir que esteja alinhado com o fuso horário do sistema.
   PGTZ=America/Sao_Paulo               
   
   # Nome do banco de dados onde as operações serão realizadas. Deve refletir o nome do banco configurado no PostgreSQL.
   DATABASE_NAME=<NOME_DO_BANCO>        
   
   # Nome do schema usado no banco de dados. Ele organiza tabelas e outros objetos do banco de dados.
   DATABASE_SCH=<NOME_DO_SCHEMA>        
   
   # Usuário responsável pelas operações DDL (Data Definition Language), como criação e alteração de tabelas, índices, etc.
   DDL_USERNAME=<USUARIO_DDL>           
   
   # Senha do usuário DDL. Deve ser forte e seguir as políticas de segurança definidas para acesso administrativo.
   DDL_PASSWORD=<SENHA_DDL>             
   
   # Usuário responsável pelas operações DML (Data Manipulation Language), como `SELECT`, `INSERT`, `UPDATE` e `DELETE`.
   DML_USERNAME=<USUARIO_DML>           
   
   # Senha do usuário DML. Também deve ser forte, mas normalmente tem permissões limitadas para operações no banco.
   DML_PASSWORD=<SENHA_DML>
   ```
3. Ambiente de Execução

   A aplicação está rodando na seguinte máquina e diretório:

   Servidor: dsno31
   Conta: docker31
   Diretório: /l/disk0/docker31/backoffice
   URL: http://ocidsno31:8080/

## Nova release

Para atualizar a versão, basta verificar o e-mail com a release gerada ou acessar o Jenkins através da URL:
[https://jenkins-netuno.cpqd.com.br/netuno/job/ORBILL-TOOLS/job/code/job/backoffice/job/master/](https://jenkins-netuno.cpqd.com.br/netuno/job/ORBILL-TOOLS/job/code/job/backoffice/job/master/).

### Passos para atualização:

1. **Acessar o servidor**  
   ```sh
   ssh user@dsno31
   ```

2. **Trocar para a conta correta**  
   ```sh
   sudo -iu docker31
   ```

3. **Editar o arquivo `docker-compose.yml`**  
   Use um dos editores de texto disponíveis:  
   ```sh
   nano docker-compose.yml
   ```  
   ou  
   ```sh
   vi docker-compose.yml
   ```

4. **Atualizar a aplicação**  
   ```sh
   docker compose down ; docker compose up --build ; docker compose logs -f
   ```
  
## 

1. Construa e inicialize os containers Docker:

   ```bash
   docker compose up --build --force-recreate
   ```

2. Acesse a aplicação:

   - A aplicação estará disponível em: [http://localhost:8080](http://localhost:8080)
   - O banco de dados estará disponível na porta definida no arquivo `.database.env`.

3. Para parar os containers:

   ```bash
   docker-compose down
   ```

4. Para parar os containers e excluir os volumes:

   ```bash
   docker-compose down -v
   ```

## Configuração do Banco de Dados

- O arquivo `schema.sql` sera executado automaticamente na primeira inicialização do banco.
- O banco de dados persistirá os dados no volume `postgres-data`.

## Personalização do Postgre arquivo é usado pelo serviço `db` no `docker-compose.yaml`.

## Notas

- Certifique-se de que as portas definidas nos arquivos `.env` e `.database.env` não estejam em uso no seu sistema.
- Mantenha as variáveis sensíveis fora do código fonte em ambientes de produção.