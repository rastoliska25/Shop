package com.learn2code.shop.service.VolumeCalculator.modelLaff;

import com.learn2code.shop.service.VolumeCalculator.*;
import com.learn2code.shop.service.VolumeCalculator.dto.BasicResultOfPacking;
import com.learn2code.shop.service.VolumeCalculator.dto.BasicTruckStatus;
import com.learn2code.shop.service.VolumeCalculator.dto.ItemListSorter;
import com.learn2code.shop.service.VolumeCalculator.interfaces.*;

import java.util.ArrayList;
import java.util.List;


public class TruckPackingLaff extends BasicPackingTool {

    private final BasicTruckStatus containerState;

    public TruckPackingLaff(final Truck truck) {
        super();
        this.containerState = new BasicTruckStatus(
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
        return this.containerState;
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

        if (!element.vojdeDnu(this.containerState.truck()))
            return PackItemResult.NevojdeSa;

        var placement = this.findPlacement(
                this.containerState.zakladneUlozenie(),
                element
        );
        if (placement == null)
            return PackItemResult.NevojdeSaMaloMiesta;

        placement.setElement(element);
        this.containerState.pridajElement(element);

        return PackItemResult.Uspesne;
    }

    protected Placement findPlacement(final Placement placement, final Element element) {

        if (placement.element() == null)
            return placement;

        var remainder = placement.najdiZvysne(element);
        if (remainder != null)
            return remainder;

        for (var child : placement.dieta()) {
            var childMatch = this.findPlacement(child, element);
            if (childMatch != null)
                return childMatch;
        }

        return null;
    }
}
