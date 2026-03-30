FROM alpine/java:22-jdk
COPY target/oauth-server-0.0.1-SNAPSHOT.jar oauth-server.jar
ENTRYPOINT ["java","-jar","/oauth-server.jar"]