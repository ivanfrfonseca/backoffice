# Etapa 1: Build do projeto com Maven e JDK 17
FROM container-registry.cpqd.com.br/dockerhub/maven:3.8.1-openjdk-17 AS builder

# Diretório de trabalho no container
WORKDIR /app

# Montando cache para dependências do Maven
VOLUME /root/.m2

# Copiando o arquivo pom.xml para baixar dependências primeiro (otimização de cache)
COPY pom.xml .

# Adicionando log detalhado para verificar erros durante o download de dependências
RUN mvn dependency:go-offline

# Copiando o restante do código-fonte para o container
COPY . .

# Compilando o projeto com logs detalhados
RUN mvn clean package -DskipTests

# Etapa 2: Configuração do Tomcat com JDK 17
FROM container-registry.cpqd.com.br/dockerhub/eclipse-temurin:17-jdk AS runtime

# Diretório de trabalho no Tomcat
WORKDIR /app

# Copiando o WAR gerado na etapa de build para o Tomcat
COPY --from=builder /app/target/cpqd-*.jar ./cpqd-backoffice.jar

# Dando permissão 777 ao arquivo copiado
RUN chmod 777 ./cpqd-backoffice.jar

# Expondo a porta padrão do Tomcat
EXPOSE 8080

# Iniciando o Tomcat
CMD ["java", "-jar", "cpqd-backoffice.jar"]


