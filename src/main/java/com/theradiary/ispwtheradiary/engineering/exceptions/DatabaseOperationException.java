package com.theradiary.ispwtheradiary.engineering.exceptions;

public class DatabaseOperationException extends Exception{
    public DatabaseOperationException(String message, Throwable cause){
        super(message, cause);
    }
}
