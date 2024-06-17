package spharos.nu.goods.domain.goods.kafka.config;

import java.util.HashMap;
import java.util.Map;

import org.apache.kafka.clients.CommonClientConfigs;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.config.SaslConfigs;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.support.serializer.JsonSerializer;

public class CommonJsonSerializer {

	static Map<String, Object> getStringObjectMap(String bootstrapServer, String apiKey, String apiSecret) {
		Map<String, Object> props = new HashMap<>();

		props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServer);
		props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
		props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
		props.put(org.springframework.kafka.support.serializer.JsonSerializer.TYPE_MAPPINGS,
			"GoodsCreateEventDto:spharos.nu.goods.domain.goods.dto.event.GoodsCreateEventDto," +
			"GoodsDeleteEventDto:spharos.nu.goods.domain.goods.dto.event.GoodsDeleteEventDto," +
			"GoodsDisableEventDto:spharos.nu.goods.domain.goods.dto.event.GoodsDisableEventDto," +
			"GoodsStatusEventDto:spharos.nu.goods.domain.goods.dto.event.GoodsStatusEventDto"
		);
		/*confluent kafka 연결을 위한 설정*/
		props.put(CommonClientConfigs.SECURITY_PROTOCOL_CONFIG,"SASL_SSL");
		props.put(SaslConfigs.SASL_MECHANISM,"PLAIN");
		props.put(SaslConfigs.SASL_JAAS_CONFIG,String.format("org.apache.kafka.common.security.plain.PlainLoginModule required username=\"%s\" password=\"%s\";", apiKey, apiSecret));

		return props;
	}
}
