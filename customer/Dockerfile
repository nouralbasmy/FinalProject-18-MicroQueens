# Use OpenJDK 23 base image
FROM openjdk:23-jdk-slim

# Set the working directory
WORKDIR /app

# Copy the JAR file
COPY target/customer-0.0.1-SNAPSHOT.jar /app/customer.jar

# Expose the port (adjust if needed)
EXPOSE 8082

# Run the app
CMD ["java", "-jar", "customer.jar"]
