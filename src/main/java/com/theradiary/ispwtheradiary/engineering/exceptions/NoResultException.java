package com.theradiary.ispwtheradiary.engineering.exceptions;

public class NoResultException extends Exception{
    public NoResultException(){super("L'operazione non ha  prodotto risultato");}
    public NoResultException(String message){super(message);}
}
