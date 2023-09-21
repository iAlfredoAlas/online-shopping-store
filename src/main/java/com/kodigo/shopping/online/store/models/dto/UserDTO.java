package com.kodigo.shopping.online.store.models.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.util.ArrayList;
import java.util.List;


@AllArgsConstructor
@NoArgsConstructor
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
    private String userEmail;

    @NotNull(message = "The status cannot be null")
    @Getter
    @Setter
    private Boolean isUserActive = Boolean.TRUE;

    @Getter
    @Setter
    private List<RolDTO> rolList = new ArrayList<>();

    @JsonIgnore
    public String getPassword() {
        return password;
    }

    @JsonProperty
    public void setPassword(String password) {
        this.password = password;
    }
}
