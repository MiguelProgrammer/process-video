FROM openjdk:21-jdk

LABEL authors="Miguel Silva"

# Create the /app directory in the container
RUN mkdir /app

# Set /app as the working directory
WORKDIR /app

# Copy the JAR file from your local machine to the container
COPY target/*.jar /app/process-video.jar

# Expose the port the app will run on
EXPOSE 80

# Set the entrypoint for the container to run the JAR file
ENTRYPOINT ["java", "-jar", "/app/process-video.jar"]
