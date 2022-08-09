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
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Properties;

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
        List<Statue> statuesToProduceBack = new ArrayList<>();
        try {
            int x = 0;
            ConsumerRecords<String, Statue> records = consumer.poll(Duration.ofMillis(300));

            for (ConsumerRecord<String, Statue> record : records) {
                System.out.println("consumed through poll:" + record.value());
                statues.add(record.value());

                /*
                if (record.value().getWeight() < 350) {
                    statues.add(record.value());
                } else {
                    statuesToProduceBack.add(record.value());
                }
                */

            }
        } finally {
            consumer.close();
        }

        //vratenie do consumera

        for (Statue statue : statuesToProduceBack) {
            kafkaTemplate.send(TOPIC, statue);
            System.out.println("published statue " + statue);
        }


        TruckFillCalculation truckFillCalculation = new TruckFillCalculation(truckRepository, statueRepository, statues);
        truckFillCalculation.calculate();

    }
}




