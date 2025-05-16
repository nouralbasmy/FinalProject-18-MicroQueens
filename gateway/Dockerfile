# Use OpenJDK 23 base image
FROM openjdk:23-jdk-slim

# Set the working directory
WORKDIR /app

# Copy the JAR file
COPY target/gateway-0.0.1-SNAPSHOT.jar /app/gateway.jar

# Expose the port
EXPOSE 8080

# Run the app
CMD ["java", "-jar", "gateway.jar"]