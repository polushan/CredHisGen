## install Java8
  - sudo add-apt-repository ppa:webupd8team/java
  - sudo apt-get update
  - sudo apt-get install oracle-java8-installer
## install maven

## build project
  - bash ./assembly.sh
## start project
  - bash ./start.sh "http://server-address:port"

## flags
  - src/main/resources/flags.txt
  
## alternative run(host and port write in Dockerfile).
### Need to change "http://service-address:port/" to your service address in a dockerfile.
  -  docker build -t credhisgen ./
  -  docker run credhisgen

