FROM openjdk:11
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} app2.jar
ENTRYPOINT ["java","-jar","/app2.jar"]
EXPOSE 8081:8080