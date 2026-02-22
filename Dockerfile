FROM eclipse-temurin:21-jre-jammy
COPY target/*.jar app.jar
EXPOSE 8443
ENTRYPOINT ["java", "-jar", "app.jar"]