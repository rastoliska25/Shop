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

        capacity = 1800;
        List<Statue> statues = new ArrayList<>();
        IntStream.range(0, 100)
                .forEach(i -> {
                    statues.add(new Statue(1, "socha 1", 1, 610L, 343L, 436L, 792L, 5));
                    statues.add(new Statue(2, "socha 2", 1, 504L, 343L, 436L, 792L, 5));
                    statues.add(new Statue(3, "socha 3", 1, 318L, 343L, 436L, 792L, 5));
                    statues.add(new Statue(4, "socha 4", 1, 429L, 343L, 436L, 792L, 5));
                    statues.add(new Statue(5, "socha 5", 1, 701L, 343L, 436L, 792L, 5));
                    statues.add(new Statue(6, "socha 6", 1, 454L, 343L, 436L, 792L, 5));
                    statues.add(new Statue(7, "socha 7", 1, 270L, 343L, 436L, 792L, 5));
                    statues.add(new Statue(7, "socha 7", 1, 278L, 343L, 436L, 792L, 5));

                    Assertions.assertEquals(1799, memoization(statues)); //má byť 1499
                });
    }

    @Test
    void memoReturnTestTwo() {
        capacity = 2000;
        IntStream.range(0, 100)
                .forEach(i -> {
                    List<Statue> statues = new ArrayList<>();
                    statues.add(new Statue(1, "socha 1", 1, 626L, 343L, 436L, 792L, 5));
                    statues.add(new Statue(2, "socha 2", 1, 524L, 343L, 436L, 792L, 5));
                    statues.add(new Statue(3, "socha 3", 1, 311L, 343L, 436L, 792L, 5));
                    statues.add(new Statue(4, "socha 4", 1, 421L, 343L, 436L, 792L, 5));
                    statues.add(new Statue(5, "socha 5", 1, 721L, 343L, 436L, 792L, 5));
                    statues.add(new Statue(6, "socha 6", 1, 452L, 343L, 436L, 792L, 5));
                    statues.add(new Statue(7, "socha 7", 1, 282L, 343L, 436L, 792L, 5));
                    statues.add(new Statue(8, "socha 8", 1, 602L, 343L, 436L, 792L, 5));
                    Assertions.assertEquals(1999, memoization(statues)); //má byť 1999
                });
    }

    @Test
    void memoReturnTestThree() {
        capacity = 2300;
        IntStream.range(0, 100)
                .forEach(i -> {
                    List<Statue> statues = new ArrayList<>();
                    statues.add(new Statue(1, "socha 1", 1, 626L, 343L, 436L, 792L, 5));
                    statues.add(new Statue(2, "socha 2", 1, 524L, 343L, 436L, 792L, 5));
                    statues.add(new Statue(3, "socha 3", 1, 311L, 343L, 436L, 792L, 5));
                    statues.add(new Statue(4, "socha 4", 1, 421L, 343L, 436L, 792L, 5));
                    statues.add(new Statue(5, "socha 5", 1, 721L, 343L, 436L, 792L, 5));
                    statues.add(new Statue(6, "socha 6", 1, 452L, 343L, 436L, 792L, 5));
                    statues.add(new Statue(7, "socha 7", 1, 282L, 343L, 436L, 792L, 5));
                    statues.add(new Statue(8, "socha 8", 1, 602L, 343L, 436L, 792L, 5));
                    statues.add(new Statue(9, "socha 9", 1, 211L, 343L, 436L, 792L, 5));
                    statues.add(new Statue(10, "socha 10", 1, 466L, 343L, 436L, 792L, 5));
                    Assertions.assertEquals(2299, memoization(statues)); //má byť 2299
                });
    }

    @Test
    void multipleMemoReturnTest() {
        IntStream.range(0, 100)
                .forEach(i -> {
                    capacity = new Random().nextInt(1500, 10000);

                    List<Statue> statues = new ArrayList<>();
                    IntStream.range(0, 100)
                            .forEach(j -> {
                                statues.add(new Statue(1, "socha" + new Random().nextInt(1, 100), 1, new Random().nextLong(100, 350), 343L, 500L, 600L, 5));
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