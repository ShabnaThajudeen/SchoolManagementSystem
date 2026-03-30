FROM alpine/java:22-jdk
COPY target/apigateway-0.0.1-SNAPSHOT.jar apigateway.jar
ENTRYPOINT ["java","-jar","/apigateway.jar"]