package com.learn2code.shop.service.volumeCalculator.interfaces;

import java.util.List;

public interface TruckStatus {

    Truck truck();

    List<Element> elementy();

    Placement zakladneUlozenie();

    default double levelDoplnenia() {
        return (double) this.objemElementov() / this.truck().objem();
    }

    default int zostavajuciObjem() {

        int volume = truck().objem();
        for (var item : elementy()) {
            volume -= item.objem();
        }

        return volume;
    }

    default int objemElementov() {
        int volume = 0;
        for (var item : elementy()) {
            volume += item.objem();
        }

        return volume;
    }
}
