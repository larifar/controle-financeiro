FROM maven:3.9.6-eclipse-temurin-17 as build
WORKDIR /app
COPY . .
RUN mvn clean package -DskipTests -pl bot-telegram -am

FROM eclipse-temurin:17-jre
WORKDIR /app
COPY --from=build /app/bot-telegram/target/*-jar-with-dependencies.jar app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]
