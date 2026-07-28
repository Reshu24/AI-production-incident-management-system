# Step 1 — start with Java 17 on lightweight Linux
FROM eclipse-temurin:17-jdk-alpine

# Step 2 — set working directory inside container
WORKDIR /app

# Step 3 — copy the built jar into the container
COPY target/incident-0.0.1-SNAPSHOT.jar app.jar

# Step 4 — expose port 8080 so outside world can reach it
EXPOSE 8080

# Step 5 — run the app when container starts
ENTRYPOINT ["java", "-jar", "app.jar"]