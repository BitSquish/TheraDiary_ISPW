package com.theradiary.ispwtheradiary.controller.graphic;

import com.theradiary.ispwtheradiary.controller.application.Account;
import com.theradiary.ispwtheradiary.controller.graphic.account.PatientAccountController;
import com.theradiary.ispwtheradiary.controller.graphic.account.PsychologistAccountController;
import com.theradiary.ispwtheradiary.controller.graphic.homepage.HomepageController;
import com.theradiary.ispwtheradiary.controller.graphic.homepage.HomepagePsController;
import com.theradiary.ispwtheradiary.controller.graphic.homepage.HomepagePtController;
import com.theradiary.ispwtheradiary.controller.graphic.login.LoginController;
import com.theradiary.ispwtheradiary.engineering.enums.Role;
import com.theradiary.ispwtheradiary.engineering.others.Session;
import com.theradiary.ispwtheradiary.model.beans.PatientBean;
import com.theradiary.ispwtheradiary.model.beans.PsychologistBean;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Line;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public abstract class CommonController {
    protected Session session;

    protected CommonController(Session session){
        this.session = session;
    }


    @FXML
    private Line line1;

    @FXML
    private Label contattiLabel;

    @FXML
    private ImageView mail1;

    @FXML
    private ImageView stars1;

    @FXML
    private Label recensioneLabel;

    @FXML
    private ImageView faq1;

    @FXML
    private Label faqLabel;
    @FXML
    protected static final String LOGIN_PATH = "/com/theradiary/ispwtheradiary/view/Login.fxml";
    @FXML
    protected static final String HOMEPAGE_NOT_LOGGED_PATH = "/com/theradiary/ispwtheradiary/view/HomepageNotLogged.fxml";
    @FXML
    protected static final String HOMEPAGE_LOGGED_PT_PATH = "/com/theradiary/ispwtheradiary/view/HomepageLoggedPt.fxml";
    @FXML
    protected static final String HOMEPAGE_LOGGED_PS_PATH = "/com/theradiary/ispwtheradiary/view/HomepageLoggedPs.fxml";
    @FXML
    protected static final String PATIENT_ACCOUNT_PATH = "/com/theradiary/ispwtheradiary/view/PatientAccount.fxml";
    @FXML
    protected static final String PSYCHOLOGIST_ACCOUNT_PATH = "/com/theradiary/ispwtheradiary/view/PsychologistAccount.fxml";
    @FXML
    protected static final String DIARY_AND_TASKS_PATH = "/com/theradiary/ispwtheradiary/view/DiaryAndTasks.fxml";
    @FXML
    protected static final String DASHBOARD_PATH = "/com/theradiary/ispwtheradiary/view/Dashboard.fxml";
    @FXML
    protected static final String PAG_PATH = "/com/theradiary/ispwtheradiary/view/PAG.fxml";
    @FXML
    protected static final String SEARCH_PATH = "/com/theradiary/ispwtheradiary/view/Search.fxml";
    @FXML
    protected static final String MEDICAL_OFFICE_PATH = "/com/theradiary/ispwtheradiary/view/MedicalOffice.fxml";
    @FXML
    protected static final String APPOINTMENT_PATH = "/com/theradiary/ispwtheradiary/view/Appointment.fxml";
    @FXML
    protected static final String PATIENT_PROFILE_PATH = "/com/theradiary/ispwtheradiary/view/PatientProfile.fxml";
    @FXML
    protected static final String PATIENT_LIST_PATH = "/com/theradiary/ispwtheradiary/view/PatientList.fxml";
    @FXML
    protected static final String PATIENT_LIST = "/com/theradiary/ispwtheradiary/view/PatientList.fxml";
    @FXML
    protected static final String PSYCHOLOGIST_LIST_PATH="/com/theradiary/ispwtheradiary/view/PsychologistList.fxml";


    @FXML
    protected void goToHomepage(MouseEvent event){
        try{
            FXMLLoader loader;
            if(session.getUser() == null){
                // Carica la nuova pagina FXML
                loader = new FXMLLoader(getClass().getResource(HOMEPAGE_NOT_LOGGED_PATH));
                // Imposta il controller per la nuova pagina
                loader.setControllerFactory(c -> new HomepageController(session));
            }
            else if(session.getUser().getCredentialsBean().getRole().equals(Role.PATIENT)){
                // Carica la nuova pagina FXML
                loader = new FXMLLoader(getClass().getResource(HOMEPAGE_LOGGED_PT_PATH));
                // Imposta il controller per la nuova pagina
                loader.setControllerFactory(c -> new HomepagePtController(session));
            }
            else{
                // Carica la nuova pagina FXML
                loader = new FXMLLoader(getClass().getResource(HOMEPAGE_LOGGED_PS_PATH));
                // Imposta il controller per la nuova pagina
                loader.setControllerFactory(c -> new HomepagePsController(session));
            }
            Parent root = loader.load();
            changeScene(root, event);
        }catch(IOException e){
            throw new RuntimeException(e.getMessage());
        }
    }


    @FXML
    protected void goToAccountPage(MouseEvent event) {
        try{
            FXMLLoader loader;
            Parent root;
            if(session.getUser() == null){
                // Carica la nuova pagina FXML
                loader = new FXMLLoader(getClass().getResource(LOGIN_PATH));
                // Imposta il controller per la nuova pagina
                loader.setControllerFactory(c -> new LoginController(session));
                root = loader.load();
            }
            else if(session.getUser().getCredentialsBean().getRole().equals(Role.PATIENT)){
                loader = new FXMLLoader(getClass().getResource(PATIENT_ACCOUNT_PATH));
                loader.setControllerFactory(c -> new PatientAccountController(session));
                root = loader.load();
                ((PatientAccountController)loader.getController()).initializeCategories();
            }
            else{
                loader = new FXMLLoader(getClass().getResource(PSYCHOLOGIST_ACCOUNT_PATH));
                loader.setControllerFactory(c -> new PsychologistAccountController(session));
                root = loader.load();
                ((PsychologistAccountController)loader.getController()).initializeMajors();
            }
            changeScene(root, event);
        }catch(IOException e){
            throw new RuntimeException(e);
        }
    }

    @FXML
    private void goToTasks(MouseEvent event){
        try {
            FXMLLoader loader;
            Parent root;
            if(session.getUser()==null){
                loader = new FXMLLoader(getClass().getResource(LOGIN_PATH));
                loader.setControllerFactory(c -> new LoginController(session));
                root = loader.load();
            } else if (session.getUser().getCredentialsBean().getRole().equals(Role.PATIENT)) {
                loader = new FXMLLoader(getClass().getResource(DIARY_AND_TASKS_PATH));
                loader.setControllerFactory(c -> new DiaryAndTasksController(session));
                root = loader.load();
            }else{
                PsychologistBean psychologistBean = (PsychologistBean) session.getUser();
                //Recupero la lista dei pazienti
                List<PatientBean> patientBeans = new Account().retrievePatientList(psychologistBean);

                loader = new FXMLLoader(getClass().getResource(PATIENT_LIST));
                loader.setControllerFactory(c -> new PatientListController(session));
                root = loader.load();
                ((PatientListController) loader.getController()).printPatient(event, patientBeans);

            }
            changeScene(root, event);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }





    @FXML
    private void goToDashboard(MouseEvent event){
        try {
            FXMLLoader loader;
            if(session.getUser()==null){
                loader = new FXMLLoader(getClass().getResource(LOGIN_PATH));
                loader.setControllerFactory(c -> new LoginController(session));
            }
            else{
                loader = new FXMLLoader(getClass().getResource(DASHBOARD_PATH));
                loader.setControllerFactory(c -> new DashboardController(session));
            }
            Parent root = loader.load();
            changeScene(root, event);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    private void goToPAG(MouseEvent event){//sull'account se clicchi sulla scritta vai a PAG
        try {
            FXMLLoader loader;
            if(session.getUser()==null){
                loader = new FXMLLoader(getClass().getResource(LOGIN_PATH));
                loader.setControllerFactory(c -> new LoginController(session));
            }
            else{
                loader = new FXMLLoader(getClass().getResource(PAG_PATH));
                loader.setControllerFactory(c -> new PAGController(session));
            }
            Parent root = loader.load();
            changeScene(root, event);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    @FXML
    protected void goToSearch(MouseEvent event){
        try {
            FXMLLoader loader;
            if(session.getUser()==null){
                loader = new FXMLLoader(getClass().getResource(LOGIN_PATH));
                loader.setControllerFactory(c -> new LoginController(session));
            }

            else if(session.getUser().getCredentialsBean().getRole().toString().equals("PATIENT")){
                loader = new FXMLLoader(getClass().getResource(SEARCH_PATH));
                loader.setControllerFactory(c -> new SearchController(session));
            }
            else{
                loader = new FXMLLoader(getClass().getResource(MEDICAL_OFFICE_PATH));
                loader.setControllerFactory(c -> new MedicalOfficeController(session));
                Parent root = loader.load();
                ((MedicalOfficeController)loader.getController()).initializeTextFields(); // Chiamata al metodo per caricare i dati
                changeScene(root, event);
                return;
            }
            Parent root = loader.load();
            changeScene(root, event);
        } catch (IOException | SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    private void goToAppointment(MouseEvent event) {
        try {
            FXMLLoader loader;

            // Controllo se l'utente è loggato
            if (session.getUser() == null) {
                // Se l'utente non è loggato, lo reindirizzo alla pagina di login

                // Carico il file FXML per la pagina di login
                loader = new FXMLLoader(getClass().getResource(LOGIN_PATH));
                // Specifico il controller per gestire gli elementi della pagina di login
                loader.setControllerFactory(c -> new LoginController(session));
            } else {
                // Se l'utente è loggato, lo reindirizzo alla pagina degli articoli

                // Carico il file FXML per la pagina degli articoli
                loader = new FXMLLoader(getClass().getResource(APPOINTMENT_PATH));
                // Specifico il controller per gestire gli elementi della pagina degli articoli
                loader.setControllerFactory(c -> new AppointmentController(session));
            }

            // Carico gli elementi grafici dalla radice del file FXML
            Parent root = loader.load();
            // Cambio la scena passando gli elementi grafici e l'evento che ha chiamato questo metodo
            changeScene(root, event);
            // Imposto il colore dell'AnchorPane attivo
        } catch (IOException e) {
            // Lancio un'eccezione runtime se si verifica un errore durante il caricamento del file FXML
            throw new RuntimeException(e);
        }
    }


    protected void changeScene(Parent root, MouseEvent event) {
        Scene scene = new Scene(root);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }



}