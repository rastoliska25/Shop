package com.learn2code.shop.service.VolumeCalculator.dto;

import com.learn2code.shop.service.VolumeCalculator.interfaces.RectangularBox;
import com.learn2code.shop.service.VolumeCalculator.interfaces.Truck;

public class BasicTruck extends BasicRectangularBox implements RectangularBox, Truck {

    private String meno = null;

    public BasicTruck(final int sirka, final int vyska, final int dlzka) {
        super(sirka, vyska, dlzka);
    }

    public BasicTruck(final Truck truck) {
        super(truck);
        this.meno = truck.meno();
    }

    @Override
    public String meno() {
        return this.meno;
    }
    
}
