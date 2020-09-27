package io.github.acnaweb.poc.kafka;

import java.util.HashMap;

import org.apache.kafka.clients.consumer.ConsumerRecord;

public class FraudDetectorService {

	void consume(ConsumerRecord<String, Order> record) {
		System.out.println(
				"fraude " + record.key() + "/" + record.value() + "/" + record.offset() + "/" + record.partition());
	}

	public static void main(String[] args) {
		FraudDetectorService fraudeDetectorService = new FraudDetectorService();

		try (KafkaService<Order> kafkaService = new KafkaService<Order>(FraudDetectorService.class.getSimpleName(),
				"ECOMMERCE_NEW_ORDER", fraudeDetectorService::consume, Order.class, new HashMap<String, String>());) {
			kafkaService.run();
		}
	}

}
