package com.learn2code.shop.service.VolumeCalculator.interfaces;


import com.learn2code.shop.service.VolumeCalculator.PackItemResult;

import java.util.List;

public interface PackingTool {
    Truck truck();

    TruckStatus status();

    PackItemResult pridat(final Element element);

    ResultOfPacking zabalit(final List<Element> elements);
}
