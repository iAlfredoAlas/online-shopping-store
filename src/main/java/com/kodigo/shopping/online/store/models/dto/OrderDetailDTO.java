package com.kodigo.shopping.online.store.models.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

public class OrderDetailDTO {

    @Getter
    @Setter
    private Long idOrderDetail;

    @Getter
    @Setter
    private Integer quantity;

    @NotNull(message = "The status cannot be null.")
    @Digits(fraction = 2, integer = 4, message = "May not exceed 4 integers and 2 decimal places.")
    @Getter
    @Setter
    private BigDecimal subtotal;

    @Getter
    @Setter
    private ClientOrderDTO idClientOrder;

    @Getter
    @Setter
    private ProductDTO idProduct;

}
