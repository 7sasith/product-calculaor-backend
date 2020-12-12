package com.product.calculator.backend.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;

@Document("products")
public class Product {
    @Id
    private String id;
    private String name;
    private int unitPerCarton;
    private BigDecimal cartonPrice;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getUnitPerCarton() {
        return unitPerCarton;
    }

    public void setUnitPerCarton(int unitPerCarton) {
        this.unitPerCarton = unitPerCarton;
    }

    public BigDecimal getCartonPrice() {
        return cartonPrice;
    }

    public void setCartonPrice(BigDecimal cartonPrice) {
        this.cartonPrice = cartonPrice;
    }
}
