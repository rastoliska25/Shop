package com.learn2code.Shop.db.service;

import com.learn2code.Shop.db.repository.StatueRepository;
import com.learn2code.Shop.db.repository.TruckRepository;
import com.learn2code.Shop.domain.Statue;
import com.learn2code.Shop.domain.Truck;
import org.springframework.kafka.core.KafkaTemplate;

import java.util.*;

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

        int statuesWeightSum = statues.stream().mapToInt(o -> Math.toIntExact(o.getWeight())).sum();

        System.out.println("\n" + "hmotnost soch dokopy: " + statuesWeightSum + "\n");

        trucks = truckRepository.findAll();

        trucks.sort(Comparator.comparing(Truck::getTransportWeight));  //potriedenie trucks na základe transportWeight

        Truck truckWithHighestTransportWeight = trucks.get(trucks.size() - 1);
        for (Truck truck : trucks) {
            System.out.println(truck);
        }

        System.out.println("\n Truck s najvacsou prepravnou hmotnostou: " + truckWithHighestTransportWeight + "\n");
        capacity = truckWithHighestTransportWeight.getTransportWeight();

        System.out.println("kapacita: " + capacity);
        System.out.println(statues.size());
        memoization(statues);
    }

    public void memoization(List<Statue> statues) {
        int NB_ITEMS = statues.size();

        int[][] matrix = new int[NB_ITEMS + 1][capacity + 1];

        for (int i = 0; i <= capacity; i++)
            matrix[0][i] = 0;

        for (int i = 1; i <= NB_ITEMS; i++) {
            for (int j = 0; j <= capacity; j++) {
                if (statues.get(i - 1).getWeight() > j) matrix[i][j] = matrix[i - 1][j];
                else
                    matrix[i][j] = Math.max(matrix[i - 1][j], matrix[i - 1][j - Math.toIntExact(statues.get(i - 1).getWeight())] + Math.toIntExact(statues.get(i - 1).getWeight()));
            }
        }

        //všetky sochy:
        System.out.println("\n Vsetky sochy:");
        vypisSoch(statues);

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

        System.out.println("\nVybrané sochy:");
        vypisSoch(statueList);

        long sumOfSelectedStatues = statueList.stream()
                .map(Statue::getWeight)
                .mapToLong(Long::longValue)
                .sum();

        System.out.println("\nSuma vybranych soch:" + sumOfSelectedStatues);

        System.out.println("\nSochy na produce:");
        vypisSoch(statues);

        //vrátenie do kafky
        for (Statue statue : statues) {
            kafkaTemplate.send(TOPIC, statue);
            System.out.println("Published statue " + statue);
        }
    }

    void vypisSoch(List<Statue> statues) {
        for (Statue statue : statues) {
            System.out.println(statue.getName());
        }
    }




























    /*
    //memoization original
    public void memoization(int[] statuesIds, int[] statuesWeightArray, String[] statuesNamesArray, Truck truckWithHighestTransportWeight) {
        int NB_ITEMS = statuesWeightArray.length;

        int[][] matrix = new int[NB_ITEMS + 1][capacity + 1];

        for (int i = 0; i <= capacity; i++)
            matrix[0][i] = 0;

        for (int i = 1; i <= NB_ITEMS; i++) {
            for (int j = 0; j <= capacity; j++) {
                if (statuesWeightArray[i - 1] > j) matrix[i][j] = matrix[i - 1][j];
                else
                    matrix[i][j] = Math.max(matrix[i - 1][j], matrix[i - 1][j - statuesWeightArray[i - 1]] + statuesWeightArray[i - 1]);
            }
        }

        //výpis vybraných sôch
        int res = matrix[NB_ITEMS][capacity];
        int w = capacity;
        List<Integer> statueSolution = new ArrayList<>();
        List<String> statueSolutionNames = new ArrayList<>();
        List<Integer> statueSolutionWeights = new ArrayList<>();

        for (int i = NB_ITEMS; i > 0 && res > 0; i--) {
            if (res != matrix[i - 1][w]) {
                statueSolution.add(statuesIds[i - 1]);
                statueSolutionNames.add(statuesNamesArray[i - 1]);
                statueSolutionWeights.add(statuesWeightArray[i - 1]);

                res -= statuesWeightArray[i - 1];
                w -= statuesWeightArray[i - 1];
            }
        }

        System.out.println("\n");
        System.out.println("ID vybranych soch: " + statueSolution);
        System.out.println("Mena vybranych soch: " + statueSolutionNames);
        System.out.println("Jednotlive hmotnosti vybranych soch: " + statueSolutionWeights);
        System.out.println("Suma hmotnosti vlozenych soch: " + matrix[NB_ITEMS][capacity]);

        //db Insert
        insertDB(statueSolutionNames, statueSolutionWeights, truckWithHighestTransportWeight);


    }

    public void insertDB(List<String> statueSolutionNames, List<Integer> statueSolutionWeights, Truck truckWithHighestTransportWeight) {

        //lists to arrays
        int[] statuesWeightArray = statueSolutionWeights.stream()
                .mapToInt(Integer::intValue)
                .toArray();

        String[] statuesNamesArray = statueSolutionNames.toArray(new String[0]);

        for (int i = 0; i < statuesWeightArray.length; i++) {
            for (Statue statue : statues) {
                if ((statue.getWeight() == statuesWeightArray[i]) && (Objects.equals(statue.getName(), statuesNamesArray[i]))) {
                    statue.setTruckId(truckWithHighestTransportWeight.getId());
                    statueRepository.save(statue);
                }
            }
        }
    }

     */


}
