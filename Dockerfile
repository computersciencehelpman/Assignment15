# syntax=docker/dockerfile:1.7

########## Build stage ##########
FROM maven:3.9.9-eclipse-temurin-17 AS build
WORKDIR /app

# Copy pom first to leverage layer caching for dependencies
COPY pom.xml .
RUN mvn -B -DskipTests dependency:go-offline

# Copy sources
COPY src ./src

# Verbose build so Railway logs show the real cause if anything fails
RUN set -eux; \
    echo "== PWD =="; pwd; \
    echo "== LS =="; ls -la; \
    mvn -version; \
    mvn -B -e -X -Dmaven.test.skip=true -DskipTests clean package

########## Runtime stage ##########
FROM eclipse-temurin:17-jre
WORKDIR /app

# Copy the built JAR (handles any jar name)
COPY --from=build /app/target/*.jar app.jar

EXPOSE 8080
ENTRYPOINT ["java","-jar","/app/app.jar"]
