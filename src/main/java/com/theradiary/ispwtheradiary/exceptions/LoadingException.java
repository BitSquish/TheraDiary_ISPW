package com.theradiary.ispwtheradiary.exceptions;

public class LoadingException extends RuntimeException {
    public LoadingException(String message, Throwable cause) {
        super(message, cause);
    }
    /*sofia*/
    /*Usato in caso il caricamento della scena fallisca, quindi  segnala il problema.*/
}
