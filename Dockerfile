# syntax=docker/dockerfile:1.7

########## Build stage ##########
FROM eclipse-temurin:17-jdk AS build
SHELL ["/bin/bash", "-lc"]
WORKDIR /app

# Install Maven from apt (avoids pulling maven:* from Docker Hub)
RUN apt-get update \
 && apt-get install -y --no-install-recommends maven \
 && rm -rf /var/lib/apt/lists/*

# Cache deps
COPY pom.xml .
RUN mvn -B -DskipTests dependency:go-offline

# Copy sources and build
COPY src ./src
# Print the last 200 lines if the build fails so Railway logs show the cause
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
