package com.learn2code.shop.service.volumeCalculator.dto;

import com.learn2code.shop.service.volumeCalculator.interfaces.RectangularBox;

public class BasicRectangularBox implements RectangularBox {

    private int sirka = 0;
    private int vyska = 0;
    private int dlzka = 0;

    private int sirkaS = 0;
    private int vyskaS = 0;
    private int dlzkaS = 0;


    public BasicRectangularBox(final RectangularBox rectangularBox) {
        this.ulozNaMiesto(rectangularBox.sirka(), rectangularBox.vyska(), rectangularBox.dlzka());
        this.zmenitVelkost(rectangularBox.sirkaS(), rectangularBox.vyskaS(), rectangularBox.dlzkaS());
    }

    public BasicRectangularBox(final int sirka, final int vyska, final int dlzka, final int sirkaS, final int vyskaS, final int dlzkaS) {
        this.ulozNaMiesto(sirka, vyska, dlzka);
        this.zmenitVelkost(sirkaS, vyskaS, dlzkaS);
    }

    public BasicRectangularBox(final int sirkaS, final int vyskaS, final int dlzkaS) {
        this.zmenitVelkost(sirkaS, vyskaS, dlzkaS);
    }

    @Override
    public int sirkaS() {
        return this.sirkaS;
    }

    @Override
    public int vyskaS() {
        return this.vyskaS;
    }

    @Override
    public int dlzkaS() {
        return this.dlzkaS;
    }

    @Override
    public void zmenitVelkost(final int sirkaS, final int vyskaS, final int zs) {
        this.sirkaS = sirkaS;
        this.vyskaS = vyskaS;
        this.dlzkaS = zs;
    }


    @Override
    public int sirka() {
        return this.sirka;
    }

    @Override
    public int vyska() {
        return this.vyska;
    }

    @Override
    public int dlzka() {
        return this.dlzka;
    }

    @Override
    public void ulozNaMiesto(final int x, final int y, final int z) {
        this.sirka = x;
        this.vyska = y;
        this.dlzka = z;
    }

    @Override
    public void posunutie(final int x, final int y, final int z) {
        this.sirka += x;
        this.vyska += y;
        this.dlzka += z;
    }

    @Override
    public String toString() {
        return "Box{"
                + sirka + "/" + vyska + "/" + dlzka
                + " " + sirkaS + "/" + vyskaS + "/" + dlzkaS
                + " " + sirkaMin() + ".." + sirkaMax() + "/" + vyskaMin() + ".." + vyskaMax() + "/" + dlzkaMin() + ".." + dlzkaMax()
                + "}";
    }
}
