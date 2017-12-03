FROM tomcat:8.0

COPY target/tha-javaagent.jar /usr/local/tha/
ADD scripts/setenv.sh /usr/local/tomcat/bin/setenv.sh

EXPOSE 8080
