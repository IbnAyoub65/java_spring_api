package com.inscription.devoir.exception;

public class InscriptionException extends RuntimeException{

    public  InscriptionException(String message){
        super(message);
    }

    public InscriptionException(String message, Throwable cause) { super(message, cause);
    }
}
