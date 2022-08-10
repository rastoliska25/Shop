package com.learn2code.Shop.db.service;

import com.learn2code.Shop.db.repository.StatueRepository;
import com.learn2code.Shop.db.repository.TruckRepository;
import com.learn2code.Shop.domain.Statue;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.*;
import java.util.stream.IntStream;

@Component
@EnableKafka
public class StatuesConsumer {
    private final StatueRepository statueRepository;
    private final TruckRepository truckRepository;
    private final KafkaTemplate<String, Statue> kafkaTemplate;

    private static final String TOPIC = "demo";

    public StatuesConsumer(StatueRepository statueRepository, TruckRepository truckRepository, KafkaTemplate<String, Statue> kafkaTemplate) {
        this.statueRepository = statueRepository;
        this.truckRepository = truckRepository;
        this.kafkaTemplate = kafkaTemplate;
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

        List<Statue> statues = new ArrayList<>();
        try {
            ConsumerRecords<String, Statue> records = consumer.poll(Duration.ofMillis(300));

            for (ConsumerRecord<String, Statue> record : records) {
                System.out.println("consumed through poll:" + record.value());
                statues.add(record.value());
            }
        } finally {
            consumer.close();
        }

        TruckFillCalculation truckFillCalculation = new TruckFillCalculation(truckRepository, statueRepository, kafkaTemplate, statues);
        truckFillCalculation.calculate();
    }
}




