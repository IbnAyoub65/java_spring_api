FROM eclipse-temurin:17-jdk-alpine
VOLUME /tmp
ARG JAR_FILE=*.jar
COPY ${JAR_FILE} devoir-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java","-jar","devoir-0.0.1-SNAPSHOT.jar"]