package com.product.calculator.backend.repository;

import com.product.calculator.backend.domain.Product;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends MongoRepository<Product, String> {

    Product findOneById(String id);
}
