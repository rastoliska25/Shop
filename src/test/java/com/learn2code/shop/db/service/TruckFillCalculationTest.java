package com.learn2code.shop.db.service;

import com.learn2code.shop.domain.Statue;
import com.learn2code.shop.service.TruckFillCalculation;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.stereotype.Component;
import org.springframework.test.annotation.DirtiesContext;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.IntStream;

@Component
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class TruckFillCalculationTest {

    List<Statue> statuesEmpty = new ArrayList<>();

    int capacity;


    @PostConstruct
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

                    TruckFillCalculation truckFillCalculation = new TruckFillCalculation(statues);
                    Assertions.assertEquals(1799, truckFillCalculation.memoization(capacity)); //má byť 1499
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

                    TruckFillCalculation truckFillCalculation = new TruckFillCalculation(statues);
                    Assertions.assertEquals(1999, truckFillCalculation.memoization(capacity)); //má byť 1999
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

                    TruckFillCalculation truckFillCalculation = new TruckFillCalculation(statues);
                    Assertions.assertEquals(2299, truckFillCalculation.memoization(capacity)); //má byť 2299
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

                    TruckFillCalculation truckFillCalculation = new TruckFillCalculation(statues);
                    Assertions.assertTrue(capacity >= truckFillCalculation.memoization(capacity));
                });
    }

}