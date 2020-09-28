package io.github.acnaweb.poc.kafka;

import java.math.BigDecimal;
import java.util.UUID;

public class NewOrderMain {
	public static void main(String[] args) {
		try (KafkaDispatcher<Order> orderDispatcher = new KafkaDispatcher<Order>()) {
			try (KafkaDispatcher<String> emailDispatcher = new KafkaDispatcher<String>();) {
				String email = Math.random() + "@email.com";

				for (int i = 1; i <= 10; i++) {

					String orderId = UUID.randomUUID().toString();
					BigDecimal amount = new BigDecimal(Math.random() * 5000 + 1);

					Order value = new Order(orderId, email, amount);
					orderDispatcher.send("ECOMMERCE_NEW_ORDER", email, value);

					emailDispatcher.send("ECOMMERCE_SEND_EMAIL", email, "Obrigado pelo seu pedido");
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
