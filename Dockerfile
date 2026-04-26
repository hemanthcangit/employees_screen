# Use lightweight Java image
FROM eclipse-temurin:21-jdk-jammy

# App jar location
ARG JAR_FILE=target/*.jar

# Copy jar to container
COPY ${JAR_FILE} app.jar

# Expose port
EXPOSE 8080

# Run application
ENTRYPOINT ["java","-jar","/app.jar"]