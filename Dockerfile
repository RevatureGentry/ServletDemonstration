FROM tomcat:jdk8-adoptopenjdk-hotspot
COPY target/MehrabServletDemo.war webapps/
CMD ["catalina.sh", "run"]