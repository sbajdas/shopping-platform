package com.bajdas.shopping.repository;

import java.util.List;

import com.bajdas.shopping.model.Product;
import org.springframework.data.repository.CrudRepository;

public interface ProductRepository extends CrudRepository<Product, Long> {

    List<Product> findAll();
}
