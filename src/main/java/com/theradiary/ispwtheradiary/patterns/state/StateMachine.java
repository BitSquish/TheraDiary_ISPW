package com.theradiary.ispwtheradiary.patterns.state;

public interface StateMachine {
    /*specifica la macchina a stati riferita dal client, definisce gli insieme degli eventi sulla macchina a stati vista dal client, mantiene un riferimento
    ad uno stato concreto attraverso l'astrazione abstractstate*/
    public void goNext();
    public void goBack();
    public void transition(AbstractState state);
    public void setState();
    public AbstractState getCurrentState();
    void start();
}
