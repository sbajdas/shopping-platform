package com.bajdas.shopping.repository;

import java.util.List;
import java.util.UUID;

import com.bajdas.shopping.model.Product;
import lombok.NonNull;
import org.springframework.data.repository.CrudRepository;

public interface ProductRepository extends CrudRepository<Product, UUID> {

    @NonNull
    List<Product> findAll();
}
