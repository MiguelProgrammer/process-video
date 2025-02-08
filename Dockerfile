FROM openjdk:21-jdk
LABEL authors="Miguel Silva"
RUN mkdir /app
WORKDIR /app
COPY target/*.jar /app/process-videos
EXPOSE 80
ENTRYPOINT ["java", "-jar", "/app/process-videos.jar"]