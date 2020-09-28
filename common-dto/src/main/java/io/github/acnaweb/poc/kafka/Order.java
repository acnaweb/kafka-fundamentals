package io.github.acnaweb.poc.kafka;

import java.math.BigDecimal;

public class Order {
	private final String orderId, userId, email;
	private final BigDecimal amount;

	public Order(String orderId, String userId, String email, BigDecimal amount) {
		this.orderId = orderId;
		this.userId = userId;
		this.email = email;
		this.amount = amount;
	}

	public String getUserId() {
		return userId;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Order [orderId=");
		builder.append(orderId);
		builder.append(", userId=");
		builder.append(userId);
		builder.append(", email=");
		builder.append(email);
		builder.append(", amount=");
		builder.append(amount);
		builder.append("]");
		return builder.toString();
	}

}
