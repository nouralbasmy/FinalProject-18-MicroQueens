# Use OpenJDK 23 base image
FROM openjdk:25-ea-4-jdk-oraclelinux9

# Set the working directory
WORKDIR /app

# Copy the JAR file
COPY target/payment-0.0.1-SNAPSHOT.jar /app/payment.jar

# Expose the port (adjust if needed)
EXPOSE 8091

ENV CUSTOMER_SERVICE_URL=http://customer-service:8094/order \
    ORDER_SERVICE_URL=http://order-service:8090/order
# Run the app
CMD ["java", "-jar", "payment.jar"]
