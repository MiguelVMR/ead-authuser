package com.ead.authuser.dtos;

import jakarta.validation.constraints.NotBlank;

/**
 * The Record LoginRecordDto
 *
 * @author Miguel Vilela Moraes Ribeiro
 * @since 16/04/2025
 */
public record LoginRecordDto(@NotBlank String username,
                             @NotBlank String password) {
}
