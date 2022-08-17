package com.learn2code.shop.service.VolumeCalculator.visualization;


import com.learn2code.shop.domain.Statue;
import com.learn2code.shop.domain.Truck;
import com.learn2code.shop.service.VolumeCalculator.dto.BasicElement;
import com.learn2code.shop.service.VolumeCalculator.dto.BasicTruck;
import com.learn2code.shop.service.VolumeCalculator.interfaces.Element;
import com.learn2code.shop.service.VolumeCalculator.modelLaff.TruckPackingLaff;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class TruckPacker extends TruckPackerBase {

    public void packuj(Truck truckWithHighestTransportWeight, List<Statue> statues) throws IOException {

        var truck = new BasicTruck(truckWithHighestTransportWeight.getTransportWidth(), truckWithHighestTransportWeight.getTransportHeight(), truckWithHighestTransportWeight.getTransportLength());
        //var truck = new BasicTruck(200, 200, 200);
        this.drawing.pridaj(truck);

        var packer = new TruckPackingLaff(truck);

        var randomItems = new ArrayList<Element>();
        /*
        for (int i = 0; i < 220; i++)
            randomItems.add(this.createRandomItem(1, 10));

         */

        statues.forEach(
                (statue) -> {
                    randomItems.add(new BasicElement(statue.getWidth().intValue(), statue.getHeight().intValue(), statue.getLength().intValue()));
                });

        /*
        randomItems.add(new BasicElement(47, 46, 48));
        randomItems.add(new BasicElement(44, 48, 47));
        randomItems.add(new BasicElement(46, 45, 47));
        */

        this.packingList(packer, randomItems);
        this.zapisHtmlSubor();
    }
}