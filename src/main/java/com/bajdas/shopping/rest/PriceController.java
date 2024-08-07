package com.bajdas.shopping.rest;

import com.bajdas.shopping.model.ProductPriceRequest;
import com.bajdas.shopping.model.ProductPriceResponse;
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
    public ProductPriceResponse calculatePrice(@RequestBody ProductPriceRequest productPriceRequest) {
        return priceService.calculatePrice(productPriceRequest);
    }



}
