package com.learn2code.Shop.domain;

import javax.persistence.*;
import java.util.List;

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
    private Byte loadSecuring;

    @OneToMany(targetEntity = Statue.class, cascade = CascadeType.ALL)
    @JoinColumn(name = "type_id", referencedColumnName = "id")
    private List<Statue> statuesList;

    public StatueType() {
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getLabel() {
        return label;
    }

    public Byte getLoadSecuring() {
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
