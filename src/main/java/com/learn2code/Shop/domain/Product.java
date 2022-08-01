package com.learn2code.Shop.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.sql.Timestamp;

@Entity
public class Product {

    @Id
    @GeneratedValue
    private Integer id;

    private int merchantId;

    private String name;

    private String description;

    private double price;

    private Timestamp createdAt;

    private int available;

    public Product() {
    }

    public Integer getId() {
        return id;
    }

    public int getMerchantId() {
        return merchantId;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public double getPrice() {
        return price;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public int getAvailable() {
        return available;
    }

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", merchantId=" + merchantId +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", price=" + price +
                ", createdAt=" + createdAt +
                ", available=" + available +
                '}';
    }
}
