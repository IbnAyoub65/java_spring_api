package com.inscription.devoir.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@Slf4j
@ControllerAdvice
public class InscriptionGlobalExceptionHandler  extends GlobalExceptionHandler{

    @ResponseBody
    @ExceptionHandler(value = {InscriptionException.class})
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorDto handleException(InscriptionException orderNotFoundException){
        log.error(orderNotFoundException.getMessage(),orderNotFoundException);
        return ErrorDto.builder()
                .code(HttpStatus.OK.getReasonPhrase())
                .build();
    }
}
