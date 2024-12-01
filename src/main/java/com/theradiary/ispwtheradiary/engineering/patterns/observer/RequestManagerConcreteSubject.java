package com.theradiary.ispwtheradiary.engineering.patterns.observer;

import com.theradiary.ispwtheradiary.model.Request;

import java.util.ArrayList;
import java.util.List;

public class RequestManagerConcreteSubject extends Subject{
    /*
        La classe ConcreteSubject:
            - Mantiene lo stato del soggetto concreto
            - Invia una notifica agli Observer quando lo stato cambia
            - Memorizza i riferimenti agli oggetti ConcreteObserver
     */

    private static RequestManagerConcreteSubject instance = null;
    private List<Request> requests = new ArrayList<>();
    //Singleton
    public static RequestManagerConcreteSubject getInstance(){
        if(instance==null){
            instance=new RequestManagerConcreteSubject();
        }
        return instance;
    }
    private RequestManagerConcreteSubject(){}

    public void addRequest(Request request){
        requests.add(request);
        notifyObservers();
    }

    public void removeRequest(Request request){
        requests.remove(request);
        notifyObservers();
    }
    //per ottenere lo stato del subject
    public List<Request> getRequests(){
        return requests;
    }

    //metodo da invocare nell'applicativo per avere la lista aggiornata tramite observer
    public void updateRequests(List<Request> requests){
        this.requests = requests;
    }
}
