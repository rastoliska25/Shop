package com.learn2code.shop.service.VolumeCalculator.visualization;

import com.learn2code.shop.service.VolumeCalculator.PackItemResult;
import com.learn2code.shop.service.VolumeCalculator.interfaces.Element;
import com.learn2code.shop.service.VolumeCalculator.interfaces.PackingTool;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;

public abstract class TruckPackerBase extends DrawingRectangleBase {

    public void packingList(final PackingTool packer, final List<Element> elements) {

        var result = packer.zabalit(elements);

        var success = result.vezmi(PackItemResult.Uspesne);
        for (var i : success) {
            this.drawing.pridaj(i);
            this.drawing.setStyl(i, "color: 0x" + randomFarba() + ", wireframe: false");
        }

        System.out.println(packer);
        System.out.println(result);
    }

    private String randomFarba() {
        BigInteger randomInteger = BigDecimal.valueOf(Math.floor(Math.random() * 16777215)).toBigInteger();

        return randomInteger.toString(16);
    }

}