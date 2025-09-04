# syntax=docker/dockerfile:1.7

########## Build stage ##########
FROM maven:3.9.9-eclipse-temurin-17 AS build
WORKDIR /app

# Copy pom.xml and warm the Maven cache
COPY pom.xml .
RUN --mount=type=cache,id=s/840b27b1-f3c5-425e-8cbf-b91502235b2c-/root/.m2/repository,target=/root/.m2/repository \
    mvn -B -DskipTests dependency:go-offline

# Copy sources and build the jar
COPY src ./src
RUN --mount=type=cache,id=s/840b27b1-f3c5-425e-8cbf-b91502235b2c-/root/.m2/repository,target=/root/.m2/repository \
    mvn -B -DskipTests clean package

########## Runtime stage ##########
FROM eclipse-temurin:17-jre
WORKDIR /app

# Copy the built artifact (works with any jar name)
COPY --from=build /app/target/*.jar app.jar

EXPOSE 8080
ENTRYPOINT ["java","-jar","/app/app.jar"]
