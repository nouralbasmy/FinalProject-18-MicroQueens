# Use OpenJDK 23 base image
FROM openjdk:25-ea-4-jdk-oraclelinux9

# Set the working directory
WORKDIR /app

# Copy the JAR file
COPY target/notification-0.0.1-SNAPSHOT.jar /app/notification.jar

ENV CUSTOMER_SERVICE_URL=http://customer-service:8094/customer

# Expose the port (adjust if needed)
EXPOSE 8092

# Run the app
CMD ["java", "-jar", "notification.jar"]
