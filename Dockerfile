# Stage 1: Build the application
FROM docker.arvancloud.ir/maven:3.8.6-openjdk-17 AS builder

# Set the working directory in the container
WORKDIR /app

# Copy the Maven wrapper files and pom.xml
COPY pom.xml mvnw* ./

# Copy the source code
COPY src ./src

# Run Maven to clean and package the application
RUN chmod +x ./mvnw && ./mvnw clean package -DskipTests

# Stage 2: Create the final image
FROM docker.arvancloud.ir/openjdk:17-jdk-slim

# Set the working directory in the container
WORKDIR /app

# Copy the packaged JAR file from the builder stage
COPY --from=builder /app/target/CountriesData-0.0.1-SNAPSHOT.jar app.jar

# Expose the port your application runs on
EXPOSE 8080

# Run the jar file
ENTRYPOINT ["java", "-jar", "app.jar"]
