# Use OpenJDK 23 base image
FROM openjdk:23-jdk-slim

# Set the working directory in the container
WORKDIR /app

# Copy the already built JAR file into the container
COPY target/restaurant-0.0.1-SNAPSHOT.jar /app/restaurant.jar


# Expose the application port
EXPOSE 8093

# Set the entry point to run the application
CMD ["java", "-jar", "restaurant.jar"]
