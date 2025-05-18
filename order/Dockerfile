# Use OpenJDK 23 base image
FROM openjdk:23-jdk-slim

# Set the working directory
WORKDIR /app

# Copy the JAR file
COPY target/order-0.0.1-SNAPSHOT.jar /app/order.jar

ENV SERVER_PORT=8090 \
    RESTAURANT_URL=http://restaurant-service:8093 \
    PAYMENT_URL=http://payment-service:8091 \
    CUSTOMER_URL=http://customer-service:8094

# Expose the port (adjust if needed)
EXPOSE 8090

# Run the app
CMD ["java", "-jar", "order.jar"]
