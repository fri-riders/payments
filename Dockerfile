FROM openjdk:8-jre-alpine

RUN mkdir /app

WORKDIR /app

ADD ./target/payments-1.0-SNAPSHOT.jar /app

EXPOSE 8087

CMD ["java", "-jar", "payments-1.0-SNAPSHOT.jar"]
