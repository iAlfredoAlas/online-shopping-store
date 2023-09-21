package com.kodigo.shopping.online.store.models.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
public class ClientOrderDTO {

    @Getter
    @Setter
    private Long idClientOrder;

    @NotNull(message = "The status cannot be null")
    @Getter
    @Setter
    private Boolean isClientOrderActive = Boolean.TRUE;

    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "UTC")
    @Getter
    @Setter
    private LocalDate dateOrder;

    @Getter
    @Setter
    private UserDTO idUser;

}
