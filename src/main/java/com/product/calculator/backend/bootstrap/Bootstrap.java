package com.product.calculator.backend.bootstrap;

import com.product.calculator.backend.domain.Product;
import com.product.calculator.backend.repository.ProductRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class Bootstrap implements CommandLineRunner {

    private static final Logger log = LoggerFactory.getLogger(Bootstrap.class);
    @Autowired
    private ProductRepository productRepository;

    @Override
    public void run(String... args) throws Exception {

        Product product1 = new Product();
        product1.setId("1");
        product1.setName("Horseshoe");
        product1.setCartonPrice(new BigDecimal(825));
        product1.setUnitPerCarton(5);
        productRepository.save(product1);

        Product product2 = new Product();
        product2.setId("2");
        product2.setName("Penguin-ears");
        product2.setCartonPrice(new BigDecimal(175));
        product2.setUnitPerCarton(20);
        productRepository.save(product2);

    }
}
