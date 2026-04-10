FROM eclipse-temurin:21-jdk-jammy

WORKDIR /app

RUN apt-get update && apt-get install -y maven && rm -rf /var/lib/apt/lists/*

COPY . .

RUN mvn clean package -DskipTests

EXPOSE 10000

CMD ["java", "-jar", "target/majorprojectecopulse-0.0.1-SNAPSHOT.jar"]