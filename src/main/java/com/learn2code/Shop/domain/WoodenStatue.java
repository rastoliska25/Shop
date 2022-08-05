package com.learn2code.Shop.domain;

public class WoodenStatue extends Statue{
    private String woodType;

    public WoodenStatue() {}

    public String getWoodType() {
        return woodType;
    }

    @Override
    public String toString() {
        return "WoodenStatue{" +
                "woodType='" + woodType + '\'' +
                '}';
    }
}
