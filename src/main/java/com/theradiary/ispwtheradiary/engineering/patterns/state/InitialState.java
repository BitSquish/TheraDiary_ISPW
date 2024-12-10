package com.theradiary.ispwtheradiary.engineering.patterns.state;

import com.theradiary.ispwtheradiary.controller.graphic.CLI.LoginCLI;
import com.theradiary.ispwtheradiary.controller.graphic.CLI.RegisterCLI;
import com.theradiary.ispwtheradiary.engineering.others.Printer;

import java.util.Scanner;

public class InitialState extends AbstractState{
    /*Implementa il comportamento associato ad un particolare stato, in seguito creo una sottovlasse per ogni stato che si vuole modellare
     Task, appuntamenti, studio medico, homepage ecc*/
    public InitialState(){
        super();
    }
    @Override
    public void action(StateMachineImpl context) {
        AbstractState nextState;
        Scanner scanner = new Scanner(System.in);
        int choice;
        while((context.getCurrentState()!=null)){
            try{
                this.stampa();
                this.showMenu();
                choice = scanner.nextInt();
                switch (choice) {
                    case (1):
                        context.setState();
                        System.exit(0);
                        return;
                    case (2):
                        nextState = new LoginCLI();
                        goNext(context, nextState);
                        break;
                    case (3):
                        nextState = new RegisterCLI();
                        goNext(context, nextState);
                        break;
                    default:
                        Printer.errorPrint("Scelta non valida");
                        break;
                }
            }catch (Exception e){
                Printer.errorPrint("Errore nella scelta");
                scanner.nextLine();
            }
        }
    }
    @Override
    public void showMenu() {
        Printer.println("1) Esci");
        Printer.println("2) Login");
        Printer.println("3) Registrati");
        Printer.print("Scelta: ");
    }
    @Override
    public void stampa() {
        Printer.println("");
        Printer.println("Benvenuto in TheraDiary");
        Printer.println("é necessario effettuare il login o registrarsi");
    }
    @Override
    public void exit(StateMachineImpl stateMachine){}
}
