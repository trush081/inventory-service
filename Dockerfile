# Use a specific version of Amazon Corretto
FROM gradle:jdk21

LABEL authors="trent"

# Set the working directory in the container
WORKDIR /app

# Copy the Gradle configuration files and project files into the container
COPY . .

# Set up Doppler
RUN curl -sLf --compressed "https://cli.doppler.com/install.sh" | sh

ARG DOPPLER_TOKEN
ENV DOPPLER_TOKEN=${DOPPLER_TOKEN}

# Set Up other env variables
ARG SPRING_PROFILES_ACTIVE
ENV SPRING_PROFILES_ACTIVE=${SPRING_PROFILES_ACTIVE}

# Download dependencies and build the application
RUN doppler run -- gradle build

# Expose the port the app runs on
EXPOSE 8080

# Clean up unnecessary artifacts
RUN rm -rf gradle

# Run the application
CMD ["doppler", "run", "--", "java", "-jar", "build/libs/Inventory-Service-0.0.1-SNAPSHOT.jar"]