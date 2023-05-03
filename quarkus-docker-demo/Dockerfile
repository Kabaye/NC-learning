FROM maven:3.8.5-openjdk-17-slim AS build
COPY src /app/src
COPY pom.xml /app
RUN mvn -f /app/pom.xml clean package

ENV DEFAULT_AMOUNT 5
EXPOSE 8181

# for multi-stage build
#COPY --from=build /app/target/quarkus-app /app/quakus-jar
#ENTRYPOINT ["java","-jar","/app/quakus-jar/quarkus-run.jar"]

ENTRYPOINT ["java","-jar","/app/target/quarkus-app/quarkus-run.jar"]
