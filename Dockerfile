FROM eclipse-temurin:17-jdk-alpine
WORKDIR /app
COPY target/Application.jar Application.jar
EXPOSE 8080
CMD ["java","-jar","Application.jar"]