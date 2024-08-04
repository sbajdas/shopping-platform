package com.bajdas.shopping.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import com.bajdas.shopping.model.Discount;
import com.bajdas.shopping.model.Product;
import com.bajdas.shopping.model.ProductNotFoundException;
import com.bajdas.shopping.model.ProductPrice;
import com.bajdas.shopping.model.ProductPriceDto;
import com.bajdas.shopping.repository.DiscountRepository;
import com.bajdas.shopping.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PriceService {

    final ProductRepository productRepository;
    final DiscountRepository discountRepository;

    public ProductPrice calculatePrice(ProductPriceDto productPriceDto) {
        Product product = productRepository.findById(productPriceDto.uuid()).orElseThrow(ProductNotFoundException::new);

        BigDecimal totalPrice = calculateTotalPrice(product, productPriceDto.quantity());
        return new ProductPrice(product, productPriceDto.quantity(), totalPrice);
    }

    private BigDecimal calculateTotalPrice(Product product, BigDecimal quantity) {
        BigDecimal regularTotalPrice = quantity.multiply(product.getPrice());
        BigDecimal biggestDiscount = findBiggestDiscount(quantity, regularTotalPrice);
        return regularTotalPrice.subtract(biggestDiscount);
    }

    private BigDecimal findBiggestDiscount(BigDecimal quantity, BigDecimal regularTotalPrice) {
        List<Discount> potentialDiscounts = discountRepository.findAllByQuantityGreaterThanEqual(quantity);
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
