package com.learn2code.Shop.domain;

import javax.persistence.*;
import java.sql.Timestamp;

@Table(name = "product")
@Entity
public class Product {

    @Id
    @GeneratedValue
    @Column(name = "id")
    private Integer id;
    @Column(name = "merchant_id")
    private int merchantId;
    @Column(name = "name")
    private String name;
    @Column(name = "description")
    private String description;
    @Column(name = "price")
    private double price;
    @Column(name = "created_at")
    private Timestamp createdAt;
    @Column(name = "available")
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
