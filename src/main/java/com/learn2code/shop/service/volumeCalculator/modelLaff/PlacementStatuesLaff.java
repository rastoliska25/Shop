package com.learn2code.shop.service.volumeCalculator.modelLaff;

import com.learn2code.shop.service.volumeCalculator.dto.BasicRectangularBox;
import com.learn2code.shop.service.volumeCalculator.interfaces.Element;
import com.learn2code.shop.service.volumeCalculator.interfaces.Placement;
import com.learn2code.shop.service.volumeCalculator.interfaces.RectangularBox;

import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

public class PlacementStatuesLaff extends BasicRectangularBox implements RectangularBox, Placement {

    private final RectangularBox rodic;
    private Element element;

    private final Set<PlacementStatuesLaff> dieta = new HashSet<>();

    private final Set<PlacementStatuesLaff> zvysne = new HashSet<>();

    private PlacementStatuesLaff(final RectangularBox rodic, final RectangularBox positionAndDimension) {

        super(positionAndDimension);
        this.rodic = rodic;

        //No item added so far: Whole place remains
        this.zvysne.add(this);
    }

    public PlacementStatuesLaff(final RectangularBox rodic) {
        this(rodic, rodic);
    }

    @Override
    public Element element() {
        return this.element;
    }

    @Override
    public RectangularBox rodic() {
        return this.rodic;
    }

    @Override
    public Set<Placement> dieta() {
        return Set.copyOf(this.dieta);
    }

    public void setElement(final Element element) {

        Objects.requireNonNull(element);
        if (!element.vojdeDnu(this))
            throw new IllegalArgumentException("Item does not fit");

        this.element = element;
        this.element.ulozNaMiesto(
                this.sirka() - this.sirkaS() / 2 + element.sirkaS() / 2,
                this.vyska() - this.vyskaS() / 2 + element.vyskaS() / 2,
                this.dlzka() - this.dlzkaS() / 2 + element.dlzkaS() / 2
        );


        this.zvysne.clear();

        this.pridajZvysne(new BasicRectangularBox(
                element.sirka() + element.sirkaS() / 2 + (this.sirkaS() - element.sirkaS()) / 2,
                element.vyska(),
                this.dlzka(),
                this.sirkaS() - element.sirkaS(),
                element.vyskaS(),
                this.dlzkaS()
        ));

        this.pridajZvysne(new BasicRectangularBox(
                this.sirka(),
                element.vyska() + element.vyskaS() / 2 + (this.vyskaS() - element.vyskaS()) / 2,
                this.dlzka(),
                this.sirkaS(),
                this.vyskaS() - element.vyskaS(),
                this.dlzkaS()
        ));

        this.pridajZvysne(new BasicRectangularBox(
                element.sirka() + element.sirkaS() / 2 + (this.sirkaS() - element.sirkaS()) / 2,
                this.vyska(),
                this.dlzka(),
                this.sirkaS() - element.sirkaS(),
                this.vyskaS(),
                this.dlzkaS()
        ));

        this.pridajZvysne(new BasicRectangularBox(
                element.sirka(),
                element.vyska() + element.vyskaS() / 2 + (this.vyskaS() - element.vyskaS()) / 2,
                this.dlzka(),
                element.sirkaS(),
                this.vyskaS() - element.vyskaS(),
                this.dlzkaS()
        ));

        this.pridajZvysne(new BasicRectangularBox(
                element.sirka(),
                element.vyska(),
                this.dlzka() + element.dlzkaS() / 2,
                element.sirkaS(),
                element.vyskaS(),
                this.dlzkaS() - element.dlzkaS()
        ));

        this.pridajZvysne(new BasicRectangularBox(
                element.sirka() + element.sirkaS() / 2 + (this.sirkaS() - element.sirkaS()) / 2,
                element.vyska() + element.vyskaS() / 2 + (this.vyskaS() - element.vyskaS()) / 2,
                this.dlzka(),
                this.sirkaS() - element.sirkaS(),
                this.vyskaS() - element.vyskaS(),
                this.dlzkaS()
        ));


        if (this.rodic != null && this.rodic instanceof PlacementStatuesLaff parentPlacement) {

            parentPlacement.dieta.add(this);

            var iterator = parentPlacement.zvysne.iterator();
            while (iterator.hasNext()) {
                var parentRemainder = iterator.next();
                for (var remainder : this.zvysne) {
                    if (remainder.pretina(parentRemainder)) {
                        iterator.remove();
                        break;
                    }
                }
            }
        }
    }

    private void pridajZvysne(final RectangularBox rectangularBox) {

        if (rectangularBox.sirkaS() == 0 || rectangularBox.vyskaS() == 0 || rectangularBox.dlzkaS() == 0)
            return;

        for (var existing : this.zvysne) {
            if (rectangularBox.pozicieSaRovnaju(existing) && rectangularBox.dimenzieSaRovnaju(existing))
                return;
        }

        var placement = new PlacementStatuesLaff(this, rectangularBox);
        this.zvysne.add(placement);

    }

    @Override
    public List<Placement> zvysne() {
        return List.copyOf(this.zvysne);
    }

    @Override
    public PlacementStatuesLaff najdiZvysne(final RectangularBox rectangularBox) {

        Objects.requireNonNull(rectangularBox);

        PlacementStatuesLaff best = null;
        double bestScore = 0;

        for (var remainder : this.zvysne) {
            if (rectangularBox.vojdeDnu(remainder)) {

                // Consider better if box uses more space on x- or y-axis

                var currentScore = Math.max(
                        (double) rectangularBox.sirkaS() / remainder.sirkaS(),
                        (double) rectangularBox.vyskaS() / remainder.vyskaS()
                );

                if (best == null || bestScore < currentScore) {
                    best = remainder;
                    bestScore = currentScore;
                }
            }
        }

        return best;
    }

    @Override
    public Placement najdiMiesto(final Element element) {

        if (this.element == element)
            return this;

        for (var child : this.dieta) {
            var placement = child.najdiMiesto(element);
            if (placement != null)
                return placement;
        }

        return null;
    }
}
