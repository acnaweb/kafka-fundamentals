package io.github.acnaweb.poc.kafka;

import java.util.HashMap;
import java.util.concurrent.ExecutionException;

import org.apache.kafka.clients.consumer.ConsumerRecord;

public class CreateUserService {

	public void consume(ConsumerRecord<String, Order> record) throws InterruptedException, ExecutionException {
		System.out.println(record.value());
	}

	public static void main(String[] args) {
		CreateUserService createUserService = new CreateUserService();

		KafkaService<Order> kafkaService = new KafkaService<Order>(CreateUserService.class.getSimpleName(),
				"ECOMMERCE_NEW_ORDER", createUserService::consume, Order.class, new HashMap<String, String>());
		kafkaService.run();
	}
}
