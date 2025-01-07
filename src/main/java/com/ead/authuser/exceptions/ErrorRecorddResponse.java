package com.ead.authuser.exceptions;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.Map;

/**
 * The Record ErrorRecorddResponse
 *
 * @author Miguel Vilela Moraes Ribeiro
 * @since 19/11/2024
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public record ErrorRecorddResponse(
        int errorCode,
        String errorMessage,
        Map<String, String> errorDetails) { }
