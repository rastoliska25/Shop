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
        TestPack testPack = new TestPack(2, 300, 400); //3 trucky na naplnenie
        ArrayList<Rectangle> blocks = new ArrayList<>();

        blocks.add(new Rectangle("Test1", 150, 200));
        blocks.add(new Rectangle("Test2", 150, 200));
        blocks.add(new Rectangle("Test3", 150, 200));
        blocks.add(new Rectangle("Test4", 150, 200));
        blocks.add(new Rectangle("Test5", 150, 200));

        blocks.sort(new Comparator<Rectangle>() {
            @Override
            public int compare(Rectangle a, Rectangle b) {

                return (Double.compare(b.a, a.a)); //sortovanie
            }
        });

        testPack.joint(blocks);
        Iterator<Rectangle> blocksItr = blocks.iterator();
        while (blocksItr.hasNext()) {
            Rectangle block = blocksItr.next();
            if (block.fit != null) {
                if (block.fit.toRoot) {
                    System.out.format("%32s", "zaciatok");
                    System.out.println("");
                    System.out.format("%32s%24s%16s%16s%16s", "Meno:", "x", "y", "w", "h");
                    System.out.println("");
                }
                System.out.format("%32s%24s%16s%16s%16s", block.name, block.fit.x, block.fit.y, block.a, block.b);
                System.out.println("");
            }
        }

        System.out.println("");
    }
}
