package com.theradiary.ispwtheradiary.patterns.observer;



import java.util.ArrayList;

import java.util.List;


public abstract class Subject{

    /*
        La classe subject:
            - Ha conoscenza dei propri Observer, i quali possono essere in numero illimitato
            - Fornisce operazioni per l’aggiunta e cancellazione di Observer
            - Fornisce operazioni per la notifica agli Observer.
     */

    private final List<Observer> observers;
    protected Subject() {
        observers = new ArrayList<>();
    }

    public void addObserver(Observer observer) {
        observers.add(observer);
    }


    protected void notifyObservers() {
        for (Observer observer : observers) {
            observer.update();
        }
    }
}
