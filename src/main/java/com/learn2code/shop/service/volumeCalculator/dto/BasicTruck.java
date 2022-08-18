package com.learn2code.shop.service.volumeCalculator.dto;

import com.learn2code.shop.service.volumeCalculator.interfaces.RectangularBox;
import com.learn2code.shop.service.volumeCalculator.interfaces.Truck;

public class BasicTruck extends BasicRectangularBox implements RectangularBox, Truck {

    private String meno = null;

    public BasicTruck(final int sirka, final int vyska, final int dlzka) {
        super(sirka, vyska, dlzka);
    }

}
