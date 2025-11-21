# Build stage
FROM gradle:8-jdk17 AS build
COPY --chown=gradle:gradle . /home/gradle/src
WORKDIR /home/gradle/src
RUN gradle buildFatJar --no-daemon

# Runtime stage
FROM amazoncorretto:17-alpine-jdk
WORKDIR /app
COPY --from=build /home/gradle/src/build/libs/*.jar /app/ktor-app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/app/ktor-app.jar"]
