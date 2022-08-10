package com.learn2code.Shop.db.service;

import com.learn2code.Shop.domain.Statue;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.test.annotation.DirtiesContext;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.IntStream;

@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class TruckFillCalculationTest {

    int capacity;

    @Test
    void memoReturnTestOne() {
        capacity = 1500;
        List<Statue> statues = new ArrayList<>();
        statues.add(new Statue("socha 1", 524L));
        statues.add(new Statue("socha 2", 311L));
        statues.add(new Statue("socha 3", 421L));
        statues.add(new Statue("socha 4", 721L));
        statues.add(new Statue("socha 5", 452L));
        statues.add(new Statue("socha 8", 271L));

        Assertions.assertEquals(1484, memoization(statues)); //má byť 1484
    }

    @Test
    void memoReturnTestTwo() {
        capacity = 2000;
        List<Statue> statues = new ArrayList<>();
        statues.add(new Statue("socha 1", 524L));
        statues.add(new Statue("socha 2", 452L));
        statues.add(new Statue("socha 3", 421L));
        statues.add(new Statue("socha 4", 721L));
        statues.add(new Statue("socha 5", 452L));
        statues.add(new Statue("socha 6", 741L));
        statues.add(new Statue("socha 7", 821L));
        statues.add(new Statue("socha 8", 271L));
        Assertions.assertEquals(1996, memoization(statues)); //má byť 1996
    }

    @Test
    void memoReturnTestThree() {
        capacity = 2300;
        List<Statue> statues = new ArrayList<>();
        statues.add(new Statue("socha 1", 221L));
        statues.add(new Statue("socha 2", 482L));
        statues.add(new Statue("socha 3", 532L));
        statues.add(new Statue("socha 4", 452L));
        statues.add(new Statue("socha 5", 4333L));
        statues.add(new Statue("socha 6", 784L));
        statues.add(new Statue("socha 7", 801L));
        statues.add(new Statue("socha 8", 254L));
        statues.add(new Statue("socha 9", 152L));
        statues.add(new Statue("socha 10", 711L));
        Assertions.assertEquals(2298, memoization(statues)); //má byť 2298
    }

    @Test
    void multipleMemoReturnTest() {
        IntStream.range(0, 100)
                .forEach(i -> {
                    capacity = new Random().nextInt(1500, 10000);

                    List<Statue> statues = new ArrayList<>();
                    IntStream.range(0, 100)
                            .forEach(j -> {
                                statues.add(new Statue("socha" + new Random().nextInt(1, 100), new Random().nextLong(100, 350)));
                            });
                    Assertions.assertTrue(capacity >= memoization(statues));
                });
    }

    @Test
    public Long memoization(List<Statue> statues) {
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

        int res = matrix[NB_ITEMS][capacity];
        int w = capacity;

        List<Statue> statueList = new ArrayList<>();

        for (int i = NB_ITEMS; i > 0 && res > 0; i--) {
            if (res != matrix[i - 1][w]) {
                statueList.add(statues.get(i - 1));
                res -= Math.toIntExact(statues.get(i - 1).getWeight());
                w -= Math.toIntExact(statues.get(i - 1).getWeight());
                statues.remove(statues.get(i - 1));
            }
        }

        return statueList.stream()
                .map(Statue::getWeight)
                .mapToLong(Long::longValue)
                .sum();
    }
}