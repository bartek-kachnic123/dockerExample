FROM ubuntu:24.04

RUN apt-get update && \
    apt-get install -y \
    unzip wget

# Python 3.10 && Java 8
RUN apt-get install -y \
    software-properties-common && \
    add-apt-repository ppa:deadsnakes/ppa && \
    apt-get update && \
    apt-get install -y python3.10 openjdk-8-jdk


# Kotlin
RUN wget https://github.com/JetBrains/kotlin/releases/download/v2.1.20/kotlin-compiler-2.1.20.zip -O /tmp/kotlin.zip \
    && mkdir -p /opt \
    && unzip /tmp/kotlin.zip -d /opt \
    && rm  /tmp/kotlin.zip

ENV KOTLIN_ROOT=/opt/kotlinc
ENV PATH=$PATH:$KOTLIN_ROOT/bin

# Gradle 
RUN wget https://services.gradle.org/distributions/gradle-8.13-bin.zip && \
    unzip -d /opt/gradle gradle-8.13-bin.zip && rm gradle-8.13-bin.zip

ENV GRADLE_HOME=/opt/gradle/gradle-8.13
ENV PATH=$PATH:$GRADLE_HOME/bin


COPY ./app /app
WORKDIR /app
RUN gradle build --no-daemon
CMD ["gradle", "run", "--warning-mode=none"]