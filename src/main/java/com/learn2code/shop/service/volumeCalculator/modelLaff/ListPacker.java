package com.learn2code.shop.service.volumeCalculator.modelLaff;

import com.learn2code.shop.service.volumeCalculator.*;
import com.learn2code.shop.service.volumeCalculator.dto.BasicElement;
import com.learn2code.shop.service.volumeCalculator.dto.BasicTruck;
import com.learn2code.shop.service.volumeCalculator.interfaces.Element;
import com.learn2code.shop.service.volumeCalculator.interfaces.PackingTool;
import com.learn2code.shop.service.volumeCalculator.interfaces.ResultOfPacking;
import com.learn2code.shop.service.volumeCalculator.interfaces.Truck;

import java.util.*;

public class ListPacker {

    private final Class<? extends BasicPackingTool> packerClass;
    private final Set<Truck> availableTrucks;

    private final Map<PackingTool, Map<PackingTool, List<Element>>> discarded = new HashMap<>();

    public ListPacker(Class<? extends BasicPackingTool> packerClass, Set<Truck> availableTrucks) {
        this.packerClass = packerClass;
        this.availableTrucks = availableTrucks;
    }

    public Map<PackingTool, Map<PackingTool, List<Element>>> discarded() {
        return discarded;
    }

    public Map<PackingTool, List<Element>> pack(final List<Element> elements) throws ReflectiveOperationException {

        var result = new LinkedHashMap<PackingTool, List<Element>>();

        var remainingItems = elements;
        while (remainingItems.size() > 0) {

            var chunk = this.packChunk(remainingItems);
            if (chunk == null)
                return null;

            var packer = chunk.getKey();
            var packListResult = chunk.getValue();
            var packedItems = packListResult.vezmi(PackItemResult.Uspesne);
            if (packedItems.size() == 0)
                return null;

            result.put(packer, packedItems);

            remainingItems = packListResult.vezmiNeprejdene();
        }

        return result;
    }

    protected Map.Entry<PackingTool, ResultOfPacking> packChunk(final List<Element> elements) throws ReflectiveOperationException {

        var packedLists = this.packToAllContainers(elements);
        return this.selectBest(packedLists);
    }

    protected Map.Entry<PackingTool, ResultOfPacking> selectBest(final Map<PackingTool, ResultOfPacking> packed) {

        if (packed.isEmpty())
            return null;

        Map.Entry<PackingTool, ResultOfPacking> best = null;
        int bestItemCount = 0;
        double bestFillingLevel = 0;

        var discarded = new LinkedHashMap<PackingTool, List<Element>>();

        for (var e : packed.entrySet()) {

            var packer = e.getKey();
            var items = e.getValue();
            var state = packer.status();
            var itemCount = state.elementy().size();
            var itemFillingLevel = state.levelDoplnenia();

            if (best == null || bestItemCount < itemCount
                    || (bestItemCount == itemCount && itemFillingLevel > bestFillingLevel)
            ) {
                best = e;
                bestItemCount = itemCount;
                bestFillingLevel = itemFillingLevel;
            }

            discarded.put(packer, items.vezmi(PackItemResult.Uspesne));
        }

        discarded.remove(best.getKey());
        this.discarded.put(best.getKey(), discarded);

        return best;
    }

    protected Map<PackingTool, ResultOfPacking> packToAllContainers(final List<Element> elements) throws ReflectiveOperationException {

        var result = new HashMap<PackingTool, ResultOfPacking>();

        for (var containerTemplate : availableTrucks) {
            var container = new BasicTruck(containerTemplate);
            var packer = this.createPacker(container);
            var packResult = packer.zabalit(this.copyItems(elements));
            result.put(packer, packResult);
        }

        return result;
    }

    protected List<Element> copyItems(final List<Element> elements) {
        var result = new ArrayList<Element>(elements.size());
        for (var item : elements) {
            result.add(new BasicElement(item));
        }
        return result;
    }

    protected PackingTool createPacker(final Truck truck) throws ReflectiveOperationException {
        return this.packerClass
                .getConstructor(Truck.class)
                .newInstance(truck);
    }

}
