package com.kodigo.shopping.online.store.models.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

public class RolDTO {

    @Getter
    @Setter
    private Long idRol;

    @Pattern(regexp = "^[a-zA-Z][a-zA-Z]{0,39}+$", message = "The name must not contain spaces at the beginning, nor be longer than 40 characters.")
    @NotBlank(message = "The field cannot remain empty")
    @Getter
    @Setter
    private String nameRol;

    @NotNull(message = "The status cannot be null")
    @Getter
    @Setter
    private Boolean isRolActive = Boolean.TRUE;

}
