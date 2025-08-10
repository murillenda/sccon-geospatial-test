package com.github.murillenda.sccon.geospatial.api.exceptionhandler;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.time.LocalDateTime;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record ErrorResponse(
        Integer status,
        LocalDateTime timestamp,
        String title,
        String detail,
        List<ErrorField> fields
) {
    public record ErrorField(String name, String message) {}
}