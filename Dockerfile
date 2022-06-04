FROM openjdk:11-jdk-slim
EXPOSE 8080
CMD gradle clean build
ADD build/libs/dgexchange-1.0-SNAPSHOT.jar dgexchange-1.0-SNAPSHOT.jar
ENTRYPOINT ["java","-jar","dgexchange-1.0-SNAPSHOT.jar"]