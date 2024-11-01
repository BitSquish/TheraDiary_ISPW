package com.theradiary.ispwtheradiary.engineering.exceptions;
/* nel caso in cui i dati per il login siano errati*/
public class LoginDBException extends Exception{
    public LoginDBException(String message) {
        super(message);
    }
}
