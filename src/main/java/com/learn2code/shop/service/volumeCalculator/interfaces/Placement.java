package com.learn2code.shop.service.volumeCalculator.interfaces;

import java.util.List;
import java.util.Set;

public interface Placement extends RectangularBox {

    Set<Placement> dieta();

    Element element();

    Placement najdiMiesto(final Element element);

    void setElement(final Element element);

    List<Placement> zvysne();

    Placement najdiZvysne(final RectangularBox rectangularBox);

}
