package com.theradiary.ispwtheradiary.engineering.exceptions;
/* nel caso in cui i dati per il login siano errati*/
public class LoginAndRegistrationException extends Exception{
    public LoginAndRegistrationException(String message) {
        super(message);
    }
}
