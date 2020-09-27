package io.github.acnaweb.poc.kafka;

import java.math.BigDecimal;
import java.util.UUID;

public class NewOrderMain {
	public static void main(String[] args) {
		try (KafkaDispatcher<Order> orderDispatcher = new KafkaDispatcher<Order>()) {
			try (KafkaDispatcher<Email> emailDispatcher = new KafkaDispatcher<Email>();) {

				for (int i = 1; i <= 10; i++) {

					String userId = UUID.randomUUID().toString();
					String orderId = UUID.randomUUID().toString();
					BigDecimal amount = new BigDecimal(Math.random() * 5000 + 1);

					Order value = new Order(userId, orderId, amount);
					orderDispatcher.send("ECOMMERCE_NEW_ORDER", userId, value);

					Email email = new Email("Pedido aceito", "Muito obrigado");
					emailDispatcher.send("ECOMMERCE_SEND_EMAIL", userId, email);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
