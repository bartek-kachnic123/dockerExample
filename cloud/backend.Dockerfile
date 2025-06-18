FROM hseeberger/scala-sbt:17.0.2_1.6.2_3.1.1 AS builder
WORKDIR /app
COPY ../play-scala ./
RUN sbt clean dist

FROM openjdk:17-jdk-slim
WORKDIR /app
COPY --from=builder /app/target/universal/play-scala-1.0-SNAPSHOT.zip ./
RUN apt update && apt install -y unzip && unzip play-scala-1.0-SNAPSHOT.zip && rm play-scala-1.0-SNAPSHOT.zip

WORKDIR /app/play-scala-1.0-SNAPSHOT
RUN chmod +x ./bin/play-scala

EXPOSE 80
ENTRYPOINT ["/bin/sh", "-c", "bin/play-scala -Dhttp.port=80"]
