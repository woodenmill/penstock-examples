# Penstock Examples

## Running 

* Start Kafka, KSQL server and monitoring stack by running: 
```bash
cd ./docker
docker-compose up -d --build
```

* Run `PerformanceSpec`:
```bash
sbt test
```