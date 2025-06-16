FROM gradle:jdk21 AS build
WORKDIR /app
COPY . .
RUN gradle bootJar --no-daemon

FROM amazoncorretto:21-al2023
WORKDIR /app
COPY --from=build /app/build/libs/*.jar app.jar

ENTRYPOINT ["java", "-jar", "app.jar"]