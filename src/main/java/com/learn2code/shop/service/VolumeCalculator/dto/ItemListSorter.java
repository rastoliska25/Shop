package com.learn2code.shop.service.VolumeCalculator.dto;

import com.learn2code.shop.service.VolumeCalculator.interfaces.Element;

import java.util.Comparator;
import java.util.List;

public class ItemListSorter {
    public static void sortByLargestFootprintAndLowestHeight(final List<Element> elements) {
        elements.sort(Comparator.comparingDouble((Element i) -> i.sirkaS() * i.vyskaS() * -1)
                .thenComparing(Element::dlzkaS));
    }
}
