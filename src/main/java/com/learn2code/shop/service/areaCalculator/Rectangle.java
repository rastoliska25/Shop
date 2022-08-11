package com.learn2code.shop.service.areaCalculator;

public class Rectangle {
    public boolean toRoot = false;
    public String name;
    public double x;
    public double y;

    public double a;
    public double b;
    public boolean used = false;
    public Rectangle right = null;
    public Rectangle down = null;
    public Rectangle fit = null;

    public Rectangle(String name, double a, double b) {
        this.name = name;
        this.a = a;
        this.b = b;
    }

    public Rectangle(double x, double y, double a, double b) {
        this.x = x;
        this.y = y;
        this.a = a;
        this.b = b;
        if(x == 0 && y == 0){
            this.toRoot = true;
        }
    }

}
