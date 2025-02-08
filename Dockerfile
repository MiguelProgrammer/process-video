FROM openjdk:21-jdk
LABEL authors="Miguel Silva"
RUN mkdir /app
WORKDIR /app
COPY target/*.jar /process-videos
EXPOSE 80
ENTRYPOINT ["java", "-jar", "/process-videos.jar"]