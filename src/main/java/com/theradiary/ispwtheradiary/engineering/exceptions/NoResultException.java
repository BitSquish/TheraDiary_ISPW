package com.theradiary.ispwtheradiary.engineering.exceptions;

public class NoResultException extends RuntimeException{
    public NoResultException(){super("L'operazione non ha  prodotto risultato");}
    public NoResultException(String message){super(message);}
    public NoResultException(String message, Throwable cause){super(message, cause);}
}
