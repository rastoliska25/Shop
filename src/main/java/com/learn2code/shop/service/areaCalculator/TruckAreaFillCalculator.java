package com.learn2code.shop.service.areaCalculator;

import com.learn2code.shop.Logging;
import com.learn2code.shop.domain.Statue;
import com.learn2code.shop.domain.Truck;
import org.springframework.kafka.core.KafkaTemplate;

import javax.swing.*;
import java.util.*;

public class TruckAreaFillCalculator {
    private final KafkaTemplate<String, Statue> kafkaTemplate;

    private static final String TOPIC = "demo";
    List<Statue> statues = new ArrayList<>();

    List<Statue> statuesToInsert = new ArrayList<>();
    List<Statue> statuesToProduceBack = new ArrayList<>();

    List<Rectangle> statuesDrawing = new ArrayList<>();
    Truck truck;

    public TruckAreaFillCalculator(KafkaTemplate<String, Statue> kafkaTemplate, List<Statue> statues, Truck truck) {
        this.kafkaTemplate = kafkaTemplate;
        this.statues = statues;
        this.truck = truck;
    }

    public List<Statue> calculation() {
        TruckFilling<Object> testPack = new TruckFilling<>(truck.getTransportWidth(), truck.getTransportLength());
        Rectangle block = new Rectangle();

        statues.sort(Comparator.comparing(Statue::getLength));
        Collections.reverse(statues);

        statues.forEach(
                (statue) -> {
                    if (statue.getLength() >= statue.getWidth()) {
                        if (testPack.insert(Math.toIntExact(statue.getWidth()), Math.toIntExact(statue.getLength()), block) == null) {
                            Logging.logger.info("nepreslo" + statue);
                            statuesToProduceBack.add(statue);
                        } else {
                            Logging.logger.info("preslo" + statue);
                            statuesToInsert.add(statue);
                        }
                    } else {
                        if (testPack.insert(Math.toIntExact(statue.getLength()), Math.toIntExact(statue.getWidth()), block) == null) {
                            Logging.logger.info("nepreslo" + statue);
                            statuesToProduceBack.add(statue);
                        } else {
                            Logging.logger.info("preslo" + statue);
                            statuesToInsert.add(statue);
                        }
                    }
                });

        navratSochDoKafky(statuesToProduceBack);

        statuesDrawing = testPack.statuesToDraw();
        System.out.println(statuesDrawing);

        vykreslenie(statuesDrawing, statuesToInsert);

        return statuesToInsert;
    }

    void navratSochDoKafky(List<Statue> statues) {
        statues.forEach(
                (statue) -> {
                    Logging.logger.info("Published statue " + statue);
                    kafkaTemplate.send(TOPIC, statue);
                });
    }

    void vykreslenie(List<Rectangle> statuesToDraw, List<Statue> statuesToInsert) {
        JFrame jFrame = new JFrame(truck.getName() + "   " + truck.getTransportWidth() + "x" + truck.getTransportLength());
        Drawing drawing = new Drawing(statuesToDraw, statuesToInsert);
        jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jFrame.setSize((truck.getTransportWidth()) / 4, (truck.getTransportLength()) / 4);
        jFrame.setVisible(true);
        jFrame.setIgnoreRepaint(true);
        jFrame.add(drawing);
    }
}
