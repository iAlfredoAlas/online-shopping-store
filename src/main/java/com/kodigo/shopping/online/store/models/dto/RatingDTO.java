package com.kodigo.shopping.online.store.models.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.kodigo.shopping.online.store.models.Product;
import com.kodigo.shopping.online.store.models.User;
import com.kodigo.shopping.online.store.util.ETypeRating;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.time.LocalDate;

public class RatingDTO {

    @Getter
    @Setter
    private Long idRating;

    @Pattern(regexp = "^[a-zA-Z][a-zA-Z]{0,39}+$", message = "The name must not contain spaces at the beginning, nor be longer than 40 characters.")
    @NotBlank(message = "The field cannot remain empty.")
    @Getter
    @Setter
    private String ratingDetail;

    @Enumerated(EnumType.STRING)
    @Pattern(regexp = "^[a-zA-Z][a-zA-Z]{0,8}+$", message = "The name must not contain spaces at the beginning, nor be longer than 9 characters.")
    @NotBlank(message = "The field cannot remain empty.")
    @Getter
    @Setter
    private ETypeRating typeRating;

    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "UTC")
    @Getter
    @Setter
    private LocalDate dateRating;

    @Getter
    @Setter
    private Boolean isRatingActive;

    @Getter
    @Setter
    private UserDTO idUser;

    @Getter
    @Setter
    private ProductDTO idProduct;

}
