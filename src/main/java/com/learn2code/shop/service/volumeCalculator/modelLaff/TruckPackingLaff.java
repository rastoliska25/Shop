package com.learn2code.shop.service.volumeCalculator.modelLaff;

import com.learn2code.shop.service.volumeCalculator.*;
import com.learn2code.shop.service.volumeCalculator.dto.BasicResultOfPacking;
import com.learn2code.shop.service.volumeCalculator.dto.BasicTruckStatus;
import com.learn2code.shop.service.volumeCalculator.dto.ItemListSorter;
import com.learn2code.shop.service.volumeCalculator.interfaces.*;

import java.util.ArrayList;
import java.util.List;


public class TruckPackingLaff implements PackingTool {

    private final BasicTruckStatus truckStatus;

    public TruckPackingLaff(final Truck truck) {
        super();
        this.truckStatus = new BasicTruckStatus(
                truck,
                new PlacementStatuesLaff(truck)
        );
    }

    @Override
    public Truck truck() {
        return null;
    }

    @Override
    public TruckStatus status() {
        return this.truckStatus;
    }

    @Override
    public ResultOfPacking zabalit(List<Element> elements) {

        var sortedItems = new ArrayList<>(elements);
        ItemListSorter.sortByLargestFootprintAndLowestHeight(sortedItems);

        var result = new BasicResultOfPacking();

        for (var item : sortedItems) {
            var itemResult = this.pridat(item);
            result.add(itemResult, item);
        }

        return result;
    }

    @Override
    public PackItemResult pridat(final Element element) {

        if (!element.vojdeDnu(this.truckStatus.truck()))
            return PackItemResult.NevojdeSa;

        var placement = this.najdiMiesto(
                this.truckStatus.zakladneUlozenie(),
                element
        );
        if (placement == null)
            return PackItemResult.NevojdeSaMaloMiesta;

        placement.setElement(element);
        this.truckStatus.pridajElement(element);

        return PackItemResult.Uspesne;
    }

    protected Placement najdiMiesto(final Placement placement, final Element element) {

        if (placement.element() == null)
            return placement;

        var remainder = placement.najdiZvysne(element);
        if (remainder != null)
            return remainder;

        for (var child : placement.dieta()) {
            var childMatch = this.najdiMiesto(child, element);
            if (childMatch != null)
                return childMatch;
        }

        return null;
    }
}
