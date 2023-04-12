FROM openjdk:17-oracle

#ARG DEF_AMOUNT
ENV DEFAULT_AMOUNT 5

ARG JAR_DIR=target/quarkus-app
COPY ${JAR_DIR} /app/quarkus-app

USER root

EXPOSE 8181

ENTRYPOINT ["java","-jar","/app/quarkus-app/quarkus-run.jar"]
