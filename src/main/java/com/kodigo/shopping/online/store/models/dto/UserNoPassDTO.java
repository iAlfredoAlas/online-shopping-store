package com.kodigo.shopping.online.store.models.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
public class UserNoPassDTO {

    @Getter
    @Setter
    private Long idUser;

    @Getter
    @Setter
    private String userName;

    @Getter
    @Setter
    private String userEmail;

    @Getter
    @Setter
    private Boolean isUserActive = Boolean.TRUE;

    @Getter
    @Setter
    private List<RolDTO> rolList = new ArrayList<>();

}
