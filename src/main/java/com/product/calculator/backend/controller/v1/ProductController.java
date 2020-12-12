package com.product.calculator.backend.controller.v1;

import com.product.calculator.backend.domain.Product;
import com.product.calculator.backend.dto.PriceCalculatorDTO;
import com.product.calculator.backend.dto.ProductUnitPriceDTO;
import com.product.calculator.backend.enums.Unit;
import com.product.calculator.backend.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("api/v1/product")
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping
    public List<Product> getAllProducts(){
       return productService.findAll();
    }

    @GetMapping("{id}/priceList")
    public List<ProductUnitPriceDTO> getProductPriceList(@PathVariable("id") String id) {
        return productService.generatePriceList(id);
    }

    @PostMapping("calculate")
    public ResponseEntity calculate(@RequestBody PriceCalculatorDTO priceCalculatorDTO) {

        Product product = productService.getProduct(priceCalculatorDTO.getProductId());
        if(product == null) {
            return ResponseEntity.badRequest().body("Product Not Found");
        }
        int noOfUnits;
        if(Unit.CARTON.equals(priceCalculatorDTO.getUnit())) {
            noOfUnits = product.getUnitPerCarton() * priceCalculatorDTO.getQuantity();
        } else {
            noOfUnits = priceCalculatorDTO.getQuantity();
        }
        return ResponseEntity.ok(productService.calculate(product, noOfUnits));
    }
}
