# Use Maven to build the application (build stage)
FROM openjdk:21-jdk-slim
WORKDIR /app

COPY .mvn/ .mvn
COPY mvnw pom.xml ./

COPY src ./src

EXPOSE 8081

CMD ["./mvnw", "spring-boot:run"]