package com.learn2code.Shop.db.service;

import com.learn2code.Shop.db.repository.StatueRepository;
import com.learn2code.Shop.domain.Statue;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Properties;

@Component
@EnableKafka
public class StatuesConsumer {

    private final StatueRepository statueRepository;

    public StatuesConsumer(StatueRepository statueRepository) {
        this.statueRepository = statueRepository;
    }

    public void consumeStatues() {
        Properties props = new Properties();
        props.setProperty(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:29092");
        props.setProperty(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        props.setProperty(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class.getName());
        props.setProperty(ConsumerConfig.GROUP_ID_CONFIG, "mygroup");
        props.setProperty(JsonDeserializer.TRUSTED_PACKAGES, "*");

        KafkaConsumer<String, Statue> consumer = new KafkaConsumer<String, Statue>(props);

        consumer.subscribe(Collections.singletonList("demo")); //naviazanie na topic

        try {
            int x = 0;
            ConsumerRecords<String, Statue> records = consumer.poll(Duration.ofMillis(100));

            for (ConsumerRecord<String, Statue> record : records) {
                System.out.println("consumed through poll:" + record.value());

                if (record.value().getWeight() < 500) {
                    record.value().setTruckId(500);
                    statueRepository.save(record.value());
                } else {
                    record.value().setTruckId(1);
                    statueRepository.save(record.value());
                }
            }
        } finally {
            consumer.close();
        }
    }
}




