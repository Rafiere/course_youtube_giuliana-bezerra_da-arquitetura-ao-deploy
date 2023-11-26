FROM eclipse-temurin:17.0.8.1_1-jdk-jammy

# Copiando todos os arquivos da raiz do projeto para o container.
COPY . .

RUN ./mvnw clean install -DskipTests

ENTRYPOINT ["java", "-jar", "target/daarquiteturaaodeploy-0.0.1-SNAPSHOT.jar"]