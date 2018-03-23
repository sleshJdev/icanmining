FROM openjdk:8-jdk-alpine
ARG app_file
COPY ${app_file} ./app.war
CMD ['java', '-jar', './app.war']
