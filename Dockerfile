# Use uma imagem base do JDK 17
FROM openjdk:17-jdk-slim

# Define o diretório de trabalho no contêiner
WORKDIR /app

# Copia os arquivos necessários para build do Maven
COPY mvnw ./
COPY .mvn .mvn
COPY pom.xml ./pom.xml

# Dá permissão de execução ao Maven Wrapper
RUN chmod +x mvnw

# Instala as dependências do Maven
RUN ./mvnw dependency:resolve

# Copia o código-fonte para o contêiner
COPY src ./src

# Compila e empacota o projeto
RUN ./mvnw clean package -DskipTests

# Expõe a porta 8080
EXPOSE 8080

# Comando para iniciar a aplicação
CMD ["java", "-jar", "target/demencia-api-0.0.1-SNAPSHOT.jar"]
