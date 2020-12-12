package com.product.calculator.backend.dto;

import com.product.calculator.backend.enums.Unit;

public class PriceCalculatorDTO {
    private String productId;
    private Unit unit;
    private int quantity;

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public Unit getUnit() {
        return unit;
    }

    public void setUnit(Unit unit) {
        this.unit = unit;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
