FROM openjdk:17.0.1-jdk-slim
WORKDIR /opt
ENV PORT 8080
EXPOSE 8080
COPY maven/books-service*.jar /opt/app.jar
ENTRYPOINT exec java $JAVA_OPTS -jar app.jar