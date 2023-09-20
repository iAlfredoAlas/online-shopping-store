package com.kodigo.shopping.online.store.models.dto;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

public class CategoryDTO {

    @Getter
    @Setter
    private Long idCategory;

    @Pattern(regexp = "^[a-zA-Z][a-zA-Z]{0,39}+$", message = "The name must not contain spaces at the beginning, nor be longer than 40 characters.")
    @NotBlank(message = "The field cannot remain empty")
    @Getter
    @Setter
    private String categoryName;

    @Pattern(regexp = "^[a-zA-Z][a-zA-Z]{0,39}+$", message = "The name must not contain spaces at the beginning, nor be longer than 40 characters.")
    @NotBlank(message = "The field cannot remain empty")
    @Getter
    @Setter
    private String categoryDescription;

    @NotNull(message = "The status cannot be null")
    @Getter
    @Setter
    private Boolean isCategoryActive = Boolean.TRUE;

}
