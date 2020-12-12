package com.product.calculator.backend.service;

import com.product.calculator.backend.domain.Product;
import com.product.calculator.backend.dto.ProductUnitPriceDTO;
import com.product.calculator.backend.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Override
    public Product getProduct(String id) {
        return productRepository.findOneById(id);
    }

    @Override
    public List<ProductUnitPriceDTO> generatePriceList(String id) {

        Product product = getProduct(id);
        List<ProductUnitPriceDTO> products = new ArrayList<>();
        for(int unit= 1; unit<=50; unit++) {
            ProductUnitPriceDTO p = new ProductUnitPriceDTO();
            p.setNoOfUnits(unit);
            p.setPrice(calculate(product, unit));
            products.add(p);
        }
        return products;
    }

    @Override
    public List<Product> findAll() {
        return productRepository.findAll();
    }

    @Override
    public BigDecimal calculate(Product product, int unit) {

        int noOfCartons = unit/product.getUnitPerCarton();
        int singleUnits = unit%product.getUnitPerCarton();
        BigDecimal singleUnitPrice = product.getCartonPrice().divide(new BigDecimal(product.getUnitPerCarton()))
                .multiply(new BigDecimal("1.3"));

        BigDecimal total;
        total = product.getCartonPrice().multiply(new BigDecimal(noOfCartons));
        if(noOfCartons >= 3) {
            total = total.multiply(new BigDecimal("0.9"));
        }

        total = total.add(singleUnitPrice.multiply(new BigDecimal(singleUnits)));
        return total;
    }
}
