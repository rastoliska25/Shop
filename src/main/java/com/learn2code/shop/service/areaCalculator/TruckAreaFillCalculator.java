package com.learn2code.shop.service.areaCalculator;

import com.learn2code.shop.domain.Statue;
import com.learn2code.shop.domain.Truck;
import org.springframework.kafka.core.KafkaTemplate;

import java.util.*;

public class TruckAreaFillCalculator {
    private final KafkaTemplate<String, Statue> kafkaTemplate;

    private static final String TOPIC = "demo";
    List<Statue> statues = new ArrayList<>();

    List<Statue> statuesToInsert = new ArrayList<>();
    List<Statue> statuesToProduceBack = new ArrayList<>();

    Truck truck;

    public TruckAreaFillCalculator(KafkaTemplate<String, Statue> kafkaTemplate, List<Statue> statues, Truck truck) {
        this.kafkaTemplate = kafkaTemplate;
        this.statues = statues;
        this.truck = truck;
    }

    public List<Statue> calculation() {
        //TruckFilling<Object> testPack = new TruckFilling<>(300, 400);
        TruckFilling<Object> testPack = new TruckFilling<>(truck.getTransportWidth() - 1000, truck.getTransportLength() - 2000);
        Rectangle block = new Rectangle();
        Rectangle blockInserted = new Rectangle();

        statues.sort(Comparator.comparing(Statue::getLength));
        Collections.reverse(statues);

        statues.forEach(
                (statue) -> {
                    if (statue.getLength() >= statue.getWidth()) {
                        if (testPack.insert(Math.toIntExact(statue.getWidth()), Math.toIntExact(statue.getLength()), block) == null) {
                            System.out.println("nepreslo" + statue);
                            statuesToProduceBack.add(statue);
                        } else {
                            System.out.println("preslo" + statue);
                            statuesToInsert.add(statue);
                        }
                    } else {
                        if (testPack.insert(Math.toIntExact(statue.getLength()), Math.toIntExact(statue.getWidth()), block) == null) {
                            System.out.println("nepreslo" + statue);
                            statuesToProduceBack.add(statue);
                        } else {
                            System.out.println("preslo" + statue);
                            statuesToInsert.add(statue);
                        }
                    }
                });

        navratSochDoKafky(statuesToProduceBack);

        return statuesToInsert;
    }

    void navratSochDoKafky(List<Statue> statues) {
        statues.forEach(
                (statue) -> {
                    System.out.println("Published statue " + statue);
                    kafkaTemplate.send(TOPIC, statue);
                });
    }
}
