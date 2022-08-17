package com.learn2code.shop.service.VolumeCalculator.dto;

import com.learn2code.shop.service.VolumeCalculator.PackItemResult;
import com.learn2code.shop.service.VolumeCalculator.interfaces.Element;
import com.learn2code.shop.service.VolumeCalculator.interfaces.ResultOfPacking;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BasicResultOfPacking implements ResultOfPacking {

    private final Map<PackItemResult, List<Element>> items = new HashMap<>();

    private int celkovyObjem = 0;

    public void add(final PackItemResult packItemResult, final Element element) {
        var list = this.items.computeIfAbsent(
                packItemResult, (k) -> new ArrayList<>()
        );
        list.add(element);

        if (packItemResult == PackItemResult.Uspesne) {
            this.celkovyObjem += element.sirkaS() * element.vyskaS() * element.dlzkaS();
        }
    }

    public List<Element> vezmi(final PackItemResult packItemResult) {
        var list = items.get(packItemResult);
        if (list == null)
            return List.of();
        return List.copyOf(list);
    }

    @Override
    public int celkovyObjem() {
        return this.celkovyObjem;
    }

    @Override
    public String toString() {

        StringBuilder s = new StringBuilder();

        for (var e : this.items.entrySet()) {
            if (s.length() > 0)
                s.append(", ");

            s.append(e.getKey())
                    .append(": ")
                    .append(e.getValue().size());
        }

        return "BasicPackListResult{" +
                s +
                ", celkovyObjem=" + celkovyObjem +
                '}';
    }
}
