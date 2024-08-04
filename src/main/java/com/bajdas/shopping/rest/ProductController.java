package com.bajdas.shopping.rest;

import java.util.List;
import java.util.UUID;

import com.bajdas.shopping.model.Product;
import com.bajdas.shopping.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/products")
@RequiredArgsConstructor
public class ProductController {

    final ProductRepository productRepository;


    @PostMapping("/add")
    public void addProduct(@RequestBody Product product) {
        productRepository.save(product);
    }

    @GetMapping
    public List<Product> getProducts(){
        return productRepository.findAll();
    }

    @GetMapping("/{uuid}")
    public ResponseEntity<Product> getProduct(@PathVariable UUID uuid) {
        return productRepository.findById(uuid)
                                .map(ResponseEntity::ok)
                                .orElse(ResponseEntity.notFound().build());
    }

}
