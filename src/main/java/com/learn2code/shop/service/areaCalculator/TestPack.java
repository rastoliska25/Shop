package com.learn2code.shop.service.areaCalculator;

import java.util.ArrayList;
import java.util.Iterator;

public class TestPack {

    private final ArrayList<Rectangle> truckArea = new ArrayList<>();

    public TestPack(int numofpackets, double a, double b) {
        for (int i = 0; i < numofpackets; i++) {
            this.truckArea.add(new Rectangle(0, 0, a, b));
        }
    }

    public void joint(ArrayList<Rectangle> blocks) {
        Rectangle rectangle;
        Rectangle block;
        Iterator<Rectangle> blockItr = blocks.iterator();
        int i = 0;
        while (blockItr.hasNext()) {
            block = blockItr.next();
            if ((rectangle = this.findRectangle(this.truckArea.get(i), block.a, block.b)) != null) {
                block.fit = this.splitRectangle(rectangle, block.a, block.b);
                if (rectangle.toRoot) {
                    block.fit.toRoot = true;
                }
            } else {
                i++;
            }
        }
    }

    public Rectangle findRectangle(Rectangle root, double a, double b) {
        if (root.used) {
            Rectangle right = findRectangle(root.right, a, b);
            return (right != null ? right : findRectangle(root.down, a, b));
        } else if ((a <= root.a) && (b <= root.b)) {
            return root;
        } else {
            return null;
        }
    }

    public Rectangle splitRectangle(Rectangle rectangle, double a, double b) {
        rectangle.used = true;
        rectangle.down = new Rectangle(rectangle.x, rectangle.y + b, rectangle.a, rectangle.b - b);
        rectangle.right = new Rectangle(rectangle.x + a, rectangle.y, rectangle.a - a, b);
        return rectangle;
    }

}
