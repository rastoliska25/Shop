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
        /*
        for (Statue statue : statues) {
            if (statue.getWeight() < 500) {
                statue.setTruckId(5);
            } else {
                statue.setTruckId(500);
            }
            statueRepository.save(statue);
        }*/

        //int sum = statues.stream().filter(o -> o.getWeight() > 10).mapToInt(o -> Math.toIntExact(o.getWeight())).sum(); //s filtrom na hmotnost
        int statuesWeightSum = statues.stream().mapToInt(o -> Math.toIntExact(o.getWeight())).sum(); //s filtrom na hmotnost

        System.out.println("\n" + "hmotnost soch dokopy: "+ statuesWeightSum + "\n");

        trucks = truckRepository.findAll();

        Collections.sort(trucks, Comparator.comparing(Truck::getTransportWeight));  //potriedenie trucks na základe transportWeight

        int truckTransportWeight;
        Truck truckWithHighestTransportWeight =  trucks.get(trucks.size() - 1);
        for (Truck truck : trucks) {
            truckTransportWeight = truck.getTransportWeight();
            //if statuesWeightSum
            System.out.println(truck);
        }

        System.out.println("Truck s najvacsou prepravnou hmotnostou: " + truckWithHighestTransportWeight);



    }
}
