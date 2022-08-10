package com.learn2code.Shop.db.service;

import com.learn2code.Shop.db.repository.StatueRepository;
import com.learn2code.Shop.db.repository.TruckRepository;
import com.learn2code.Shop.domain.Statue;
import com.learn2code.Shop.domain.Truck;
import org.springframework.kafka.core.KafkaTemplate;

import java.util.*;
import java.util.stream.IntStream;

public class TruckFillCalculation {
    private final TruckRepository truckRepository;
    private final StatueRepository statueRepository;
    private final KafkaTemplate<String, Statue> kafkaTemplate;
    private static final String TOPIC = "demo";
    List<Statue> statues = new ArrayList<>();

    public TruckFillCalculation(TruckRepository truckRepository, StatueRepository statueRepository, KafkaTemplate<String, Statue> kafkaTemplate, List<Statue> statues) {
        this.truckRepository = truckRepository;
        this.statueRepository = statueRepository;
        this.kafkaTemplate = kafkaTemplate;
        this.statues = statues;
    }

    List<Truck> trucks = new ArrayList<>();
    int capacity;

    public void calculate() {
        trucks = truckRepository.findAll();

        trucks.sort(Comparator.comparing(Truck::getTransportWeight));  //potriedenie trucks na základe transportWeight

        Truck truckWithHighestTransportWeight = trucks.get(trucks.size() - 1);
        System.out.println("\n");
        System.out.println("Trucky k dispozicii: ");
        trucks.forEach(
                System.out::println);

        System.out.println("\nTruck s najvacsou prepravnou hmotnostou: " + truckWithHighestTransportWeight + "\n");
        capacity = truckWithHighestTransportWeight.getTransportWeight();

        System.out.println("Kapacita: " + capacity);
        System.out.println("\nPocet vsetkych soch: " + statues.size());
        memoization(statues);
    }

    public void memoization(List<Statue> statues) {
        int NB_ITEMS = statues.size();

        int[][] matrix = new int[NB_ITEMS + 1][capacity + 1];

        IntStream.range(0, capacity + 1)
                .forEach(index -> {
                    matrix[0][index] = 0;
                });

        IntStream.range(1, NB_ITEMS + 1)
                .forEach(i -> {
                    IntStream.range(0, capacity + 1)
                            .forEach(j -> {
                                if (statues.get(i - 1).getWeight() > j) matrix[i][j] = matrix[i - 1][j];
                                else
                                    matrix[i][j] = Math.max(matrix[i - 1][j], matrix[i - 1][j - Math.toIntExact(statues.get(i - 1).getWeight())] + Math.toIntExact(statues.get(i - 1).getWeight()));
                            });
                });

        //všetky sochy:
        System.out.println("\nVsetky sochy:");
        vypisSoch(statues);
        System.out.println("\nHmotnost vsetkych soch dokopy: " + statues.stream().mapToInt(o -> Math.toIntExact(o.getWeight())).sum() + "\n");

        //výpis vybraných sôch
        int res = matrix[NB_ITEMS][capacity];
        int w = capacity;

        List<Statue> statueList = new ArrayList<>();

        for (int i = NB_ITEMS; i > 0 && res > 0; i--) {
            if (res != matrix[i - 1][w]) {
                statueList.add(statues.get(i - 1));
                res -= Math.toIntExact(statues.get(i - 1).getWeight());
                w -= Math.toIntExact(statues.get(i - 1).getWeight());

                //uloži sochu rovno do DB
                statueRepository.save(statues.get(i - 1));

                //v statues ostanu len sochy, ktore sa pošlu spať do kafky
                statues.remove(statues.get(i - 1));
            }
        }

        System.out.println("\nVybrane sochy:");
        vypisSoch(statueList);

        long sumOfSelectedStatues = statueList.stream()
                .map(Statue::getWeight)
                .mapToLong(Long::longValue)
                .sum();

        System.out.println("\nSuma vybranych soch:" + sumOfSelectedStatues);

        System.out.println("\nSochy na produce:");
        vypisSoch(statues);
        System.out.println("\nPublished sochy:");

        statues.forEach(
                (statue) -> {
                    kafkaTemplate.send(TOPIC, statue);
                    System.out.println("Published statue " + statue);
                });
    }

    void vypisSoch(List<Statue> statues) {
        statues.forEach(
                System.out::println);
    }
}
