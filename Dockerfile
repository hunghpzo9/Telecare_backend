FROM openjdk:11
# tzdata for timzone
RUN apt-get update -y
RUN apt-get install -y tzdata

# timezone env with default
ENV TZ=Asia/Ho_Chi_Minh
EXPOSE 8080
ARG JAR_FILE=target/telecare-0.0.1-SNAPSHOT.jar
ADD ${JAR_FILE} app.jar
ENTRYPOINT ["java","-jar","/app.jar"]