package io.github.acnaweb.poc.kafka;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.common.serialization.StringDeserializer;

public class LogService {
	private static final Map<String, String> properties;
	static {
		properties = new HashMap<String, String>();
		properties.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
	}

	void consume(ConsumerRecord<String, String> record) {
		System.out.println("log " + record.topic() + "/" + record.key() + "/" + record.value() + record.offset() + "/"
				+ record.partition());

	}

	public static void main(String[] args) {
		LogService logService = new LogService();

		try (KafkaService<String> kafkaService = new KafkaService<String>(LogService.class.getSimpleName(),
				Pattern.compile("ECOMMERCE.*"), logService::consume, String.class, properties);) {
			kafkaService.run();
		}
	}

}
