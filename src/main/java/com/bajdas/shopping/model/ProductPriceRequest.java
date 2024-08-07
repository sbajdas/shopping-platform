package com.bajdas.shopping.model;

import java.math.BigDecimal;
import java.util.UUID;

public record ProductPriceRequest(UUID uuid, BigDecimal quantity) {

    }
