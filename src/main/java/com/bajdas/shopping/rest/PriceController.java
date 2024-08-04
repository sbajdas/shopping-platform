package com.bajdas.shopping.rest;

import java.util.List;

import com.bajdas.shopping.model.Discount;
import com.bajdas.shopping.model.ProductPrice;
import com.bajdas.shopping.model.ProductPriceDto;
import com.bajdas.shopping.repository.DiscountRepository;
import com.bajdas.shopping.service.DiscountValidator;
import com.bajdas.shopping.service.PriceService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class PriceController {

    final PriceService priceService;
    final DiscountRepository discountRepository;

    @PostMapping("/price")
    public ProductPrice getPrice(@RequestBody ProductPriceDto productPriceDto) {
        return priceService.calculatePrice(productPriceDto);
    }

    @PostMapping("/discount")
    public void addDiscount(@RequestBody Discount discount) {
        DiscountValidator.validate(discount);
        discountRepository.save(discount);
    }

    @GetMapping("/discount/all")
    public List<Discount> getDiscounts() {
        return discountRepository.findAll();
    }

}
