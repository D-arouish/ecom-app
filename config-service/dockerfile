FROM openjdk:17-oracle
VOLUME /tmp
#copy file in target folder to a new file called app.jar
#because we have only one file .jar we use *
COPY target/*.jar app.jar
#execute java then -jar then the name of application
ENTRYPOINT ["java","-jar","/app.jar"]
