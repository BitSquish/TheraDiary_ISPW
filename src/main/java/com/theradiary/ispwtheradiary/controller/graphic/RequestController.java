package com.theradiary.ispwtheradiary.controller.graphic;

import com.theradiary.ispwtheradiary.controller.application.RequestApplication;
import com.theradiary.ispwtheradiary.controller.graphic.login.LoginController;
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
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class RequestController extends CommonController implements Observer {

    /*
        La classe ConcreteObserver:
            implementa l’interfaccia dell’Observer definendo il comportamento in caso di cambio di stato del soggetto osservato
     */

    public RequestController(FXMLPathConfig fxmlPathConfig, Session session) {
        super(fxmlPathConfig,session);
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


    private RequestManagerConcreteSubject requestManagerConcreteSubject;
    private List<RequestBean> requestBeans = new ArrayList<>();


    @FXML
    protected void back(MouseEvent event) {
        try {
            FXMLLoader loader;
            Parent root;
            // Verifica se l'utente è loggato
            if(session.getUser() == null) {
                // Se non c'è un utente loggato, carica la schermata di login
                loader = new FXMLLoader(getClass().getResource(fxmlPathConfig.getFXMLPath(LOGIN_PATH)));
                loader.setControllerFactory(c -> new LoginController(fxmlPathConfig, session)); // Imposta il controller per la login
                root = loader.load();
            } else {
                // Se l'utente è loggato, carica la schermata dei pazienti
                loader = new FXMLLoader(getClass().getResource(fxmlPathConfig.getFXMLPath(PATIENT_LIST_PATH)));
                loader.setControllerFactory(c -> new PatientListController(fxmlPathConfig, session));
                root = loader.load();
                ((PatientListController) loader.getController()).printPatient(event, ((PsychologistBean)session.getUser()).getPatientsBean());
            }
            // Carica e cambia scena
            changeScene(root, event);

        } catch (IOException e) {
            e.printStackTrace();
            // Aggiungi un messaggio di errore personalizzato
            throw new RuntimeException("Errore nel caricamento della scena: " + e.getMessage(), e);
        }
    }

    public void loadRequest(List<RequestBean> requestBeans){
        //Istanza del concreteSubject
        requestManagerConcreteSubject = RequestManagerConcreteSubject.getInstance();
        requestManagerConcreteSubject.addObserver(this);
        //Imposto valori della tabella
        fullName.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getPatientBean().getFullName()));
        cityName.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getPatientBean().getCity()));
        requestDate.setCellValueFactory(new PropertyValueFactory<>("date"));
        modality.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getPatientBean().getModality()));
        //Aggiungo i nuovi elementi
        requestBeanTableView.getItems().clear();    //Svuota la tableview
        requestBeanTableView.getItems().addAll(requestBeans);
        //Bottone per accettare la richiesta
        acceptButton.setCellFactory(param -> new TableCell<>() {
            private final Button btnAccept = new Button("Accetta");
            {
                btnAccept.setOnMouseClicked(event -> {
                    RequestBean requestBean = getTableView().getItems().get(getIndex());
                    manageRequest(requestBean, true);
                });
            }
            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    setGraphic(btnAccept);
                }
            }
        });
        //Bottone per rifiutare la richiesta
        rejectButton.setCellFactory(param -> new TableCell<>() {
            private final Button btnReject = new Button("Rifiuta");
            {
                btnReject.setOnMouseClicked(event -> {
                    RequestBean requestBean = getTableView().getItems().get(getIndex());
                    manageRequest(requestBean, false);
                });
            }
            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    setGraphic(btnReject);
                }
            }
        });
    }

    private void manageRequest(RequestBean requestBean, boolean flag) {
        //chiamo applicativo passandogli la richiesta da rimuovere
        RequestApplication requestApplication = new RequestApplication();
        requestApplication.deleteRequest(requestBean);
        if(flag){
            PatientBean patientBean = requestBean.getPatientBean();
            patientBean.setPsychologistBean(requestBean.getPsychologistBean());
            requestApplication.addPsychologistToPatient(patientBean);   //assegno lo psicologo al paziente
            ((PsychologistBean)session.getUser()).getPatientsBean().add(patientBean);    //aggiungo il paziente alla lista dei pazienti dello psicologo
        }   //Servirebbe un refresh della tabella TODO

        //valuta se aggiungere un messaggio di rifiuto richiesta

    }


    @Override
    public void update() {
        List <Request>requests = requestManagerConcreteSubject.getRequests();
        for(Request request:requests) {
            RequestBean requestBean = request.toBean();
            requestBeans.add(requestBean);
        }
        //Aggiungo i nuovi elementi
        requestBeanTableView.getItems().clear();    //Svuota la tableview
        requestBeanTableView.getItems().addAll(requestBeans);
    }
}
