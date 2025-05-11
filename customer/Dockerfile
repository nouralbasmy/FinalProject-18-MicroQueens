# Use OpenJDK 23 base image
FROM openjdk:23-jdk-slim

# Set the working directory
WORKDIR /app

# Copy the JAR file
COPY target/customer-0.0.1-SNAPSHOT.jar /app/customer.jar

ENV SERVER_PORT=${SERVER_PORT} \
    RESTAURANT_URL=http://restaurant-service:8093 \
    CART_SERVICE_URL=http://cart-service:8090/cart \
    NOTIFICATION_SERVICE_URL=http://notification-service:8092/notifications \
    ORDER_SERVICE_URL=http://order-service:8090/order

# Expose the port (adjust if needed)
EXPOSE 8094

# Run the app
CMD ["java", "-jar", "customer.jar"]
