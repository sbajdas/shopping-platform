package com.bajdas.shopping.model;

import java.math.BigDecimal;

import com.bajdas.shopping.service.DiscountValidator;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Immutable;

@Immutable
@Entity
@Table(name = "DISCOUNTS")
@SequenceGenerator(name = "sequence", sequenceName = "mysequence")
@Data
@NoArgsConstructor
public class Discount {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequence")
    long id;
    BigDecimal quantity;
    BigDecimal amountDiscount = BigDecimal.ZERO;
    BigDecimal percentDiscount = BigDecimal.ZERO;

    public Discount(BigDecimal quantity, BigDecimal amountDiscount, BigDecimal percentDiscount) {
        this.quantity = quantity;
        this.amountDiscount = amountDiscount;
        this.percentDiscount = percentDiscount;
        DiscountValidator.validate(this);
    }

}
