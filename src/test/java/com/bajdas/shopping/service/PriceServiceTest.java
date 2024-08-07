package com.bajdas.shopping.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.bajdas.shopping.model.Discount;
import com.bajdas.shopping.model.Product;
import com.bajdas.shopping.model.ProductPriceRequest;
import com.bajdas.shopping.model.ProductPriceResponse;
import com.bajdas.shopping.model.ServiceException;
import com.bajdas.shopping.repository.DiscountRepository;
import com.bajdas.shopping.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PriceServiceTest {

    private final Product testProduct = new Product("testProduct", BigDecimal.TEN);
    private final Discount twentyPercentDiscount = new Discount(BigDecimal.ONE, BigDecimal.ZERO, new BigDecimal("0.2"));
    @Mock
    ProductRepository productRepository;
    @Mock
    DiscountRepository discountRepository;

    @InjectMocks
    PriceService service;


    @BeforeEach
    public void setUp() {
        when(productRepository.findById(any())).thenReturn(Optional.of(testProduct));
    }

    @Test
    void shouldCalculateBasicPrice() {
        //given
        var productPriceDto = new ProductPriceRequest(UUID.randomUUID(), new BigDecimal("2"));
        var expectedTotalPrice = testProduct.getPrice()
                                            .multiply(productPriceDto.quantity());

        //when
        ProductPriceResponse calculatedPrice = service.calculatePrice(productPriceDto);

        //then
        assertEquals(0, expectedTotalPrice.compareTo(calculatedPrice.totalPrice()));
    }

    @Test
    void shouldInclude20PercentDiscount() {
        //given
        var productPriceDto = new ProductPriceRequest(UUID.randomUUID(), new BigDecimal("2"));
        when(discountRepository.findAllByQuantityLessThanEqual(any())).thenReturn(List.of(twentyPercentDiscount));
        var expectedTotalPrice = testProduct.getPrice()
                                            .multiply(productPriceDto.quantity())
                                            .multiply(BigDecimal.ONE.subtract(twentyPercentDiscount.getPercentDiscount()));

        //when
        ProductPriceResponse calculatedPrice = service.calculatePrice(productPriceDto);

        //then
        assertEquals(0, expectedTotalPrice.compareTo(calculatedPrice.totalPrice()));
    }

    @Test
    void shouldIncludeBestPercentageDiscount() {
        //given
        var productPriceDto = new ProductPriceRequest(UUID.randomUUID(), new BigDecimal("2"));
        var tenPercentDiscount = new Discount(BigDecimal.ONE, BigDecimal.ZERO, new BigDecimal("0.1"));
        var fiftyPercentDiscount = new Discount(BigDecimal.ONE, BigDecimal.ZERO, new BigDecimal("0.5"));
        when(discountRepository.findAllByQuantityLessThanEqual(any()))
            .thenReturn(List.of(tenPercentDiscount, twentyPercentDiscount, fiftyPercentDiscount));

        var expectedTotalPrice = testProduct.getPrice()
                                            .multiply(productPriceDto.quantity())
                                            .multiply(BigDecimal.ONE.subtract(fiftyPercentDiscount.getPercentDiscount()));

        //when
        ProductPriceResponse calculatedPrice = service.calculatePrice(productPriceDto);

        //then
        assertEquals(0, expectedTotalPrice.compareTo(calculatedPrice.totalPrice()));
        }

    @Test
    void shouldIncludeBestAbsoluteDiscount() {
        //given
        var productPriceDto = new ProductPriceRequest(UUID.randomUUID(), new BigDecimal("2"));
        var oneDollartDiscount = new Discount(BigDecimal.ONE, BigDecimal.ONE, BigDecimal.ZERO);
        var twoDollarDiscount = new Discount(BigDecimal.ONE, BigDecimal.TWO, BigDecimal.ZERO);
        var tenDollarDiscount = new Discount(BigDecimal.ONE, BigDecimal.TEN, BigDecimal.ZERO);
        when(discountRepository.findAllByQuantityLessThanEqual(any())).thenReturn(List.of(oneDollartDiscount, twoDollarDiscount, tenDollarDiscount));

        var expectedTotalPrice = testProduct.getPrice()
                                            .multiply(productPriceDto.quantity())
                                            .subtract(tenDollarDiscount.getAmountDiscount());

        //when
        ProductPriceResponse calculatedPrice = service.calculatePrice(productPriceDto);

        //then
        assertEquals(0, expectedTotalPrice.compareTo(calculatedPrice.totalPrice()));
    }

    @Test
    void shouldNotAllowNegativePrice() {
        //given
        var productPriceDto = new ProductPriceRequest(UUID.randomUUID(), new BigDecimal("0.5"));
        var tenDollarDiscount = new Discount(BigDecimal.ONE, BigDecimal.TEN, BigDecimal.ZERO);
        when(discountRepository.findAllByQuantityLessThanEqual(any())).thenReturn(List.of(tenDollarDiscount));

        //then
        assertThrows(ServiceException.class, () -> service.calculatePrice(productPriceDto));
    }


}
