package com.product.calculator.backend.controller.v1;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.product.calculator.backend.domain.Product;
import com.product.calculator.backend.dto.PriceCalculatorDTO;
import com.product.calculator.backend.dto.ProductUnitPriceDTO;
import com.product.calculator.backend.enums.Unit;
import com.product.calculator.backend.service.ProductService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class ProductControllerTest {

    @MockBean
    ProductService productService;
    @Autowired
    private MockMvc mockMvc;
    public static final MediaType APPLICATION_JSON_UTF8 = new MediaType(MediaType.APPLICATION_JSON.getType(),
            MediaType.APPLICATION_JSON.getSubtype(), Charset.forName("utf8"));


    @Test
    void getAllProducts() throws Exception {
        //given
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

        //then
        mockMvc.perform(get("/api/v1/product"))
                // Validate the response code and content type
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].id", is("1")))
                .andExpect(jsonPath("$[0].name", is("Horseshoe")))
                .andExpect(jsonPath("$[0].cartonPrice", is(825)))
                .andExpect(jsonPath("$[0].unitPerCarton", is(5)))
                .andExpect(jsonPath("$[1].id", is("2")))
                .andExpect(jsonPath("$[1].name", is("Penguin-ears")))
                .andExpect(jsonPath("$[1].cartonPrice", is(175)))
                .andExpect(jsonPath("$[1].unitPerCarton", is(20)));;
    }

    @Test
    void getProductPriceList() throws Exception{
        //given
        List<ProductUnitPriceDTO> productUnitPriceDTOS = new ArrayList<>();
        ProductUnitPriceDTO productUnitPriceDTO1 = new ProductUnitPriceDTO();
        productUnitPriceDTO1.setPrice(new BigDecimal(100));
        productUnitPriceDTO1.setNoOfUnits(2);
        productUnitPriceDTOS.add(productUnitPriceDTO1);

        ProductUnitPriceDTO productUnitPriceDTO2 = new ProductUnitPriceDTO();
        productUnitPriceDTO2.setPrice(new BigDecimal("133.3"));
        productUnitPriceDTO2.setNoOfUnits(7);
        productUnitPriceDTOS.add(productUnitPriceDTO2);

        when(productService.generatePriceList("1")).thenReturn(productUnitPriceDTOS);

        //then
        mockMvc.perform(get("/api/v1/product/1/priceList"))
                // Validate the response code and content type
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].noOfUnits", is(productUnitPriceDTO1.getNoOfUnits())))
                .andExpect(jsonPath("$[0].price", is(100)))
                .andExpect(jsonPath("$[1].noOfUnits", is(productUnitPriceDTO2.getNoOfUnits())))
                .andExpect(jsonPath("$[1].price", is(133.3)));
    }

    @Test
    void calculate() throws Exception{
        //given
        Product product1 = new Product();
        product1.setId("1");
        product1.setName("Horseshoe");
        product1.setCartonPrice(new BigDecimal(825));
        product1.setUnitPerCarton(5);

        PriceCalculatorDTO priceCalculatorDTO = new PriceCalculatorDTO();
        priceCalculatorDTO.setUnit(Unit.SINGLE_UNIT);
        priceCalculatorDTO.setProductId("1");
        priceCalculatorDTO.setQuantity(2);

        when(productService.getProduct("1")).thenReturn(product1);
        when(productService.calculate(product1, 2)).thenReturn(new BigDecimal(100));

        //then
        mockMvc.perform(post("/api/v1/product/calculate")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(priceCalculatorDTO)))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk()).andExpect(jsonPath("$", is(100)));
    }

    @Test
    void calculateWhenProductNotFound() throws Exception{
        //given
        PriceCalculatorDTO priceCalculatorDTO = new PriceCalculatorDTO();
        priceCalculatorDTO.setUnit(Unit.SINGLE_UNIT);
        priceCalculatorDTO.setProductId("4");
        priceCalculatorDTO.setQuantity(2);

        when(productService.getProduct("4")).thenReturn(null);

        //then
        mockMvc.perform(post("/api/v1/product/calculate")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(priceCalculatorDTO)))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isBadRequest()).andExpect(jsonPath("$", is("Product Not Found")));
    }

    private static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}