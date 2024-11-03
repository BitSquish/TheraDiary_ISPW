package com.theradiary.ispwtheradiary.controller.graphic;

import com.theradiary.ispwtheradiary.engineering.others.Session;

public class PatientListController extends CommonController {
    public PatientListController (Session session){super(session);}/*In JavaFX, la ListView è un controllo utile per visualizzare un elenco di elementi in modo interattivo. Consente agli utenti di selezionare uno o più elementi da un elenco, ed è molto flessibile per varie applicazioni. Ecco una panoramica su come funzionano le ListView, compresi gli aspetti principali come la creazione, l'aggiunta di elementi e la gestione degli eventi.

Creazione di una ListView
Importare le Librerie Necessarie: Assicurati di importare le classi necessarie in JavaFX.

java
Copia codice
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
Creare una ListView: Puoi creare una ListView nel tuo codice Java. Ecco un esempio base di come creare una ListView:

java
Copia codice
public class ListViewExample extends Application {
    @Override
    public void start(Stage primaryStage) {
        // Creazione della ListView
        ListView<String> listView = new ListView<>();

        // Creazione di un elenco osservabile di elementi
        ObservableList<String> items = FXCollections.observableArrayList(
            "Elemento 1",
            "Elemento 2",
            "Elemento 3",
            "Elemento 4"
        );

        // Aggiunta degli elementi alla ListView
        listView.setItems(items);

        // Creazione del layout e aggiunta della ListView
        VBox vbox = new VBox(listView);
        Scene scene = new Scene(vbox, 300, 250);

        primaryStage.setTitle("Esempio di ListView");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
Aggiungere Elementi alla ListView
Puoi aggiungere elementi alla ListView in vari modi:

Durante la creazione, utilizzando una ObservableList come mostrato nell'esempio precedente.

Dopo la creazione, utilizzando metodi come add(), remove(), o setAll():

java
Copia codice
// Aggiungere un singolo elemento
items.add("Elemento 5");

// Rimuovere un elemento
items.remove("Elemento 2");

// Sostituire tutti gli elementi
items.setAll("Nuovo Elemento 1", "Nuovo Elemento 2");
Selezione di Elementi
La ListView supporta diversi tipi di selezione:

Selezione Singola: L'utente può selezionare solo un elemento alla volta. Questo è il comportamento predefinito.

Selezione Multipla: Puoi abilitare la selezione multipla impostando il metodo getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE).

java
Copia codice
listView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
Gestire Eventi
Puoi gestire eventi come la selezione degli elementi utilizzando i listener. Ad esempio, per rilevare la selezione di un elemento:

java
Copia codice
listView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
    System.out.println("Selezionato: " + newValue);
});
Personalizzazione della ListView
Puoi personalizzare l'aspetto della ListView:

Item Factory: Puoi definire un modo personalizzato per visualizzare gli elementi utilizzando una ListCell personalizzata.
java
Copia codice
listView.setCellFactory(param -> new ListCell<String>() {
    @Override
    protected void updateItem(String item, boolean empty) {
        super.updateItem(item, empty);
        if (empty || item == null) {
            setText(null);
        } else {
            setText(item);
        }
    }
});
Vantaggi della ListView
Interattività: Gli utenti possono facilmente selezionare e interagire con gli elementi.
Flessibilità: Puoi utilizzare diversi tipi di dati e personalizzare l'aspetto.
Supporto per il Modello-View-Controller (MVC): Si integra bene con la logica di applicazione separando l'interfaccia utente dalla logica di business.
Conclusione
La ListView è un controllo potente e versatile per visualizzare e gestire elenchi di dati in JavaFX. Con le opzioni di personalizzazione e interazione, puoi adattarla a molte esigenze diverse nelle tue applicazioni. Se hai domande specifiche o necessiti di ulteriori esempi, sentiti libero di chiedere!
*/}
