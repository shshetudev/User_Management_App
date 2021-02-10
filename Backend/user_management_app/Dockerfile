FROM maven:3.6-jdk-8
FROM java:8
WORKDIR /app
COPY target/user_management_app.jar /app/user_management_app.jar
EXPOSE 8181
ENTRYPOINT [ "java","-jar","-Dspring.profiles.active=docker","user_management_app.jar" ]
