package com.theradiary.ispwtheradiary.start;

import com.theradiary.ispwtheradiary.controller.graphic.homepage.HomepageController;
import com.theradiary.ispwtheradiary.engineering.others.FXMLPathConfig;
import com.theradiary.ispwtheradiary.engineering.others.Printer;
import com.theradiary.ispwtheradiary.engineering.others.Session;
import com.theradiary.ispwtheradiary.engineering.patterns.state.StateMachineImpl;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

import javafx.stage.Stage;
import java.io.IOException;
import java.util.Scanner;

public class Main extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws IOException {
        Scanner scanner = new Scanner(System.in);
        boolean validInput = false;
        while (!validInput) {
            try {
                showMenu();
                int scelta = scanner.nextInt();
                scanner.nextLine();
                switch (scelta) {
                    case 1:
                        interfacciaGrafica(stage);
                        validInput = true;
                        break;
                    case 2:
                        interfacciaCLI();
                        validInput = true;
                        break;
                    default:
                        Printer.println("Scelta non valida");
                }
            } catch (Exception e) {
                Printer.errorPrint("Input non valido");
                scanner.nextLine();
            }
        }
    }
    public void interfacciaGrafica(Stage stage) throws IOException {
        FXMLPathConfig fxmlPathConfig = new FXMLPathConfig("/viewPaths.properties");
        Session session = new Session(false);
        FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPathConfig.getFXMLPath("HOMEPAGE_NOT_LOGGED_PATH")));
        loader.setControllerFactory(c -> new HomepageController(fxmlPathConfig, session)); //Controller homepage
        Parent rootParent = loader.load();
        Scene scene = new Scene(rootParent);
        stage.setTitle("Theradiary");
        // stage.getIcons().add(new Image("/logic/view/images/utente.jpg"));
        stage.setScene(scene);
        stage.setResizable(false);
        // Add an event filter to the primary stage to handle the ESC key
        stage.addEventFilter(KeyEvent.KEY_PRESSED, event -> {
            if (event.getCode() == KeyCode.ESCAPE) {
                event.consume();
                Platform.exit();
            }
        });
        stage.show();
    }
    public  void interfacciaCLI(){
        StateMachineImpl context= new StateMachineImpl();
        while(context.getCurrentState()!=null) {
            context.goNext();
        }
        Printer.println("Arrivederci");
    }
    public void showMenu() {
        Printer.println(" ");
        Printer.printlnBlue("-------------- Theradiary --------------");
        Printer.println("Scegli l'interfaccia da utilizzare:");
        Printer.println("1. Interfaccia grafica");
        Printer.println("2. Interfaccia a riga di comando");
        Printer.print("Scelta: ");
    }








}