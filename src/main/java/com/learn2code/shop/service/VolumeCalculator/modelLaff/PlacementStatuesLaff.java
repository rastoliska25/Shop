package com.learn2code.shop.service.VolumeCalculator.modelLaff;

import com.learn2code.shop.service.VolumeCalculator.dto.BasicRectangularBox;
import com.learn2code.shop.service.VolumeCalculator.interfaces.Element;
import com.learn2code.shop.service.VolumeCalculator.interfaces.Placement;
import com.learn2code.shop.service.VolumeCalculator.interfaces.RectangularBox;

import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

public class PlacementStatuesLaff extends BasicRectangularBox implements RectangularBox, Placement {

    private final RectangularBox parent;
    private Element element;

    private final Set<PlacementStatuesLaff> children = new HashSet<>();

    private final Set<PlacementStatuesLaff> remainders = new HashSet<>();

    private PlacementStatuesLaff(final RectangularBox parent, final RectangularBox positionAndDimension) {

        super(positionAndDimension);
        this.parent = parent;

        //No item added so far: Whole place remains
        this.remainders.add(this);
    }

    public PlacementStatuesLaff(final RectangularBox parent) {
        this(parent, parent);
    }

    @Override
    public Element element() {
        return this.element;
    }

    @Override
    public RectangularBox rodic() {
        return this.parent;
    }

    @Override
    public Set<Placement> dieta() {
        return Set.copyOf(this.children);
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


        this.remainders.clear();

        this.addRemainder(new BasicRectangularBox(
                element.sirka() + element.sirkaS() / 2 + (this.sirkaS() - element.sirkaS()) / 2,
                element.vyska(),
                this.dlzka(),
                this.sirkaS() - element.sirkaS(),
                element.vyskaS(),
                this.dlzkaS()
        ));

        this.addRemainder(new BasicRectangularBox(
                this.sirka(),
                element.vyska() + element.vyskaS() / 2 + (this.vyskaS() - element.vyskaS()) / 2,
                this.dlzka(),
                this.sirkaS(),
                this.vyskaS() - element.vyskaS(),
                this.dlzkaS()
        ));

        this.addRemainder(new BasicRectangularBox(
                element.sirka() + element.sirkaS() / 2 + (this.sirkaS() - element.sirkaS()) / 2,
                this.vyska(),
                this.dlzka(),
                this.sirkaS() - element.sirkaS(),
                this.vyskaS(),
                this.dlzkaS()
        ));

        this.addRemainder(new BasicRectangularBox(
                element.sirka(),
                element.vyska() + element.vyskaS() / 2 + (this.vyskaS() - element.vyskaS()) / 2,
                this.dlzka(),
                element.sirkaS(),
                this.vyskaS() - element.vyskaS(),
                this.dlzkaS()
        ));

        this.addRemainder(new BasicRectangularBox(
                element.sirka(),
                element.vyska(),
                this.dlzka() + element.dlzkaS() / 2,
                element.sirkaS(),
                element.vyskaS(),
                this.dlzkaS() - element.dlzkaS()
        ));

        this.addRemainder(new BasicRectangularBox(
                element.sirka() + element.sirkaS() / 2 + (this.sirkaS() - element.sirkaS()) / 2,
                element.vyska() + element.vyskaS() / 2 + (this.vyskaS() - element.vyskaS()) / 2,
                this.dlzka(),
                this.sirkaS() - element.sirkaS(),
                this.vyskaS() - element.vyskaS(),
                this.dlzkaS()
        ));


        if (this.parent != null && this.parent instanceof PlacementStatuesLaff) {

            var parentPlacement = (PlacementStatuesLaff) this.parent;

            //Add this to children of parent.
            parentPlacement.children.add(this);

            //Remove remainders from parent that intersect with remainders from this
            var iterator = parentPlacement.remainders.iterator();
            while (iterator.hasNext()) {
                var parentRemainder = iterator.next();
                for (var remainder : this.remainders) {
                    if (remainder.pretina(parentRemainder)) {
                        iterator.remove();
                        break;
                    }
                }
            }
        }
    }

    private void addRemainder(final RectangularBox rectangularBox) {

        if (rectangularBox.sirkaS() == 0 || rectangularBox.vyskaS() == 0 || rectangularBox.dlzkaS() == 0)
            return;

        for (var existing : this.remainders) {
            if (rectangularBox.pozicieSaRovnaju(existing) && rectangularBox.dimenzieSaRovnaju(existing))
                return;
        }

        var placement = new PlacementStatuesLaff(this, rectangularBox);
        this.remainders.add(placement);

        //System.out.println("Add remainder to " + this + "\n\t+" + box);
    }

    @Override
    public List<Placement> zvysne() {
        return List.copyOf(this.remainders);
    }

    @Override
    public PlacementStatuesLaff najdiZvysne(final RectangularBox rectangularBox) {

        Objects.requireNonNull(rectangularBox);

        PlacementStatuesLaff best = null;
        double bestScore = 0;

        for (var remainder : this.remainders) {
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

        for (var child : this.children) {
            var placement = child.najdiMiesto(element);
            if (placement != null)
                return placement;
        }

        return null;
    }
}
