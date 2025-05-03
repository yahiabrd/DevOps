# Use a base image for Gradle
FROM gradle:8.2.1-jdk17 AS build

# Set the working directory
WORKDIR /app

# Copy Gradle configuration files
COPY ./server/gradle gradle
COPY ./server/gradlew .
COPY ./server/settings.gradle .
COPY ./server/build.gradle .

# Copy the source code
COPY ./server/src src

# Build the application
RUN chmod +x ./gradlew
RUN ./gradlew clean build -x test --info

# Use a slim OpenJDK image for running the app
FROM openjdk:17-jdk-slim

# Set the working directory
WORKDIR /app

# Copy the built jar file from the build stage
COPY --from=build /app/build/libs/poke-app-server-0.0.1.jar server.jar

# Set environment variables for MySQL credentials
ENV DB_SERVER=
ENV DB_USERNAME=
ENV DB_PASSWORD=

# Expose the port your Spring Boot app runs on (usually 8080)
EXPOSE 4445

# Run the jar file
CMD ["java", "-jar", "server.jar"]