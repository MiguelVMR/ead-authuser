package com.ead.authuser.dtos;

import jakarta.validation.constraints.NotBlank;

/**
* The Record JwtRecordDto
*
* @author Miguel Vilela Moraes Ribeiro
* @since 16/04/2025
*/
public record JwtRecordDto(@NotBlank String token,
                           String type) {

    public JwtRecordDto(@NotBlank String token) {
        this(token, "Bearer");
    }
}
