package com.bajdas.shopping.model;

import java.math.BigDecimal;

public record ProductPrice(
    Product product,
    BigDecimal quantity,
    BigDecimal totalPrice
) {

}
