# Use Eclipse Temurin JDK 21 (LTS) as base image
FROM eclipse-temurin:21-jdk-jammy

# Set working directory
WORKDIR /app

# Copy only the necessary files for better layer caching
COPY pom.xml .
COPY src ./src
COPY mvnw .
COPY .mvn ./.mvn

# Build the application (cache dependencies first)
RUN ./mvnw dependency:go-offline -B
RUN ./mvnw clean package -DskipTests

# Run with production-optimized JVM settings
CMD ["java", "-jar", "target/*.jar"]  # Replace with your actual JAR name pattern