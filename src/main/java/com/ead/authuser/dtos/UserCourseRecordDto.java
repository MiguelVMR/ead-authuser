package com.ead.authuser.dtos;

import jakarta.validation.constraints.NotNull;

import java.util.UUID;

/**
 * The Record UserCourseRecordDto
 *
 * @author Miguel Vilela Moraes Ribeiro
 * @since 15/12/2024
 */
public record UserCourseRecordDto(UUID userId,
                                  @NotNull(message = "CourseId is mandatory") UUID courseId)  {
}
