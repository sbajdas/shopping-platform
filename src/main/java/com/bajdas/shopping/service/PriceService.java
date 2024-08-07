package com.bajdas.shopping.service;

import java.math.BigDecimal;
import java.util.List;

import com.bajdas.shopping.model.Discount;
import com.bajdas.shopping.model.Product;
import com.bajdas.shopping.model.ProductNotFoundException;
import com.bajdas.shopping.model.ProductPriceRequest;
import com.bajdas.shopping.model.ProductPriceResponse;
import com.bajdas.shopping.model.ServiceException;
import com.bajdas.shopping.repository.DiscountRepository;
import com.bajdas.shopping.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class PriceService {

    final ProductRepository productRepository;
    final DiscountRepository discountRepository;

    public ProductPriceResponse calculatePrice(ProductPriceRequest productPriceRequest) {
        Product product = productRepository.findById(productPriceRequest.uuid()).orElseThrow(ProductNotFoundException::new);

        BigDecimal totalPrice = calculateTotalPrice(product, productPriceRequest.quantity());
        return new ProductPriceResponse(product, productPriceRequest.quantity(), totalPrice);
    }

    private BigDecimal calculateTotalPrice(Product product, BigDecimal quantity) {
        BigDecimal regularTotalPrice = quantity.multiply(product.getPrice());
        BigDecimal biggestDiscount = findBiggestDiscount(quantity, regularTotalPrice);
        BigDecimal totalPrice = regularTotalPrice.subtract(biggestDiscount);
        if (totalPrice.signum() < 0) {
            throw new ServiceException("Price after discounts is negative!");
        }
        return totalPrice;
    }

    private BigDecimal findBiggestDiscount(BigDecimal quantity, BigDecimal regularTotalPrice) {
        List<Discount> potentialDiscounts = discountRepository.findAllByQuantityLessThanEqual(quantity);
        return potentialDiscounts.stream()
                                 .map(discount -> calculateDiscount(regularTotalPrice, discount))
                                 .max(BigDecimal::compareTo)
                                 .orElse(BigDecimal.ZERO);
    }

    private BigDecimal calculateDiscount(BigDecimal regularTotalPrice, Discount discount) {
        BigDecimal calculatedPercentageDiscount = regularTotalPrice.multiply(discount.getPercentDiscount());
        return discount.getAmountDiscount().max(calculatedPercentageDiscount);
    }
}
