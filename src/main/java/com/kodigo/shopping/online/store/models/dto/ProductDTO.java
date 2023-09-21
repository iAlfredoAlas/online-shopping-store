package com.kodigo.shopping.online.store.models.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Digits;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
public class ProductDTO {

    @Getter
    @Setter
    private Long idProduct;

    @Pattern(regexp = "^[a-zA-Z][a-zA-Z]{0,39}+$", message = "The name must not contain spaces at the beginning, nor be longer than 40 characters.")
    @NotBlank(message = "The field cannot remain empty")
    @Getter
    @Setter
    private String productName;

    @Pattern(regexp = "^[a-zA-Z][a-zA-Z]{0,39}+$", message = "The name must not contain spaces at the beginning, nor be longer than 40 characters.")
    @NotBlank(message = "The field cannot remain empty.")
    @Getter
    @Setter
    private String productDescription;

    @NotNull(message = "The status cannot be null.")
    @Getter
    @Setter
    private Integer stock;

    @NotNull(message = "The status cannot be null.")
    @Digits(fraction = 2, integer = 4, message = "May not exceed 4 integers and 2 decimal places.")
    @Getter
    @Setter
    private BigDecimal price;

    @NotNull(message = "The status cannot be null.")
    @Getter
    @Setter
    private Boolean isProductActive = Boolean.TRUE;

    @Getter
    @Setter
    private CategoryDTO idCategory;

}
