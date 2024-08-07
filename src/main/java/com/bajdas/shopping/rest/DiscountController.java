package com.bajdas.shopping.rest;

import java.util.List;

import com.bajdas.shopping.model.Discount;
import com.bajdas.shopping.repository.DiscountRepository;
import com.bajdas.shopping.service.DiscountValidator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/discount")
@RequiredArgsConstructor
@Slf4j
public class DiscountController {

    final DiscountRepository discountRepository;


    @PostMapping
    public void addDiscount(@RequestBody Discount discount) {
        DiscountValidator.validate(discount);
        discountRepository.save(discount);
        log.info("Saved new discount: {}", discount);
    }

    @DeleteMapping("/{id}")
    public void removeDiscount(@PathVariable long id) {
        discountRepository.deleteById(id);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Discount> getDiscount(@PathVariable long id) {
        return discountRepository.findById(id)
                                 .map(ResponseEntity::ok)
                                 .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping
    public List<Discount> getDiscounts() {
        return discountRepository.findAll();
    }

}
