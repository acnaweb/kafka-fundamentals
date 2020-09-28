package io.github.acnaweb.poc.kafka;

import java.math.BigDecimal;
import java.util.UUID;

public class NewOrderMain {
	public static void main(String[] args) {
		try (KafkaDispatcher<Order> orderDispatcher = new KafkaDispatcher<Order>()) {
			try (KafkaDispatcher<String> emailDispatcher = new KafkaDispatcher<String>();) {

				for (int i = 1; i <= 10; i++) {

					String userId = UUID.randomUUID().toString();
					String orderId = UUID.randomUUID().toString();
					BigDecimal amount = new BigDecimal(Math.random() * 5000 + 1);

					String email = Math.random() + "@email.com";
					Order value = new Order(orderId, userId, email, amount);
					orderDispatcher.send("ECOMMERCE_NEW_ORDER", userId, value);

					emailDispatcher.send("ECOMMERCE_SEND_EMAIL", userId, "Obrigado pelo seu pedido");
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
