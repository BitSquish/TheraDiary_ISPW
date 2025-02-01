package com.theradiary.ispwtheradiary.engineering.exceptions;

public class DAOException extends RuntimeException {


    public DAOException(){
        super("Errore nel DAO");
    }

    public DAOException(String message, Throwable cause){
        super(message, cause);
    }

}
