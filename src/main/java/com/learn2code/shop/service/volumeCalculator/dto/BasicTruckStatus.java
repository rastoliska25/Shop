package com.learn2code.shop.service.volumeCalculator.dto;

import com.learn2code.shop.service.volumeCalculator.interfaces.Element;
import com.learn2code.shop.service.volumeCalculator.interfaces.Placement;
import com.learn2code.shop.service.volumeCalculator.interfaces.Truck;
import com.learn2code.shop.service.volumeCalculator.interfaces.TruckStatus;

import java.util.ArrayList;
import java.util.List;

public class BasicTruckStatus implements TruckStatus {

    private final Truck truck;
    private final Placement zakladneUlozenie;

    private final List<Element> elements = new ArrayList<>();

    public BasicTruckStatus(final Truck truck, final Placement zakladneUlozenie) {
        this.truck = truck;
        this.zakladneUlozenie = zakladneUlozenie;
    }

    public void pridajElement(final Element element) {
        this.elements.add(element);
    }

    @Override
    public List<Element> elementy() {
        return List.copyOf(this.elements);
    }

    @Override
    public Truck truck() {
        return this.truck;
    }

    @Override
    public Placement zakladneUlozenie() {
        return this.zakladneUlozenie;
    }

    @Override
    public String toString() {
        return "BasicContainerState{" +
                "container=" + truck +
                ", items=" + elements.size() +
                ", remainVolume=" + zostavajuciObjem() +
                ", fillingLevel=" + levelDoplnenia() +
                '}';
    }
}
