package io.github.acnaweb.poc.kafka;

import java.util.HashMap;

import org.apache.kafka.clients.consumer.ConsumerRecord;

public class EmailService {

	public static void main(String[] args) {
		EmailService emailService = new EmailService();
		try (KafkaService<String> service = new KafkaService<String>(EmailService.class.getSimpleName(),
				"ECOMMERCE_SEND_EMAIL", emailService::consume, String.class, new HashMap<String, String>());) {
			service.run();
		}
	}

	public void consume(ConsumerRecord<String, String> record) {
		System.out.println(
				"email " + record.key() + "/" + record.value() + "/" + record.offset() + "/" + record.partition());

	}

}
