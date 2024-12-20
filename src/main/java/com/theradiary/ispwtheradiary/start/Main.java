package com.theradiary.ispwtheradiary.start;

import com.theradiary.ispwtheradiary.controller.graphic.homepage.HomepageGUI;
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
import com.theradiary.ispwtheradiary.engineering.others.mappers.MapperRegistration;

import javafx.stage.Stage;
import java.io.IOException;
import java.util.Scanner;

public class Main extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws IOException {
        MapperRegistration.registerMappers();   //Registrazione dei mappers che convertono bean in model e viceversa
        Scanner scanner = new Scanner(System.in);
        boolean validInput = false;
        while (!validInput) {
            try {
                showMenu();
                int choice = scanner.nextInt();
                scanner.nextLine();
                switch (choice) {
                    case 1:
                        interfaceGrafika(stage);
                        validInput = true;
                        break;
                    case 2:
                        interfaceCLI();
                        validInput = true;
                        break;
                    default:
                        Printer.println("Scelta non valida");
                }
            } catch (Exception e) {
                Printer.errorPrint(e.getMessage());
                scanner.nextLine();
            }
        }
    }
    public void interfaceGrafika(Stage stage) throws IOException {
        FXMLPathConfig fxmlPathConfig = new FXMLPathConfig("/viewPaths.properties");
        Session session = new Session();
        FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPathConfig.getFXMLPath("HOMEPAGE_NOT_LOGGED_PATH")));
        loader.setControllerFactory(c -> new HomepageGUI(fxmlPathConfig, session)); //Controller homepage
        Parent rootParent = loader.load();
        Scene scene = new Scene(rootParent);
        stage.setTitle("Theradiary");
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
    public  void interfaceCLI(){
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