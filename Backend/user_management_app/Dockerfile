FROM maven:4.0-jdk-8
WORKDIR /app
COPY target/*.jar /app/user_management_app.jar
EXPOSE 8181
ENTRYPOINT [ "java","-jar","-Dspring.profiles.active=docker","user_management_app.jar" ]
