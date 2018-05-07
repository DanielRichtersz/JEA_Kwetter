FROM gradle:4.3-jdk-alpine
ADD --chown=gradle . /test-project
WORKDIR /test-project
CMD gradle build && java -jar build/libs/ArtifactId-1.0-SNAPSHOT