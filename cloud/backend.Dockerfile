FROM hseeberger/scala-sbt:11.0.19_1.9.7_2.13.12 as builder
WORKDIR /app
COPY ../play-scala ./
RUN sbt dist

FROM openjdk:17-jdk-slim
WORKDIR /app
COPY --from=builder /app/target/universal/play-scala-1.0-SNAPSHOT.zip ./
RUN apt update && apt install -y unzip && unzip play-scala-1.0-SNAPSHOT.zip && rm play-scala-1.0-SNAPSHOT.zip

WORKDIR /app/play-scala-1.0-SNAPSHOT
RUN chmod +x ./bin/play-scala

EXPOSE 9000
CMD ["./bin/play-scala"]
