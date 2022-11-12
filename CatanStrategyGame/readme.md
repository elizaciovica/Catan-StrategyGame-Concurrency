# Introduction

TODO

# Docker

Start docker services with `docker-compose up -d`

## Kafka

1. Create a kafka topic

```bash
docker exec -it broker /bin/bash

kafka-topics --bootstrap-server localhost:9092 --topic example-topic --create --partitions 1 --replication-factor 1
kafka-topics --bootstrap-server localhost:9092 --list
```

2. Produce/Consume 

```bash
kafka-console-producer --bootstrap-server localhost:9092 --topic example-topic
kafka-console-consumer --bootstrap-server localhost:9092 --topic example-topic
```