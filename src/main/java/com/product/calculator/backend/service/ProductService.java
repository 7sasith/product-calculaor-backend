package com.product.calculator.backend.service;

import com.product.calculator.backend.domain.Product;
import com.product.calculator.backend.dto.ProductUnitPriceDTO;

import java.math.BigDecimal;
import java.util.List;

public interface ProductService {
    Product getProduct(String id);

    List<ProductUnitPriceDTO> generatePriceList(String id);

    List<Product> findAll();

    BigDecimal calculate(Product product, int unit);
}
