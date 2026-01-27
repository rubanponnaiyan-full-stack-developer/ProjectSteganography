FROM eclipse-temurin:25.0.2-jdk

WORKDIR /app

COPY backend/Steganography_Project/target/*.jar app.jar

EXPOSE 8080

CMD ["java", "-jar", "app.jar"]