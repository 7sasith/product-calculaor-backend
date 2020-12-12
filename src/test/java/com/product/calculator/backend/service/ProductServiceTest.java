package com.product.calculator.backend.service;

import com.product.calculator.backend.domain.Product;
import com.product.calculator.backend.dto.ProductUnitPriceDTO;
import com.product.calculator.backend.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.internal.configuration.MockAnnotationProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@SpringBootTest
class ProductServiceTest {

    @Autowired
    ProductService productService;
    @MockBean
    ProductRepository productRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getProduct() {
        //given
        Product product = new Product();
        product.setId("1");
        product.setName("Test");
        product.setUnitPerCarton(100);
        product.setCartonPrice(new BigDecimal(200));
        when(productRepository.findOneById("1")).thenReturn(product);

        //when
        Product p = productService.getProduct("1");

        //then
        assertEquals("Test",p.getName());
        assertEquals("1",p.getId());
        assertEquals(100,p.getUnitPerCarton());
        assertEquals(new BigDecimal(200), p.getCartonPrice());
    }

    @Test
    void generatePriceList() {
        //given
        Product product = new Product();
        product.setId("1");
        product.setName("Test");
        product.setUnitPerCarton(100);
        product.setCartonPrice(new BigDecimal(200));
        when(productRepository.findOneById("1")).thenReturn(product);

        //when
        List<ProductUnitPriceDTO> productUnitPriceDTOS = productService.generatePriceList("1");

        //then
        assertEquals(50, productUnitPriceDTOS.size());
    }

    @Test
    void findAll() {

        //given
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

        List<Product> products = new ArrayList<>();
        products.add(product1);
        products.add(product2);
        when(productRepository.findAll()).thenReturn(products);

        //when
        List<Product> productList = productService.findAll();

        //then
        assertEquals(2, productList.size());
    }

    @Test
    void calculateMoreThan3Cartons() {
        //given
        Product product1 = new Product();
        product1.setId("1");
        product1.setName("Horseshoe");
        product1.setCartonPrice(new BigDecimal(825));
        product1.setUnitPerCarton(5);

        //when
        BigDecimal total = productService.calculate(product1, 20);

        //then
        assertEquals(0, total.compareTo(new BigDecimal(2970)));
    }

    @Test
    void calculateMoreThan3CartonsAndSingleUnits() {
        //given
        Product product1 = new Product();
        product1.setId("1");
        product1.setName("Horseshoe");
        product1.setCartonPrice(new BigDecimal(825));
        product1.setUnitPerCarton(5);

        //when
        BigDecimal total = productService.calculate(product1, 23);

        //then
        assertEquals(0, total.compareTo(new BigDecimal(3613.5)));
    }

    @Test
    void calculateLessThan3CartonsAndSingleUnits() {
        //given
        Product product1 = new Product();
        product1.setId("1");
        product1.setName("Horseshoe");
        product1.setCartonPrice(new BigDecimal(825));
        product1.setUnitPerCarton(5);

        //when
        BigDecimal total = productService.calculate(product1, 12);

        //then
        assertEquals(0, total.compareTo(new BigDecimal(2079)));
    }

    @Test
    void calculateLessThan3Cartons() {
        //given
        Product product1 = new Product();
        product1.setId("1");
        product1.setName("Horseshoe");
        product1.setCartonPrice(new BigDecimal(825));
        product1.setUnitPerCarton(5);

        //when
        BigDecimal total = productService.calculate(product1, 4);

        //then
        assertEquals(0, total.compareTo(new BigDecimal(858)));
    }
}