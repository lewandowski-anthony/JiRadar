ARG BASE_IMAGE=eclipse-temurin:25-alpine

FROM $BASE_IMAGE AS builder
ENV APP_NAME=jiradar-back
ARG JAR_FILE=target/jiradar-back-*.jar
COPY ${JAR_FILE} $APP_NAME.jar

RUN java -Djarmode=tools -jar $APP_NAME.jar extract

FROM $BASE_IMAGE

LABEL org.opencontainers.image.title='Jiradar Backend - Local'
LABEL org.opencontainers.image.description='Local development image for Jiradar Backend'
LABEL org.opencontainers.image.authors='lewandowski-anthony'
LABEL org.opencontainers.image.source='https://github.com/lewandowski-anthony/jiradar/back-end'

RUN apk update &&  \
    apk upgrade &&  \
    addgroup -S spring &&  \
    adduser -S spring -G spring
WORKDIR /usr/app

RUN chown spring:spring /usr/app
USER spring:spring
EXPOSE 8080

COPY --chown=spring:spring --from=builder /jiradar-back/lib/ ./lib/
COPY --chown=spring:spring --from=builder /jiradar-back/jiradar-back.jar ./

ENV JAVA_OPTS=""

ENTRYPOINT java $JAVA_OPTS -jar jiradar-back.jar