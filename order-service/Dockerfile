FROM maven:3.9.9-eclipse-temurin-17 AS build
WORKDIR /workspace

COPY . /workspace
RUN mvn -q -pl order-service -am clean package -DskipTests

FROM eclipse-temurin:17-jre
WORKDIR /app
COPY --from=build /workspace/order-service/target/order-service-0.0.1-SNAPSHOT.jar app.jar
EXPOSE 8082
ENTRYPOINT ["java","-jar","/app/app.jar"]
