package com.theradiary.ispwtheradiary.engineering.patterns.observer;

import com.theradiary.ispwtheradiary.model.beans.RequestBean;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;

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
    public void removeObserver(Observer observer) {
        observers.remove(observer);
    }

    protected void notifyObservers() {
        for (Observer observer : observers) {
            observer.update();
        }
    }
}
