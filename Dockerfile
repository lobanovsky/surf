FROM eclipse-temurin:17-alpine

RUN apk add --no-cache \
    chromium \
    chromium-chromedriver \
    curl \
    bash

ENV CHROME_BIN=/usr/bin/chromium-browser \
    CHROME_DRIVER=/usr/bin/chromedriver

WORKDIR /opt/app

COPY /build/libs/*.jar app.jar

RUN chown -R 1000:1000 /opt/app
USER 1000

ENTRYPOINT java -jar /opt/app/app.jar
