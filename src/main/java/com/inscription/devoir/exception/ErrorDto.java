package com.inscription.devoir.exception;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Builder
@AllArgsConstructor
@Data
public class ErrorDto {
    private final String code;
    private final String message;
}
