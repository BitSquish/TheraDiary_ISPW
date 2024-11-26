package com.theradiary.ispwtheradiary.engineering.patterns.observer;

public abstract class Observer implements Runnable{

    protected long timeOut;
    protected boolean isAlive;

    protected Observer(int timeOut){
        this.timeOut = timeOut;
        this.isAlive = false;
    }

    @Override
    public void run() {
        this.isAlive = true; //segnala thread attivo
        while (this.isAlive){   //continua a chiamare update finchè l'osservatore è attivo
            this.update();
            try {
                Thread.sleep(this.timeOut); //code smell - Introduce una pausa tra le esecuzioni di update, determinata dal valore di timeOut.
            } catch (InterruptedException e) {
               //A lezione era stato fatto così: e.printStackTrace();
                Thread.currentThread().interrupt(); // Ripristina lo stato di interruzione
                break;
            }
        }
    }

    public void stopObservation(){
        this.isAlive = false;
    }   //ferma il thread

    protected void notifySubjectStatus(String message){
        System.out.println("---> " + message); //da sostituire
    }

    public abstract void update();
}
