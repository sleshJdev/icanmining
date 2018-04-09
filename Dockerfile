FROM openjdk:8-jdk-alpine
WORKDIR /home
ARG app_file
ARG app_profile
ENV SPRING_PROFILES_ACTIVE=${app_profile}
RUN mkdir tomcat
RUN wget -O - http://www-us.apache.org/dist/tomcat/tomcat-8/v8.5.30/bin/apache-tomcat-8.5.30.tar.gz | \
    tar -zxvf - --strip-components=1 -C tomcat
RUN rm -rf tomcat/webapps/ROOT/*
EXPOSE 8080
COPY ${app_file} app.war
RUN unzip app.war -d ./tomcat/webapps/ROOT
RUN rm app.war
ENTRYPOINT ["tomcat/bin/catalina.sh", "run"]