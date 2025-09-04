# syntax=docker/dockerfile:1.7

########## Build stage ##########
FROM maven:3.9.9-eclipse-temurin-17 AS build
WORKDIR /app

# Copy only pom.xml first to cache deps
COPY pom.xml .
RUN --mount=type=cache,id=m2cache,target=/root/.m2 \
    mvn -B -DskipTests dependency:go-offline

# Copy sources and build
COPY src ./src
RUN --mount=type=cache,id=m2cache,target=/root/.m2 \
    mvn -B -DskipTests clean package

########## Runtime stage ##########
FROM eclipse-temurin:17-jre
WORKDIR /app
COPY --from=build /app/target/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","/app/app.jar"]
