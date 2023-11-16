FROM gradle:jdk17-alpine AS builder
WORKDIR /app
COPY . .
RUN gradle clean bootJar

FROM eclipse-temurin:17-jre-alpine AS runner
WORKDIR /app
COPY .env* .
COPY --from=builder /app/build/libs/*.jar app.jar
EXPOSE 8080

FROM runner AS runner_actions
CMD ["java", "-jar", "app.jar"]