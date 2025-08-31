FROM eclipse-temurin:21-jre
WORKDIR /app
# Правильный путь относительно корня проекта
COPY ewm-statistic-service/ewm-stats-service/target/ewm-stats-service-*.jar app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]