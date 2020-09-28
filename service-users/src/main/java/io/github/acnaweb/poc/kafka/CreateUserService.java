package io.github.acnaweb.poc.kafka;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.concurrent.ExecutionException;

import org.apache.kafka.clients.consumer.ConsumerRecord;

public class CreateUserService {
	private Connection connection;

	CreateUserService() throws SQLException {
		String url = "jdbc:sqlite:target/users_database.db";
		this.connection = DriverManager.getConnection(url);

		connection.createStatement().execute("create table users (uuid varchar(200) primary key, email varchar(200))");

	}

	public static void main(String[] args) throws SQLException {
		CreateUserService createUserService = new CreateUserService();

		KafkaService<Order> kafkaService = new KafkaService<Order>(CreateUserService.class.getSimpleName(),
				"ECOMMERCE_NEW_ORDER", createUserService::consume, Order.class, new HashMap<String, String>());
		kafkaService.run();
	}

	public void consume(ConsumerRecord<String, Order> record) throws InterruptedException, ExecutionException {
		System.out.println(record.value());
		
		if (đ)
	}

}
