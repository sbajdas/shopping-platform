package com.bajdas.shopping.model;

import java.math.BigDecimal;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DiscountTest {

    @Test
    void shouldCreateProperDiscount() {
        //given
        Discount discount = new Discount(BigDecimal.ONE, BigDecimal.ONE, BigDecimal.ZERO);
        //when

        //then
        assertEquals(BigDecimal.ONE, discount.getQuantity());
        assertEquals(BigDecimal.ONE, discount.getAmountDiscount());
        assertEquals(BigDecimal.ZERO, discount.getPercentDiscount());
    }

    @Test
    void shouldThrowExceptionWhenBothDiscountsAreSet() {
        assertThrows(IllegalArgumentException.class, () -> new Discount(BigDecimal.ONE, BigDecimal.ONE, new BigDecimal("0.1")));
    }
    
    @Test
    void shouldThrowExceptionForNegativeValue() {
        assertThrows(IllegalArgumentException.class, () -> new Discount(new BigDecimal("-1"), BigDecimal.ZERO, new BigDecimal("0.1")));
        }

}
