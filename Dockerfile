# syntax=docker/dockerfile:1.7

########## Build stage ##########
FROM maven:3.9.9-eclipse-temurin-17 AS build
SHELL ["/bin/bash", "-lc"]         # lets us use pipefail and tee
WORKDIR /app

# Leverage dependency layer cache
COPY pom.xml .
RUN mvn -B -DskipTests dependency:go-offline

# Copy sources and build
COPY src ./src
# Write a full log, and if mvn fails, print the last 200 lines
RUN set -euo pipefail; \
    mvn -version; \
    mvn -B -Dmaven.test.skip=true -DskipTests clean package 2>&1 | tee /tmp/mvn.log || { \
      code=$?; echo "---- Maven build failed. Last 200 lines ----"; tail -n 200 /tmp/mvn.log; exit $code; }

########## Runtime stage ##########
FROM eclipse-temurin:17-jre
WORKDIR /app
COPY --from=build /app/target/*.jar app.jar

EXPOSE 8080
ENTRYPOINT ["java","-jar","/app/app.jar"]
