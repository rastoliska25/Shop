package com.learn2code.shop.service.areaCalculator;

public class Rectangle {
    public int x;

    public int y;

    public int width;

    public int height;

    Rectangle(int x, int y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    public Rectangle() {
    }

    @Override
    public String toString() {
        return "[ " + x + ", " + y + ", " + width + ", " + height + " ]";
    }
}
