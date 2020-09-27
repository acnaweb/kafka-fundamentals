package io.github.acnaweb.poc.kafka;

import java.util.HashMap;

import org.apache.kafka.clients.consumer.ConsumerRecord;

public class EmailService {

	public static void main(String[] args) {
		EmailService emailService = new EmailService();
		try (KafkaService<Email> service = new KafkaService<Email>(EmailService.class.getSimpleName(),
				"ECOMMERCE_SEND_EMAIL", emailService::consume, Email.class, new HashMap<String, String>());) {
			service.run();
		}
	}

	public void consume(ConsumerRecord<String, Email> record) {
		System.out.println(
				"email " + record.key() + "/" + record.value() + "/" + record.offset() + "/" + record.partition());

	}

}
