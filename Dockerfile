FROM openjdk:11-jre-slim
RUN mkdir /app
COPY facultyElections/ /app/
EXPOSE 4567
ENTRYPOINT /app/bin/facultyElections