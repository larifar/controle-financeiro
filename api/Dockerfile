# build
FROM maven:3.9.6-eclipse-temurin-17 as build
WORKDIR /app
COPY . .
RUN mvn clean package -DskipTests -pl api -am

# gerar imagem
FROM eclipse-temurin:17-jre
WORKDIR /app
COPY --from=build /app/api/target/*.jar app.jar
EXPOSE 8081
ENTRYPOINT ["java", "-jar", "app.jar"]
