package com.learn2code.shop.service.VolumeCalculator.interfaces;

public interface RectangularBox {

    int sirka();

    int vyska();

    int dlzka();

    int sirkaS();

    int vyskaS();

    int dlzkaS();

    default int objem() {
        return this.sirkaS() * this.vyskaS() * this.dlzkaS();
    }

    default int sirkaMin() {
        return this.sirka() - this.sirkaS() / 2;
    }

    default int vyskaMin() {
        return this.vyska() - this.vyskaS() / 2;
    }

    default int dlzkaMin() {
        return this.dlzka() - this.dlzkaS() / 2;
    }

    default int sirkaMax() {
        return this.sirka() + this.sirkaS() / 2;
    }

    default int vyskaMax() {
        return this.vyska() + this.vyskaS() / 2;
    }

    default int dlzkaMax() {
        return this.dlzka() + this.dlzkaS() / 2;
    }

    void zmenitVelkost(final int sirkaS, final int vyskaS, final int dlzkaS);

    void ulozNaMiesto(final int sirka, final int vyska, final int dlzka);

    void posunutie(final int sirka, final int vyska, final int dlzka);

    default boolean vojdeDnu(final RectangularBox rectangularBox) {
        return this.sirkaS() <= rectangularBox.sirkaS() && this.vyskaS() <= rectangularBox.vyskaS() && this.dlzkaS() <= rectangularBox.dlzkaS();
    }

    default boolean pozicieSaRovnaju(final RectangularBox box) {
        if (this == box)
            return true;
        if (box == null)
            return false;
        return this.sirka() == box.sirka() && this.vyska() == box.vyska() && this.dlzka() == box.dlzka();
    }

    default boolean dimenzieSaRovnaju(final RectangularBox rectangularBox) {
        if (this == rectangularBox)
            return true;
        if (rectangularBox == null)
            return false;
        return this.sirkaS() == rectangularBox.sirkaS() && this.vyskaS() == rectangularBox.vyskaS() && this.dlzkaS() == rectangularBox.dlzkaS();
    }

    default boolean pretina(final RectangularBox rectangularBox) {
        if (this.sirkaMin() < rectangularBox.sirkaMax() && this.sirkaMax() > rectangularBox.sirkaMin())
            if (this.vyskaMin() < rectangularBox.vyskaMax() && this.vyskaMax() > rectangularBox.vyskaMin())
                return this.dlzkaMin() < rectangularBox.dlzkaMax() && this.dlzkaMax() > rectangularBox.dlzkaMin();

        return false;
    }
}
