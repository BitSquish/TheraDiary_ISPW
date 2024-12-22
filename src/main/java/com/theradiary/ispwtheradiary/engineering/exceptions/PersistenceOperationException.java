package com.theradiary.ispwtheradiary.engineering.exceptions;

public class PersistenceOperationException extends RuntimeException{
    public PersistenceOperationException(String message, Throwable cause) {
        super(message, cause);
    }
}
