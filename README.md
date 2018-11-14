# Penstock Examples

## Running 

1. Start Kafka, KSQL server and monitoring stack by running: 
```bash
docker-compose -f docker/docker-compose.yml up -d --build
```

1. Run `PerformanceSpec`:
```bash
sbt test
```

1. Stop containers
```bash
docker-compose -f docker/docker-compose.yml down
```