package com.learn2code.Shop.domain;

import javax.persistence.*;

@Table(name = "trucks")
@Entity
public class Truck {

    @Id
    @GeneratedValue
    @Column(name = "id")
    private Integer id;

    @Column(name = "name")
    private String name;

    @Column(name = "transport_length")
    private Integer transportLength;

    @Column(name = "transport_width")
    private Integer transportWidth;

    @Column(name = "transport_height")
    private Integer transportHeight;

    @Column(name = "average_consumption")
    private Float averageConsumption;

    @Column(name = "load_securing")
    private boolean loadSecuring;

    public Truck() {
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Integer getTransportLength() {
        return transportLength;
    }

    public Integer getTransportWidth() {
        return transportWidth;
    }

    public Integer getTransportHeight() {
        return transportHeight;
    }

    public Float getAverageConsumption() {
        return averageConsumption;
    }

    public boolean isLoadSecuring() {
        return loadSecuring;
    }

    @Override
    public String toString() {
        return "Truck{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", transportLength=" + transportLength +
                ", transportWidth=" + transportWidth +
                ", transportHeight=" + transportHeight +
                ", averageConsumption=" + averageConsumption +
                ", loadSecuring=" + loadSecuring +
                '}';
    }

}