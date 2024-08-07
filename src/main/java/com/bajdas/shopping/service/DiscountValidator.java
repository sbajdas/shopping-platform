package com.bajdas.shopping.service;

import java.math.BigDecimal;
import java.util.function.Predicate;

import com.bajdas.shopping.model.Discount;

public class DiscountValidator {
    private static final Predicate<BigDecimal> NON_ZERO = number -> number.signum() != 0;
    private static final Predicate<BigDecimal> NEGATIVE = number -> number.signum() < 0;
    private static final Predicate<BigDecimal> GREATER_THAN_ONE = number -> number.compareTo(BigDecimal.ONE) > 0;

    public static void validate(Discount discount) {
        if (!oneDiscountIsNonZero(discount)) {
            throw new IllegalArgumentException("Either amount discount or percent discount must be non-zero.");
        }
        if (isAnyValueNegative(discount)) {
            throw new IllegalArgumentException("Quantity, amount discount, and percent discount must be non-negative.");
        }
        if (isPercentDiscountGreaterThanOne(discount)) {
            throw new IllegalArgumentException("Percent discount value should be between 0 and 1.");
        }
    }

    private static boolean oneDiscountIsNonZero(Discount discount) {
        return NON_ZERO.test(discount.getAmountDiscount()) ^ NON_ZERO.test(discount.getPercentDiscount());
    }

    private static boolean isAnyValueNegative(Discount discount) {
        return NEGATIVE.test(discount.getQuantity()) || NEGATIVE.test(discount.getAmountDiscount()) || NEGATIVE.test(discount.getPercentDiscount());
    }

    private static boolean isPercentDiscountGreaterThanOne(Discount discount) {
        return GREATER_THAN_ONE.test(discount.getPercentDiscount());
    }

}
