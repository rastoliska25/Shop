package com.learn2code.Shop.domain;

import javax.persistence.*;

@Table(name = "statue_type")
@Entity
public class StatueType {
    @Id
    @GeneratedValue
    @Column(name = "id")
    private Integer id;

    @Column(name = "name")
    private String name;

    @Column(name = "label")
    private String label;

    @Column(name = "load_securing")
    private boolean loadSecuring;

    public StatueType() {}

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getLabel() {
        return label;
    }

    public boolean isLoadSecuring() {
        return loadSecuring;
    }

    @Override
    public String toString() {
        return "StatueType{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", label='" + label + '\'' +
                ", loadSecuring=" + loadSecuring +
                '}';
    }
}
