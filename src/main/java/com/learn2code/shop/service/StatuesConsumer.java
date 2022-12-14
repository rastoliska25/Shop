package com.learn2code.shop.service;

import com.learn2code.shop.Logging;
import com.learn2code.shop.ShopApplication;
import com.learn2code.shop.db.repository.StatueRepository;
import com.learn2code.shop.db.repository.TruckRepository;
import com.learn2code.shop.domain.Statue;
import com.learn2code.shop.domain.Truck;
import com.learn2code.shop.service.areaCalculator.TruckAreaFillCalculator;
import com.learn2code.shop.service.volumeCalculator.visualization.TruckPacker;
import com.learn2code.shop.service.weightCalculation.TruckFillCalculation;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.Duration;
import java.util.*;

@Component
@EnableKafka
public class StatuesConsumer {
    private final StatueRepository statueRepository;
    private final TruckRepository truckRepository;
    private final KafkaTemplate<String, Statue> kafkaTemplate;

    private static final String TOPIC = "demo";

    List<Truck> trucks = new ArrayList<>();

    List<Statue> statuesWeightSelection = new ArrayList<>();

    List<Statue> statuesToInsert = new ArrayList<>();

    int capacity;

    public StatuesConsumer(StatueRepository statueRepository, TruckRepository truckRepository, KafkaTemplate<String, Statue> kafkaTemplate) {
        this.statueRepository = statueRepository;
        this.truckRepository = truckRepository;
        this.kafkaTemplate = kafkaTemplate;
    }

    public void consumeStatues() throws IOException {
        Properties props = new Properties();
        props.setProperty(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:29092");
        props.setProperty(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        props.setProperty(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class.getName());
        props.setProperty(ConsumerConfig.GROUP_ID_CONFIG, "mygroup");
        props.setProperty(JsonDeserializer.TRUSTED_PACKAGES, "*");

        KafkaConsumer<String, Statue> consumer = new KafkaConsumer<String, Statue>(props);

        consumer.subscribe(Collections.singletonList("demo")); //naviazanie na topic

        List<Statue> statues = new ArrayList<>();

        //hlada iba vo??n?? trucky a najde truck s highest transport weight
        trucks = truckRepository.findByUsed(null);

        if (trucks.size() > 0) {

            //consumuje len ak je volny truck
            try {
                ConsumerRecords<String, Statue> records = consumer.poll(Duration.ofMillis(300));

                for (ConsumerRecord<String, Statue> record : records) {
                    Logging.logger.info("consumed through poll: {}", record.value());
                    statues.add(record.value());
                }
            } finally {
                consumer.close();
            }

            trucks.sort(Comparator.comparing(Truck::getTransportWeight));
            Truck truckWithHighestTransportWeight = trucks.get(trucks.size() - 1);

            //da?? truck ako used do DB
            truckWithHighestTransportWeight.setUsed((byte) 1);
            truckRepository.save(truckWithHighestTransportWeight);

            Logging.logger.info("\n");
            Logging.logger.info("Trucky k dispozicii: ");
            trucks.forEach(
                    System.out::println);

            Logging.logger.info("\nTruck s najvacsou prepravnou hmotnostou: " + truckWithHighestTransportWeight + "\n");
            capacity = truckWithHighestTransportWeight.getTransportWeight();


            //hmotnosti
            TruckFillCalculation truckFillCalculation = new TruckFillCalculation(kafkaTemplate, statues, capacity);
            statuesWeightSelection = truckFillCalculation.calculate();

            //ulo??enie prebehne a?? po naukladani s??ch do trucku
            //ulozenieSoch(statuesIntoTruck);
            TruckAreaFillCalculator truckAreaFillCalculator = new TruckAreaFillCalculator(kafkaTemplate, statuesWeightSelection, truckWithHighestTransportWeight);
            statuesToInsert = truckAreaFillCalculator.calculation();

            ulozenieSoch(statuesToInsert, truckWithHighestTransportWeight.getId());


            //3D len na test
            //new TruckPacker().packuj(truckWithHighestTransportWeight, statues);

        } else
            Logging.logger.info("There is no truck available!");
    }

    //ulozenie do db
    void ulozenieSoch(List<Statue> statuesList, int id) {
        statuesList.forEach(
                statue -> {
                    statue.setTruckId(id);
                    statueRepository.save(statue);
                });
    }
}




