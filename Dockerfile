FROM maven:3.5.2-jdk-8
COPY . /usr/src/gen/
WORKDIR /usr/src/gen

RUN mvn clean compile assembly:single

#last paream host:port CreBur service
ENTRYPOINT ["java", "-jar", "CredHisGen.jar", "http://service-address:port/"]

