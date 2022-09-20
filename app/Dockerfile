FROM openjdk:8-jre-slim
RUN addgroup --system spring && adduser --system --group spring
USER spring:spring
ARG JAR_FILE
COPY ${JAR_FILE} /app.jar
EXPOSE 8080
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/app.jar"]

#EXPOSE 5005
#ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=*:5005","-jar","/app.jar"]

