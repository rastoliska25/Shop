package com.learn2code.shop.domain;

import javax.persistence.*;

@Table(name = "statues")
@Entity
public class Statue {

    @Id
    @GeneratedValue
    @Column(name = "id")
    private Integer id;

    @Column(name = "name")
    private String name;

    @Column(name = "type_id")
    private Integer typeId;

    @Column(name = "weight")
    private Long weight;

    @Column(name = "length")
    private Long length;

    @Column(name = "width")
    private Long width;

    @Column(name = "height")
    private Long height;

    @Column(name = "truck_id")
    private Integer truckId;

    public Statue() {
    }

    public Statue(Integer id, String name, Integer typeId, Long weight, Long length, Long width, Long height, Integer truckId) {
        this.id = id;
        this.name = name;
        this.typeId = typeId;
        this.weight = weight;
        this.length = length;
        this.width = width;
        this.height = height;
        this.truckId = truckId;
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Integer getTypeId() {
        return typeId;
    }

    public Long getWeight() {
        return weight;
    }

    public Long getLength() {
        return length;
    }

    public Long getWidth() {
        return width;
    }

    public Long getHeight() {
        return height;
    }

    public Integer getTruckId() {
        return truckId;
    }

    public void setTruckId(Integer truckId) {
        this.truckId = truckId;
    }

    @Override
    public String toString() {
        return "Statue{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", typeId=" + typeId +
                ", weight=" + weight +
                ", length=" + length +
                ", width=" + width +
                ", height=" + height +
                '}';
    }
}
