package com.learn2code.Shop.domain;

public class StoneStatue extends Statue{

    private String density;

    public StoneStatue() {}

    public String getDensity() {
        return density;
    }

    @Override
    public String toString() {
        return "StoneStatue{" +
                "density='" + density + '\'' +
                '}';
    }
}
