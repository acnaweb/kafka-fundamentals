## Poc-kafka

POC de Kafka com microserviços para um e-commerce
* Java 1.8
* Maven
* Microserviços
* Kafka - https://www.apache.org/dyn/closer.cgi?path=/kafka/2.6.0/kafka_2.13-2.6.0.tgz

# Comandos Kafka 
* bin/zookeeper-server-start.sh config/zookeeper.properties
* bin/kafka-server-start.sh config/server.properties
* bin/kafka-consumer-groups.sh --bootstrap-server 127.0.0.1:9092 --all-groups --describe
* bin/kafka-console-consumer.sh --bootstrap-server localhost:9092 --topic ECOMMERCE_NEW_ORDER
* bin/kafka-topics.sh --bootstrap-server 127.0.0.1:9092 --describe
* bin/kafka-topics.sh --alter --zookeeper 127.0.01:2181 --topic ECOMMERCE_NEW_ORDER --partitions 3

 
# Replicas

* config/serverN
* broker.id=N
* default.replication.factor=2
* num.partitions=1
* listeners=PLAINTEXT://:909?
* log.dirs=/data/kafkaN

# Zookeeper

dataDir=/data/zookeeper




