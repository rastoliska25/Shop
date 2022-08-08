package com.learn2code.Shop.db.service;

import com.learn2code.Shop.db.repository.StatueRepository;
import com.learn2code.Shop.db.repository.TruckRepository;
import com.learn2code.Shop.domain.Statue;
import com.learn2code.Shop.domain.Truck;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class TruckFillCalculation {

    private final TruckRepository truckRepository;
    private final StatueRepository statueRepository;
    List<Statue> statues = new ArrayList<>();

    public TruckFillCalculation(TruckRepository truckRepository, StatueRepository statueRepository, List<Statue> statues) {
        this.truckRepository = truckRepository;
        this.statueRepository = statueRepository;
        this.statues = statues;
    }

    List<Truck> trucks = new ArrayList<>();

    public void calculate() {

        //uloží všetky statues do db
        for (Statue statue : statues) {
            if (statue.getWeight() < 500) {
                statue.setTruckId(5);
            } else {
                statue.setTruckId(500);
            }
            statueRepository.save(statue);
        }

        trucks = truckRepository.findAll();

        for (Truck truck : trucks) {
            System.out.println(truck);
        }

        Collections.sort(trucks, Comparator.comparing(Truck::getTransportWeight));  //potriedenie na základe transportWeight

        int truckTransportWeight;

        for (Truck truck : trucks) {
            truckTransportWeight = truck.getTransportWeight();
            System.out.println(truck);
        }



    }
}
