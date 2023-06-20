package com.inscription.devoir.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class CustomExceptionHandler extends ResponseEntityExceptionHandler {

    // Gestion d'une exception spécifique
    @ResponseBody
    @ExceptionHandler(InscriptionException.class)
    @ResponseStatus(HttpStatus.OK)
    public ErrorDto handleYourException(InscriptionException ex, WebRequest request) {
        // Créez un objet ErrorResponse pour encapsuler les détails de l'erreur
        ErrorDto errorResponse = new ErrorDto(HttpStatus.OK.getReasonPhrase(), ex.getMessage());

        // Renvoyez une réponse avec le corps de l'erreur et le statut approprié
        return errorResponse;
    }

    // Gestion d'autres exceptions...

    // Gestion des exceptions par défaut
    @ResponseBody
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorDto handleDefaultException(Exception ex, WebRequest request) {
        // Créez un objet ErrorResponse pour encapsuler les détails de l'erreur
        ErrorDto errorResponse = new ErrorDto(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(), ex.getMessage());

        // Renvoyez une réponse avec le corps de l'erreur et le statut approprié
        return errorResponse;
    }
}

