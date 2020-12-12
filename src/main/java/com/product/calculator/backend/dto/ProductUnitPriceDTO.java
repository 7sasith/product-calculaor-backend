package com.product.calculator.backend.dto;

import java.math.BigDecimal;

public class ProductUnitPriceDTO {
    private int noOfUnits;
    private BigDecimal price;

    public int getNoOfUnits() {
        return noOfUnits;
    }

    public void setNoOfUnits(int noOfUnits) {
        this.noOfUnits = noOfUnits;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }
}
