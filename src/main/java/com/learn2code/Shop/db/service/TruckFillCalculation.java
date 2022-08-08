package com.learn2code.Shop.db.service;

import com.learn2code.Shop.db.repository.StatueRepository;
import com.learn2code.Shop.db.repository.TruckRepository;
import com.learn2code.Shop.domain.Statue;
import com.learn2code.Shop.domain.Truck;
import org.springframework.data.jpa.repository.Query;

import java.util.ArrayList;
import java.util.List;

public class TruckFillCalculation {

    private final StatueRepository statueRepository;
    List<Statue> statues = new ArrayList<>();

    public TruckFillCalculation(StatueRepository statueRepository, List<Statue> statues) {
        this.statueRepository = statueRepository;
        this.statues = statues;
    }

    List<Truck> trucks = new ArrayList<>();

    public void calculate() {

        int truckTransportWeight;

        for (Truck truck : trucks) {
            truckTransportWeight = truck.getTransportWeight();
        }

        //uloží všetky statues do db
        for (Statue statue : statues) {
            if (statue.getWeight() < 500) {
                statue.setTruckId(5);
            } else {
                statue.setTruckId(500);
            }
            statueRepository.save(statue);
        }
    }
}
