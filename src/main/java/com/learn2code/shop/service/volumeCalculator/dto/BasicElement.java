package com.learn2code.shop.service.volumeCalculator.dto;

import com.learn2code.shop.service.volumeCalculator.interfaces.Element;

public class BasicElement extends BasicRectangularBox implements Element {

    private String meno = null;

    public BasicElement(final int sirkaS, final int vyskaS, final int dlzkaS) {
        super(sirkaS, vyskaS, dlzkaS);
    }

}
