package com.inscription.devoir.exception;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.ValidationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.stream.Collectors;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {

    @ResponseBody
    @ExceptionHandler(value = {Exception.class})
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorDto handleException(Exception exception){
        log.error(exception.getMessage(),exception);
        return ErrorDto.builder().code(HttpStatus.BAD_REQUEST.getReasonPhrase())
                .message("Unexpected error")
                .build();

    }


    @ResponseBody
    @ExceptionHandler(value = {ValidationException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorDto handleException(ValidationException validationException){
        ErrorDto errorDto;
        if(validationException instanceof ConstraintViolation){
            String violation = extractViolationsFromException((ConstraintViolationException) validationException);
            log.error(violation,validationException);
            errorDto = ErrorDto.builder()
                    .code(HttpStatus.BAD_REQUEST.getReasonPhrase())
                    .message(violation)
                    .build();
        }else{
            String exceptionMessage = validationException.getMessage();
            log.error(exceptionMessage,validationException);
            errorDto = ErrorDto.builder()
                    .code(HttpStatus.BAD_REQUEST.getReasonPhrase())
                    .build();
        }
        return errorDto;
    }

    private String extractViolationsFromException(ConstraintViolationException validationException){
        return validationException.getConstraintViolations().stream().map(ConstraintViolation::getMessage)
                .collect(Collectors.joining("--"));
    }
}
