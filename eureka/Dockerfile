FROM openjdk:8-jre-alpine
RUN addgroup -S app && adduser -SDG app app
USER app
WORKDIR /home/app
RUN mkdir -p /home/app/log
VOLUME /tmp
ARG JAR_FILE
COPY target/*.jar app.jar
ENV JAVA_OPTS=""
ENTRYPOINT exec java $JAVA_OPTS -Djava.security.egd=file:/dev/./urandom -jar app.jar
