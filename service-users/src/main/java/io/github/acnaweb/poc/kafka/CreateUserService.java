package io.github.acnaweb.poc.kafka;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.common.serialization.StringDeserializer;

public class CreateUserService {
	private static final Map<String, String> properties;
	static {
		properties = new HashMap<String, String>();
		properties.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
	}

	public void consume(ConsumerRecord<String, String> record) throws InterruptedException, ExecutionException {
		System.out.println(record.value());
	}

	public static void main(String[] args) {
		CreateUserService createUserService = new CreateUserService();

		KafkaService<String> kafkaService = new KafkaService<String>(CreateUserService.class.getSimpleName(),
				"ECOMMERCE_NEW_ORDER", createUserService::consume, String.class, properties);
		kafkaService.run();
	}
}
