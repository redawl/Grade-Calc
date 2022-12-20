FROM eclipse-temurin:11-alpine
RUN mkdir "/opt/app"
COPY ./target/GradeCalc-0.0.1-SNAPSHOT.jar /opt/app/app.jar
EXPOSE 8080
CMD ["java", "-jar", "/opt/app/app.jar" ]