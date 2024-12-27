package com.theradiary.ispwtheradiary.engineering.patterns.state;

public abstract class AbstractState {
    //specifica l'interfaccia che incapsula la logica del comportamento associato ad un determinato stato
    public static final String SCELTA_NON_VALIDA = "Scelta non valida";
    protected AbstractState(){}
    //transizione di stato
    public void enter(StateMachineImpl context){}
    public void exit(StateMachineImpl context){}

    public void goBack(StateMachineImpl contextSM){
        contextSM.goBack();
    }
    public void goNext(StateMachineImpl context,AbstractState nextState){
        context.transition(nextState);
    }
    //azione
    public abstract void action(StateMachineImpl context);

    public void showMenu(){}
    public void stampa(){}

}
