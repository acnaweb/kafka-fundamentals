package io.github.acnaweb.poc.kafka;

import java.math.BigDecimal;

public class Order {
	private final String orderId, email;
	private final BigDecimal amount;

	public Order(String orderId, String email, BigDecimal amount) {
		this.orderId = orderId;
		this.email = email;
		this.amount = amount;
	}

	public String getOrderId() {
		return orderId;
	}

	public String getEmail() {
		return email;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Order [orderId=");
		builder.append(orderId);
		builder.append(", email=");
		builder.append(email);
		builder.append(", amount=");
		builder.append(amount);
		builder.append("]");
		return builder.toString();
	}

}
