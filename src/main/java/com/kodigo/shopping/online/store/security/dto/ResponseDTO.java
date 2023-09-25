package com.kodigo.shopping.online.store.security.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResponseDTO {

    private String message;
    private String code;
    private Object response;
    private Object errors;
    private LocalDateTime timestamp = LocalDateTime.now();

    public ResponseDTO(String message, String code) {
        this.message = message;
        this.code = code;
    }

    public ResponseDTO(String message, String code, Object response, Object errors) {
        this.message = message;
        this.code = code;
        this.response = response;
        this.errors = errors;
    }
}
