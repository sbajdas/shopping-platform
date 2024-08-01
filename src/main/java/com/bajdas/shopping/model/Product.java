package com.bajdas.shopping.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "products")
public class Product {
    @Id
    private long id;

    private String uuid;
    private String name;
    private double price;

}
