FROM maven:3.8.1-openjdk-8 AS build

WORKDIR /app

COPY pom.xml .
COPY src ./src

RUN mvn clean package -DskipTests

FROM openjdk:8-jre-alpine

WORKDIR /app

COPY --from=build /app/target/store-1.0.jar app.jar

ENTRYPOINT ["java", "-jar", "app.jar"]