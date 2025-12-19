FROM eclipse-temurin:17-jdk

WORKDIR /app

ADD target/ecom-app.jar /app/ecom-app.jar

ENTRYPOINT ["java","-jar","ecom-app.jar"]