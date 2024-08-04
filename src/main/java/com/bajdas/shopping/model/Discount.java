package com.bajdas.shopping.model;

import java.math.BigDecimal;

import com.bajdas.shopping.service.DiscountValidator;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Value;

@Entity
@Table(name = "DISCOUNTS")
@Data
@NoArgsConstructor
public class Discount {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    long id;
    BigDecimal quantity;
    BigDecimal amountDiscount;
    BigDecimal percentDiscount;

    public Discount(BigDecimal quantity, BigDecimal amountDiscount, BigDecimal percentDiscount) {
        this.quantity = quantity;
        this.amountDiscount = amountDiscount;
        this.percentDiscount = percentDiscount;
        DiscountValidator.validate(this);
    }

}
