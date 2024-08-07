package com.bajdas.shopping.rest;

import com.bajdas.shopping.model.ProductPrice;
import com.bajdas.shopping.model.ProductPriceDto;
import com.bajdas.shopping.service.PriceService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class PriceController {

    final PriceService priceService;

    @PostMapping("/price")
    public ProductPrice calculatePrice(@RequestBody ProductPriceDto productPriceDto) {
        return priceService.calculatePrice(productPriceDto);
    }



}
