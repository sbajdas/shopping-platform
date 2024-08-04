package com.bajdas.shopping.repository;

import java.math.BigDecimal;
import java.util.List;

import com.bajdas.shopping.model.Discount;
import org.springframework.data.repository.CrudRepository;

public interface DiscountRepository extends CrudRepository<Discount, Long> {

    List<Discount> findAllByQuantityGreaterThanEqual(BigDecimal quantity);
    List<Discount> findAll();

}
