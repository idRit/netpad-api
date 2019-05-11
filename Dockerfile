FROM amazoncorretto:8
EXPOSE 8080
ADD target/netpad-api-0.0.1-SNAPSHOT.jar netpad-api-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java","-jar","netpad-api-0.0.1-SNAPSHOT.jar"]