package com.learn2code.shop.service.weightCalculation;

import com.learn2code.shop.Logging;
import com.learn2code.shop.db.repository.StatueRepository;
import com.learn2code.shop.db.repository.TruckRepository;
import com.learn2code.shop.domain.Statue;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.core.KafkaTemplate;

import java.util.*;
import java.util.stream.IntStream;

@EnableKafka
public class TruckFillCalculation {
    private KafkaTemplate<String, Statue> kafkaTemplate;
    private static final String TOPIC = "demo";
    List<Statue> statues;

    int capacity;

    public TruckFillCalculation(KafkaTemplate<String, Statue> kafkaTemplate, List<Statue> statues, int capacity) {
        this.kafkaTemplate = kafkaTemplate;
        this.statues = statues;
        this.capacity = capacity;
    }

    public TruckFillCalculation(List<Statue> statues) {
        this.statues = statues;
    }

    int NB_ITEMS;
    int[][] matrix;

    List<Statue> statueList = new ArrayList<>();

    public List<Statue> calculate() {

        Logging.logger.info("Kapacita: " + capacity);
        Logging.logger.info("\nPocet vsetkych soch: " + statues.size());


        memoization(capacity);//vypocet pre najlepsi vyber
        Logging.logger.info("\n");
        navratSochDoKafky(statues);

        return  (statueList);//navrat soch ktore su vybrane do trucku
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
        Logging.logger.info("\nVsetky sochy:");
        vypisSoch(statues);
        Logging.logger.info("\nHmotnost vsetkych soch dokopy: " + statues.stream().mapToInt(o -> Math.toIntExact(o.getWeight())).sum() + "\n");

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

        Logging.logger.info("\nVybrane sochy:");
        vypisSoch(statueList);

        long sumOfSelectedStatues = statueList.stream()
                .map(Statue::getWeight)
                .mapToLong(Long::longValue)
                .sum();

        Logging.logger.info("\nSuma vybranych soch:" + sumOfSelectedStatues);
        Logging.logger.info("\nSochy na produce:");
        vypisSoch(statues);
        Logging.logger.info("\nPublished sochy:");

        return sumOfSelectedStatues;
    }

    void vypisSoch(List<Statue> statues) {
        statues.forEach(
                System.out::println);
    }

    void navratSochDoKafky(List<Statue> statues) {
        statues.forEach(
                (statue) -> {
                    Logging.logger.info("Published statue " + statue);
                    kafkaTemplate.send(TOPIC, statue);
                });
    }

}
