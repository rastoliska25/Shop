package com.learn2code.shop.service.areaCalculator;

import com.learn2code.shop.db.repository.StatueRepository;
import com.learn2code.shop.db.repository.TruckRepository;
import com.learn2code.shop.domain.Statue;
import org.springframework.kafka.core.KafkaTemplate;

import java.util.*;

public class TruckAreaFillCalculator {
    private final TruckRepository truckRepository;
    private final StatueRepository statueRepository;
    private final KafkaTemplate<String, Statue> kafkaTemplate;

    private static final String TOPIC = "demo";
    List<Statue> statues = new ArrayList<>();

    public TruckAreaFillCalculator(TruckRepository truckRepository, StatueRepository statueRepository, KafkaTemplate<String, Statue> kafkaTemplate, List<Statue> statues) {
        this.truckRepository = truckRepository;
        this.statueRepository = statueRepository;
        this.kafkaTemplate = kafkaTemplate;
        this.statues = statues;
    }

    public void calculation() {
        TruckFilling<Object> testPack = new TruckFilling<>(300, 400);
        ArrayList<Rectangle> blocks = new ArrayList<>();

        System.out.println("");
    }
}
