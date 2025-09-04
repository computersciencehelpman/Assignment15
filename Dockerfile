# syntax=docker/dockerfile:1.7

########## Build stage ##########
FROM maven:3.9.9-eclipse-temurin-17 AS build
SHELL ["/bin/bash", "-lc"]   # needed for PIPESTATUS
WORKDIR /app

# Cache deps by layering pom.xml first
COPY pom.xml .
RUN mvn -B -DskipTests dependency:go-offline

# Copy sources
COPY src ./src

# Run build, ALWAYS print last 200 lines if it fails
RUN set -euo pipefail; \
    mvn -version; \
    ( mvn -B -Dmaven.test.skip=true -DskipTests clean package |& tee /tmp/mvn.log ); \
    code=${PIPESTATUS[0]}; \
    if [[ $code -ne 0 ]]; then \
      echo "---- Maven build failed. Last 200 lines ----"; \
      tail -n 200 /tmp/mvn.log; \
      exit $code; \
    fi

########## Runtime stage ##########
FROM eclipse-temurin:17-jre
WORKDIR /app
COPY --from=build /app/target/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","/app/app.jar"]
