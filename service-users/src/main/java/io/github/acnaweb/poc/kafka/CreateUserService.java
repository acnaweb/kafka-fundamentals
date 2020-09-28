package io.github.acnaweb.poc.kafka;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.UUID;
import java.util.concurrent.ExecutionException;

import org.apache.kafka.clients.consumer.ConsumerRecord;

public class CreateUserService {
	private Connection connection;

	CreateUserService() throws SQLException {
		String url = "jdbc:sqlite:target/users_database.db";
		this.connection = DriverManager.getConnection(url);

		try {
			connection.createStatement()
					.execute("create table users (uuid varchar(200) primary key, email varchar(200))");
		} catch (Exception e) {
		}

	}

	public static void main(String[] args) throws SQLException {
		CreateUserService createUserService = new CreateUserService();

		KafkaService<Order> kafkaService = new KafkaService<Order>(CreateUserService.class.getSimpleName(),
				"ECOMMERCE_NEW_ORDER", createUserService::consume, Order.class, new HashMap<String, String>());
		kafkaService.run();
	}

	public void consume(ConsumerRecord<String, Order> record) throws InterruptedException, ExecutionException {
		Order order = record.value();

		try {
			if (isNewUser(order.getEmail())) {
				insertNewuser(order.getEmail());

			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	private void insertNewuser(String email) throws SQLException {
		System.out.println("inserindo " + " " + email);
		PreparedStatement stmt = connection.prepareStatement("insert into users(uuid, email) values(?, ? )");
		stmt.setString(1, UUID.randomUUID().toString());
		stmt.setString(2, email);
		stmt.execute();
	}

	private boolean isNewUser(String email) throws SQLException {
		PreparedStatement stmt = connection.prepareStatement("select uuid from users where email = ? ");
		stmt.setString(1, email);
		ResultSet rs = stmt.executeQuery();

		return !rs.next();
	}

}
