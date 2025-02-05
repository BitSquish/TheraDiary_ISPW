package com.theradiary.ispwtheradiary.controller.graphic.gui;

import com.theradiary.ispwtheradiary.controller.application.RequestApplicationController;
import com.theradiary.ispwtheradiary.controller.graphic.gui.CommonGUI;
import com.theradiary.ispwtheradiary.engineering.others.FXMLPathConfig;
import com.theradiary.ispwtheradiary.engineering.others.Session;
import com.theradiary.ispwtheradiary.engineering.patterns.observer.Observer;
import com.theradiary.ispwtheradiary.engineering.patterns.observer.RequestManagerConcreteSubject;
import com.theradiary.ispwtheradiary.model.Request;
import com.theradiary.ispwtheradiary.engineering.others.beans.PatientBean;
import com.theradiary.ispwtheradiary.engineering.others.beans.PsychologistBean;
import com.theradiary.ispwtheradiary.engineering.others.beans.RequestBean;
import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class RequestGUI extends CommonGUI implements Observer {

    /*
        La classe ConcreteObserver:
            implementa l’interfaccia dell’Observer definendo il comportamento in caso di cambio di stato del soggetto osservato
     */

    private final RequestApplicationController requestApplicationController = new RequestApplicationController();

    private final RequestManagerConcreteSubject requestManagerConcreteSubject;
    private List<RequestBean> requestBeans = new ArrayList<>();
    public RequestGUI(FXMLPathConfig fxmlPathConfig, Session session) {
        super(fxmlPathConfig, session);
        // Recupero l'istanza del ConcreteSubject
        this.requestManagerConcreteSubject = RequestManagerConcreteSubject.getInstance();
    }

    @FXML
    private TableView<RequestBean> requestBeanTableView;
    @FXML
    private TableColumn<RequestBean, String> fullName;
    @FXML
    private TableColumn<RequestBean, String> cityName;
    @FXML
    private TableColumn<RequestBean, LocalDate> requestDate;
    @FXML
    private TableColumn<RequestBean, String> modality;
    @FXML
    private TableColumn<RequestBean, Void> acceptButton;
    @FXML
    private TableColumn<RequestBean, Void> rejectButton;


    // Metodo per inizializzare l'observer
    public void initializeObserver() {
        requestManagerConcreteSubject.addObserver(this); // Aggiungi l'osservatore
    }


    // Metodo per creare la cella con il bottone
    private TableCell<RequestBean, Void> createButtonCell(String buttonText, boolean isAccept) {
        return new TableCell<>() {
            private final Button button = createButton(buttonText, isAccept);
            private Button createButton(String buttonText, boolean isAccept) {
                Button btn = new Button(buttonText);
                btn.setOnMouseClicked(event -> {
                    RequestBean requestBean = getTableView().getItems().get(getIndex());
                    manageRequest(requestBean, isAccept);
                });
                return btn;
            }


            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    setGraphic(button);
                }
            }
        };
    }

    //Metodo per caricare le richieste nella tabella
    @FXML
    public void loadRequest(List<RequestBean> requestBeansParam){
        //Imposto valori della tabella
        fullName.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getPatientBean().getFullName()));
        cityName.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getPatientBean().getCity()));
        requestDate.setCellValueFactory(new PropertyValueFactory<>("date"));
        modality.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getPatientBean().getModality()));
        //Aggiungo i nuovi elementi
        requestBeans.addAll(requestBeansParam);
        refreshTableView();
        // Configura il bottone per accettare la richiesta
        acceptButton.setCellFactory(param -> createButtonCell("Accetta", true));
        // Configura il bottone per rifiutare la richiesta
        rejectButton.setCellFactory(param -> createButtonCell("Rifiuta", false));
    }

    //Metodo per eliminare la richiesta dalla lista. Se accettata, crea l'associazione psicologo-paziente.
    private void manageRequest(RequestBean requestBean, boolean flag) {
        //chiamo applicativo passandogli la richiesta da rimuovere
        requestApplicationController.deleteRequest(requestBean);
        if(flag){
            PatientBean patientBean = requestBean.getPatientBean();
            patientBean.setPsychologistBean(requestBean.getPsychologistBean());
            requestApplicationController.addPsychologistToPatient(patientBean);   //assegno lo psicologo al paziente
            ((PsychologistBean)session.getUser()).getPatientsBean().add(patientBean);    //aggiungo il paziente alla lista dei pazienti dello psicologo
        }
    }

    //Pattern observer

   @Override
    public void update() {
        // Restituisce la lista aggiornata delle richieste
        List<Request> requests = requestManagerConcreteSubject.getRequests();
        // Aggiorna la lista dei RequestBean
        requestBeans.clear();
        for (Request request : requests) {
            requestBeans.add(requestApplicationController.createRequestBean(request));
        }
        refreshTableView();
    }

    private void refreshTableView() {
        requestBeanTableView.getItems().clear();
        requestBeanTableView.getItems().addAll(requestBeans);
    }

}
