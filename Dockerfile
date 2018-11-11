FROM openjdk:8-jdk-alpine
WORKDIR /home
ARG app_file
ARG app_profile
ENV SPRING_PROFILES_ACTIVE=${app_profile}
EXPOSE 8080
RUN mkdir tomcat
COPY ${app_file} app.war
RUN (wget -O - https://archive.apache.org/dist/tomcat/tomcat-8/v8.5.35/bin/apache-tomcat-8.5.35-deployer.tar.gz | \
    tar -zxvf - --strip-components=1 -C tomcat) && \
    (([ -d tomcat/webapps/ROOT ] || mkdir -p tomcat/webapps/ROOT) && \
        unzip app.war -d tomcat/webapps/ROOT) && \
    rm app.war
ENTRYPOINT ["tomcat/bin/catalina.sh", "run"]