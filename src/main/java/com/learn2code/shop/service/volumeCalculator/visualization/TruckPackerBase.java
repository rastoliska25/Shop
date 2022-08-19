package com.learn2code.shop.service.volumeCalculator.visualization;

import com.learn2code.shop.Logging;
import com.learn2code.shop.service.volumeCalculator.PackItemResult;
import com.learn2code.shop.service.volumeCalculator.interfaces.Element;
import com.learn2code.shop.service.volumeCalculator.interfaces.PackingTool;

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

        //Logging.logger.info(packer);
        //Logging.logger.info(result);
    }

    private String randomFarba() {
        BigInteger randomInteger = BigDecimal.valueOf(Math.floor(Math.random() * 16777215)).toBigInteger();

        return randomInteger.toString(16);
    }
}