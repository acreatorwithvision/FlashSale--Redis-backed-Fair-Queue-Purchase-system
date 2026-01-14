## Using Java 25 and Spring 4
java -version
mvn -version
-both should yield 25

## Start docker containers(kafka,redis,postgres,zookeeper)
from root
docker-compose up -d
docker ps

## Run Springboot application
cd flashsale-api
./mvnw spring-boot:run

## Verify healthcheck
curl http://localhost:8080/health