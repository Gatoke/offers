FROM adoptopenjdk/openjdk11:latest
VOLUME /tmp
COPY build/libs/*.jar offers.jar
ENTRYPOINT ["java","-jar","/offers.jar"]
CMD java - jar offers.jar
