package com.theradiary.ispwtheradiary.engineering.exceptions;

public class LoadingException extends RuntimeException {
    public LoadingException(String message, Throwable cause) {
        super(message, cause);
    }
    /*sofia*/
    /*Propago l'eccezione originale (IOException) come causa della nuova eccezione (LoadingException), sfruttando il costruttore di LoadingException che accetta una causa.*/
}
