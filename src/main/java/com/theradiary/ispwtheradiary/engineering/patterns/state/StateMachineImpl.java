package com.theradiary.ispwtheradiary.engineering.patterns.state;

import com.theradiary.ispwtheradiary.engineering.others.beans.LoggedUserBean;

import java.util.ArrayDeque;

public class StateMachineImpl implements StateMachine {
    //implementa l'interfccia state machine e gestisce la transizione di stato
    private AbstractState currentState;
    private LoggedUserBean user;
    private ArrayDeque<AbstractState> stateHistory;
    public StateMachineImpl(){
        this.currentState = new InitialState();
        this.stateHistory = new ArrayDeque<>();
    }
    @Override
    public void goNext() {
        if(currentState!=null){
            this.currentState.action(this);
        }

    }

    @Override
    public void goBack() {
        if(!stateHistory.isEmpty()){
            this.currentState.exit(this);
            this.currentState = stateHistory.pop();
            this.currentState.enter(this);
            goNext();
        }

    }

    @Override
    public void transition(AbstractState state) {
        this.currentState.exit(this);
        if(currentState!=null){
            stateHistory.push(currentState);
        }
        this.currentState = state;
        this.currentState.enter(this);
        goNext();

    }

    @Override
    public void setState() {
        this.currentState=null;

    }

    @Override
    public AbstractState getCurrentState() {
        return currentState;
    }

    @Override
    public void start() {
        currentState= new InitialState();
        goNext();

    }
    public LoggedUserBean getUser() {
        return user;
    }
    public void setUser(LoggedUserBean user) {
        this.user = user;
    }
}
