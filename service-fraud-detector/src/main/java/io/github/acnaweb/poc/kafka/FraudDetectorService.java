package io.github.acnaweb.poc.kafka;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.concurrent.ExecutionException;

import org.apache.kafka.clients.consumer.ConsumerRecord;

public class FraudDetectorService {
	private final KafkaDispatcher<Order> dispatcher = new KafkaDispatcher<Order>();

	void consume(ConsumerRecord<String, Order> record) throws InterruptedException, ExecutionException {
		Order order = record.value();
		System.out.println(order);
		if (isFraud(order)) {
			dispatcher.send("ECOMMERCE_ORDER_REJECTED", order.getUserId(), order);
		} else {
			dispatcher.send("ECOMMERCE_ORDER_APPROVED", order.getUserId(), order);
		}
	}

	private boolean isFraud(Order order) {
		return order.getAmount().compareTo(new BigDecimal("3000")) >= 0;
	}

	public static void main(String[] args) {
		FraudDetectorService fraudeDetectorService = new FraudDetectorService();

		try (KafkaService<Order> kafkaService = new KafkaService<Order>(FraudDetectorService.class.getSimpleName(),
				"ECOMMERCE_NEW_ORDER", fraudeDetectorService::consume, Order.class, new HashMap<String, String>());) {
			kafkaService.run();
		}
	}

}
