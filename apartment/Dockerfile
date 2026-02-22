FROM eclipse-temurin:17-jdk-jammy

WORKDIR /app

COPY build/libs/apartment-1.jar app.jar

ENV JAVA_OPTS=""

ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -jar app.jar --spring.profiles.active=${SPRING_PROFILES_ACTIVE}"]