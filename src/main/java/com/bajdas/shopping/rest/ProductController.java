package com.bajdas.shopping.rest;

import java.util.List;

import com.bajdas.shopping.model.Product;
import com.bajdas.shopping.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/product")
public class ProductController {

    private final ProductRepository productRepository;

    @Autowired
    public ProductController(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }


    @PostMapping("/add")
    public void addProduct(@RequestBody Product product) {
        productRepository.save(product);
    }

    @GetMapping("/list")
    public List<Product> getProducts(){
        return productRepository.findAll();
    }

}
