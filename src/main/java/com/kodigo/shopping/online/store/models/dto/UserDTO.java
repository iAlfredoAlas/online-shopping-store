package com.kodigo.shopping.online.store.models.dto;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.util.ArrayList;
import java.util.List;

public class UserDTO {

    @Getter
    @Setter
    private Long idUser;

    @Pattern(regexp = "^[a-zA-Z][a-zA-Z]{0,39}+$", message = "The name must not contain spaces at the beginning, nor be longer than 40 characters.")
    @NotBlank(message = "The field cannot remain empty")
    @Getter
    @Setter
    private String userName;


    @NotBlank(message = "The field cannot remain empty")
    @Getter
    @Setter
    private String password;

    @Email(message = "The field must be a valid email address")
    @Getter
    @Setter
    private String userMail;

    @NotNull(message = "The status cannot be null")
    @Getter
    @Setter
    private Boolean isUserActive = Boolean.TRUE;

    private List<RolDTO> rolList = new ArrayList<>();

}
