package io.github.acnaweb.poc.kafka;

import java.io.Closeable;
import java.time.Duration;
import java.util.Collections;
import java.util.Map;
import java.util.Properties;
import java.util.UUID;
import java.util.regex.Pattern;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.StringDeserializer;

public class KafkaService<T> implements Closeable {
	private final KafkaConsumer<String, T> consumer;
	private final ConsumerFunction<T> function;

	public KafkaService(String groupId, String topic, ConsumerFunction<T> function, Class<T> type,
			Map<String, String> additionalProperties) {
		this.function = function;
		this.consumer = new KafkaConsumer<String, T>(properties(type, groupId, additionalProperties));
		this.consumer.subscribe(Collections.singleton(topic));
	}

	public KafkaService(String groupId, Pattern topic, ConsumerFunction<T> function, Class<T> type,
			Map<String, String> additionalProperties) {
		this.function = function;
		this.consumer = new KafkaConsumer<String, T>(properties(type, groupId, additionalProperties));
		this.consumer.subscribe(topic);
	}

	public void run() {
		while (true) {
			ConsumerRecords<String, T> records = consumer.poll(Duration.ofMillis(1000));

			if (!records.isEmpty()) {
				for (ConsumerRecord<String, T> record : records) {
					function.consume(record);
				}
			}
		}
	}

	private Properties properties(Class<T> type, String groupId, Map<String, String> additionalProperties) {
		Properties result = new Properties();
		result.setProperty(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "127.0.0.1:9092");
		result.setProperty(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
		result.setProperty(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, GsonDeserializer.class.getName());
		// tempo de commit
		result.setProperty(ConsumerConfig.MAX_POLL_RECORDS_CONFIG, "1");

		result.setProperty(ConsumerConfig.GROUP_ID_CONFIG, groupId);
		result.setProperty(ConsumerConfig.CLIENT_ID_CONFIG, UUID.randomUUID().toString());
		result.setProperty(GsonDeserializer.TYPE_CONFIG, type.getName());
		result.putAll(additionalProperties);

		return result;

	}

	@Override
	public void close() {
		consumer.close();

	}
}
