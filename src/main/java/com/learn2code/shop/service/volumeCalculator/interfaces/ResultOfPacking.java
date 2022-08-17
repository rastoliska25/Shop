package com.learn2code.shop.service.volumeCalculator.interfaces;

import com.learn2code.shop.service.volumeCalculator.PackItemResult;

import java.util.ArrayList;
import java.util.List;

public interface ResultOfPacking {

    List<Element> vezmi(final PackItemResult packItemResult);

    default List<Element> vezmiNeprejdene() {
        var result = new ArrayList<Element>();
        for (var packItemResult : PackItemResult.values()) {
            if (packItemResult != PackItemResult.Uspesne)
                result.addAll(this.vezmi(packItemResult));
        }
        return result;
    }

    int celkovyObjem();
}
