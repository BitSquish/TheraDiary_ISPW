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


    private RequestManagerConcreteSubject requestManagerConcreteSubject;
    private List<RequestBean> requestBeans = new ArrayList<>();


    //Torna alla pagina precedente
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

    //Carica le richieste
    /*public void loadRequest(List<RequestBean> requestBeans) {
        setupTableView(); // Configura la tabella
        requestBeanTableView.getItems().clear();
        requestBeanTableView.getItems().addAll(requestBeans);
    }

    private void setupTableView() {
        // Imposta le colonne della tabella
        fullName.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getPatientBean().getFullName()));
        cityName.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getPatientBean().getCity()));
        requestDate.setCellValueFactory(new PropertyValueFactory<>("date"));
        modality.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getPatientBean().getModality()));
        // Bottone per accettare la richiesta
        acceptButton.setCellFactory(param -> new TableCell<>() {
            private final Button btnAccept = new Button("Accetta");
            {
                btnAccept.setOnMouseClicked(event -> {
                    RequestBean requestBean = getTableView().getItems().get(getIndex());
                    try {
                        manageRequest(event, requestBean, true);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
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
        // Bottone per rifiutare la richiesta
        rejectButton.setCellFactory(param -> new TableCell<>() {
            private final Button btnReject = new Button("Rifiuta");
            {
                btnReject.setOnMouseClicked(event -> {
                    RequestBean requestBean = getTableView().getItems().get(getIndex());
                    try {
                        manageRequest(event, requestBean, false);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
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
    }*/

    @FXML
    public void loadRequest(List<RequestBean> requestBeans){
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
                    try {
                        manageRequest(event, requestBean, true);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
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
                    try {
                        manageRequest(event, requestBean, false);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
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

    //Metodo per eliminare la richiesta dalla lista. Se accettata, crea l'associazione psicologo-paziente.
    private void manageRequest(MouseEvent event, RequestBean requestBean, boolean flag) throws IOException {
        //chiamo applicativo passandogli la richiesta da rimuovere
        RequestApplication requestApplication = new RequestApplication();
        requestApplication.deleteRequest(requestBean);
        if(flag){
            PatientBean patientBean = requestBean.getPatientBean();
            patientBean.setPsychologistBean(requestBean.getPsychologistBean());
            requestApplication.addPsychologistToPatient(patientBean);   //assegno lo psicologo al paziente
            ((PsychologistBean)session.getUser()).getPatientsBean().add(patientBean);    //aggiungo il paziente alla lista dei pazienti dello psicologo
        }
        // Rimuove la richiesta dalla lista e notifica la tabella
        requestBeans.remove(requestBean);
        requestBeanTableView.getItems().remove(requestBean); // Sincronizza la tabella
    }

    //Pattern observer
   /* @Override
    public void update() {
        List <Request>requests = requestManagerConcreteSubject.getRequests();
        for(Request request:requests) {
            RequestBean requestBean = request.toBean();
            requestBeans.add(requestBean);
        }
        //Aggiungo i nuovi elementi
        requestBeanTableView.getItems().clear();    //Svuota la tableview
        requestBeanTableView.getItems().addAll(requestBeans);
    }*/

    @Override
    public void update() {
        // Restituisce la lista aggiornata delle richieste
        List<Request> requests = requestManagerConcreteSubject.getRequests();
        // Aggiorna la lista dei RequestBean
        List<RequestBean> updatedRequestBeans = new ArrayList<>();
        for (Request request : requests) {
            updatedRequestBeans.add(request.toBean());
        }
        // Aggiorna solo gli elementi cambiati
        requestBeanTableView.getItems().setAll(updatedRequestBeans);
    }
}
