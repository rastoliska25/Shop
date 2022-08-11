package com.learn2code.shop.service;

import com.learn2code.shop.db.repository.StatueRepository;
import com.learn2code.shop.db.repository.TruckRepository;
import com.learn2code.shop.domain.Statue;
import com.learn2code.shop.domain.Truck;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.*;
import java.util.stream.IntStream;

@EnableKafka
public class TruckFillCalculation {

    private TruckRepository truckRepository;

    private StatueRepository statueRepository;

    private KafkaTemplate<String, Statue> kafkaTemplate;

    private static final String TOPIC = "demo";
    List<Statue> statues = new ArrayList<>();

    public TruckFillCalculation(TruckRepository truckRepository, StatueRepository statueRepository, KafkaTemplate<String, Statue> kafkaTemplate, List<Statue> statues) {
        this.truckRepository = truckRepository;
        this.statueRepository = statueRepository;
        this.kafkaTemplate = kafkaTemplate;
        this.statues = statues;
    }

    public TruckFillCalculation(List<Statue> statues) {
        this.statues = statues;
    }

    List<Truck> trucks = new ArrayList<>();
    int capacity;

    int NB_ITEMS;
    int[][] matrix;

    List<Statue> statueList = new ArrayList<>();

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


        memoization(capacity);//vypocet pre najlepsi vyber
        System.out.println("\n");
        ulozenieSoch(statueList);//ulozenie vybranych do DB
        System.out.println("\n");
        navratSochDoKafky(statues);
    }

    public long memoization(int capacity) {
        NB_ITEMS = statues.size();

        matrix = new int[NB_ITEMS + 1][capacity + 1];

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

        for (int i = NB_ITEMS; i > 0 && res > 0; i--) {
            if (res != matrix[i - 1][w]) {
                statueList.add(statues.get(i - 1));
                res -= Math.toIntExact(statues.get(i - 1).getWeight());
                w -= Math.toIntExact(statues.get(i - 1).getWeight());
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

        return sumOfSelectedStatues;
    }

    void vypisSoch(List<Statue> statues) {
        statues.forEach(
                System.out::println);
    }

    //ulozenie do db
    void ulozenieSoch(List<Statue> statuesList) {
        statuesList.forEach(
                (statue) -> {
                    statueRepository.save(statue);
                });
    }

    void navratSochDoKafky(List<Statue> statues) {
        statues.forEach(
                (statue) -> {
                    kafkaTemplate.send(TOPIC, statue);
                    System.out.println("Published statue " + statue);
                });
    }

}
