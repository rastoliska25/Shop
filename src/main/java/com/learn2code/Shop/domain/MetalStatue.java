package com.learn2code.Shop.domain;

public class MetalStatue extends Statue{
    private String yieldStrength;

    public MetalStatue() {}

    public String getYieldStrength() {
        return yieldStrength;
    }

    @Override
    public String toString() {
        return "MetalStatue{" +
                "YieldStrength='" + yieldStrength + '\'' +
                '}';
    }
}
