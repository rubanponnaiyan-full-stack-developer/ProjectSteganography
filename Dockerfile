FROM eclipse-temurin:21-jdk-jammy

WORKDIR /app

COPY backend/Steganography_Project/target/*.jar app.jar

EXPOSE 8080

CMD ["java", "-jar", "app.jar"]
