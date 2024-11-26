package com.theradiary.ispwtheradiary.engineering.patterns.observer;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;

public abstract class Subject implements Runnable{

    private List<Observer> observers;   //observer da notificare
    private final Object mutex = new Object();  //per sincronizzare accesso a lista osservatori
    protected boolean isAlive;  //segnala se il thread è attivo

    protected Subject() {
        this((Observer) null);
    }

    protected Subject(Observer obs) {
        this(new Vector<Observer>());   //viene dichiarato come Vector per garantire il thread-safety
        if (obs != null)
            this.observers.add(obs);
    }

    protected Subject(List<Observer> list) {
        this.observers = list;
        this.isAlive = false;
    }

    protected void attach(Observer obs) {   //aggiunge osservatore alla lista
        synchronized (mutex) {  //Usa la sincronizzazione sul mutex per garantire che l'aggiunta sia thread-safe.
            this.observers.add(obs);
        }
    }

    public void detach(Observer obs) {  //rimuove osservatore dalla lista
        synchronized (mutex) {
            this.observers.remove(obs);
        }
    }

    protected void notifyObservers() {
        List<Observer> observersLocal = null;

        // synchronization is used to make sure any observer registered after message is
        // received is not notified
        synchronized (mutex) {
            if (this.isThereAnythingToNotify()) //verifica se ci sono aggiornamenti
                observersLocal = new ArrayList<>(this.observers); //Copia la lista di osservatori in una nuova lista observersLocal. Questo evita problemi se la lista originale viene modificata durante l'iterazione.

            if (observersLocal != null) {
                Iterator<Observer> i = observersLocal.iterator();
                while (i.hasNext()) {
                    Observer obs = i.next();
//System.out.println("Updating Observer from the Subject");
                    obs.update();   //notifica osservatore
                }
            }
        }
    }

    @Override
    public void run() {
        this.isAlive = true;
        while (this.isAlive) {
            this.doSomething();
            this.notifyObservers();
        }
    }

    protected abstract boolean isThereAnythingToNotify();

    protected abstract void doSomething();
}
