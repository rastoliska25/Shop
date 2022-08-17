package com.learn2code.shop.service.VolumeCalculator.interfaces;

import java.util.List;
import java.util.Set;

public interface Placement extends RectangularBox {

    RectangularBox rodic();

    Set<Placement> dieta();

    Element element();

    Placement najdiMiesto(final Element element);

    void setElement(final Element element);

    List<Placement> zvysne();

    Placement najdiZvysne(final RectangularBox rectangularBox);

}
