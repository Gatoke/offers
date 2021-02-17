# Used only to create image locally

FROM openjdk:11-slim
COPY build/libs/*.jar offers.jar
ENTRYPOINT ["java","-jar","/offers.jar"]
CMD java - jar offers.jar
