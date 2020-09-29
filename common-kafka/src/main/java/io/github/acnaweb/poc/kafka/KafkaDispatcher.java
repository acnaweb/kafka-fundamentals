package io.github.acnaweb.poc.kafka;

import java.io.Closeable;
import java.util.Properties;
import java.util.concurrent.ExecutionException;

import org.apache.kafka.clients.producer.Callback;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;

public class KafkaDispatcher<T> implements Closeable {
	private final KafkaProducer<String, T> producer;

	public KafkaDispatcher() {
		producer = new KafkaProducer<String, T>(properties());
	}

	private static Properties properties() {
		Properties result = new Properties();
		result.setProperty(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "127.0.0.1:9092");
		result.setProperty(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
		result.setProperty(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, GsonSerializer.class.getName());
		result.setProperty(ProducerConfig.ACKS_CONFIG, "all");
		return result;
	}

	public void send(String topic, String key, T value) throws InterruptedException, ExecutionException {
		ProducerRecord<String, T> record = new ProducerRecord<String, T>(topic, key, value);
		Callback callback = (data, ex) -> {
			if (ex != null) {
				ex.printStackTrace();
				return;
			}
			// System.out.println("enviando " + data.topic() + ":" + data.offset() + ":" +
			// data.partition());
		};

		producer.send(record, callback).get();
	}

	@Override
	public void close() {
		producer.close();
	}

}
