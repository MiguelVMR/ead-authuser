package com.ead.authuser.dtos;

import jakarta.validation.constraints.NotNull;

import java.util.UUID;

/**
 * The Record InstructorRecordDto
 *
 * @author Miguel Vilela Moraes Ribeiro
 * @since 16/12/2024
 */
public record InstructorRecordDto(@NotNull(message = "UserId is mandatory") UUID userId) {
}
