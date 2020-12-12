package com.product.calculator.backend.controller.v1;

import com.product.calculator.backend.domain.Product;
import com.product.calculator.backend.service.ProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest
class ProductControllerTest {

    @Mock
    ProductService productService;

    ProductControllerTest productControllerTest;

    @BeforeEach
    void setUp() {
//        MockitoAnnotations.openMocks(ProductController.class);
//        List<Product> products = new ArrayList<>();
//        Product product1 = new Product();
//        product1.setId("1");
//        product1.setName("Horseshoe");
//        product1.setCartonPrice(new BigDecimal(825));
//        product1.setUnitPerCarton(5);
//        products.add(product1);
    }

    @Test
    void getAllProducts() {
        List<Product> products = new ArrayList<>();
        Product product1 = new Product();
        product1.setId("1");
        product1.setName("Horseshoe");
        product1.setCartonPrice(new BigDecimal(825));
        product1.setUnitPerCarton(5);

        Product product2 = new Product();
        product2.setId("2");
        product2.setName("Penguin-ears");
        product2.setCartonPrice(new BigDecimal(175));
        product2.setUnitPerCarton(20);

        products.add(product1);
        products.add(product2);

        when(productService.findAll()).thenReturn(products);
//        assertEquals(2, productControllerTest.getAllProducts().size());
    }

    @Test
    void getProductPriceList() {
    }

    @Test
    void calculate() {
    }
}