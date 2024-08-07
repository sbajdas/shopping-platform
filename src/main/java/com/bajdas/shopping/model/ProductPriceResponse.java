package com.bajdas.shopping.model;

import java.math.BigDecimal;

public record ProductPriceResponse(
    Product product,
    BigDecimal quantity,
    BigDecimal totalPrice
) {

}
