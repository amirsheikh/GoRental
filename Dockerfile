# Use an official OpenJDK 11 runtime as the base image
FROM openjdk:11-jdk-slim

# Set the working directory in the container
WORKDIR /app

# Copy the application JAR file into the container
COPY build/libs/GoRental-0.0.1-SNAPSHOT.jar app.jar

# Expose the port your application runs on
EXPOSE 8083

# Run the JAR file
ENTRYPOINT ["java", "-jar", "app.jar"]
