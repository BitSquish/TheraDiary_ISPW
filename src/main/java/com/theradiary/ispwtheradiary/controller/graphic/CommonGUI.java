package com.theradiary.ispwtheradiary.controller.graphic;

import com.theradiary.ispwtheradiary.controller.application.AccountController;
import com.theradiary.ispwtheradiary.controller.graphic.account.PatientAccountGUI;
import com.theradiary.ispwtheradiary.controller.graphic.account.PsychologistAccountGUI;
import com.theradiary.ispwtheradiary.controller.graphic.homepage.HomepageGUI;
import com.theradiary.ispwtheradiary.controller.graphic.homepage.HomepagePsGUI;
import com.theradiary.ispwtheradiary.controller.graphic.homepage.HomepagePtGUI;
import com.theradiary.ispwtheradiary.controller.graphic.login.LoginGUI;
import com.theradiary.ispwtheradiary.controller.graphic.task.DiaryAndTasksGUI;
import com.theradiary.ispwtheradiary.controller.graphic.task.DiaryControllerGUI;
import com.theradiary.ispwtheradiary.engineering.enums.Role;
import com.theradiary.ispwtheradiary.engineering.exceptions.SceneLoadingException;
import com.theradiary.ispwtheradiary.engineering.others.FXMLPathConfig;
import com.theradiary.ispwtheradiary.engineering.others.Session;
import com.theradiary.ispwtheradiary.engineering.others.beans.PatientBean;
import com.theradiary.ispwtheradiary.engineering.others.beans.PsychologistBean;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextInputDialog;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Line;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public abstract class CommonGUI {
    protected Session session;
    protected FXMLPathConfig fxmlPathConfig; // Nuovo campo per gestire i percorsi


    protected CommonGUI(FXMLPathConfig fxmlPathConfig, Session session){
        this.fxmlPathConfig = fxmlPathConfig;
        this.session = session;
    }
    protected static final String LOGIN_PATH = "LOGIN_PATH";
    protected static final String HOMEPAGE_NOT_LOGGED_PATH = "HOMEPAGE_NOT_LOGGED_PATH";
    protected static final String HOMEPAGE_LOGGED_PT_PATH = "HOMEPAGE_LOGGED_PT_PATH";
    protected static final String HOMEPAGE_LOGGED_PS_PATH = "HOMEPAGE_LOGGED_PS_PATH";
    protected static final String PATIENT_ACCOUNT_PATH = "PATIENT_ACCOUNT_PATH";
    protected static final String PSYCHOLOGIST_ACCOUNT_PATH = "PSYCHOLOGIST_ACCOUNT_PATH";
    protected static final String DIARY_AND_TASKS_PATH = "DIARY_AND_TASKS_PATH";
    protected static final String PATIENT_LIST_PATH = "PATIENT_LIST_PATH";
    protected static final String DASHBOARD_PATH = "DASHBOARD_PATH";
    protected static final String PAG_PATH = "PAG_PATH";
    protected static final String SEARCH_PATH = "SEARCH_PATH";
    protected static final String MEDICAL_OFFICE_PATH = "MEDICAL_OFFICE_PATH";
    protected static final String APPOINTMENTPS_PATH = "APPOINTMENTPS_PATH";
    protected static final String APPOINTMENTPT_PATH = "APPOINTMENTPT_PATH";
    protected static final String PATIENT_TASK_PATH = "PATIENT_TASK_PATH";
    protected static final String REQUEST_PATH = "REQUEST_PATH";
    protected static final String PATIENT_PROFILE_PATH = "PATIENT_PROFILE_PATH";
    protected static final String PSYCHOLOGIST_REGISTRATION_PATH = "PSYCHOLOGIST_REGISTRATION_PATH";
    protected static final String PATIENT_REGISTRATION_PATH = "PATIENT_REGISTRATION_PATH";
    protected static final String MODIFY_PATIENT_PATH = "MODIFY_PATIENT_PATH";
    protected static final String MODIFY_PSYCHOLOGIST_PATH = "MODIFY_PSYCHOLOGIST_PATH";
    protected static final String PSYCHOLOGIST_DESCRIPTION_PATH = "PSYCHOLOGIST_DESCRIPTION_PATH";
    protected static final String PATIENT_DESCRIPTION_PATH = "PATIENT_DESCRIPTION_PATH";
    protected static final String PSYCHOLOGIST_LIST_PATH = "PSYCHOLOGIST_LIST_PATH";
    protected static final String DIARY_PATH="DIARY_PATH";
    protected static final String DIARY_PAGE_PATH="DIARY_PAGE_PATH";
    protected static final String TODO_PATH="TODO_PATH";
    protected static final String LIST_TASK_PATIENT_PATH="LIST_TASK_PATIENT_PATH";
    public static final String LOADING_SCENE ="Errore durante il caricamento della scena";



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
    protected void goToHomepage(MouseEvent event) {
        try {
            FXMLLoader loader;
            if (session.getUser() == null) {
                loader = new FXMLLoader(getClass().getResource(fxmlPathConfig.getFXMLPath(HOMEPAGE_NOT_LOGGED_PATH)));
                loader.setControllerFactory(c -> new HomepageGUI(fxmlPathConfig, session));
            } else if (session.getUser().getCredentialsBean().getRole().equals(Role.PATIENT)) {
                loader = new FXMLLoader(getClass().getResource(fxmlPathConfig.getFXMLPath(HOMEPAGE_LOGGED_PT_PATH)));
                loader.setControllerFactory(c -> new HomepagePtGUI(fxmlPathConfig, session));
            } else {
                loader = new FXMLLoader(getClass().getResource(fxmlPathConfig.getFXMLPath(HOMEPAGE_LOGGED_PS_PATH)));
                loader.setControllerFactory(c -> new HomepagePsGUI(fxmlPathConfig, session));
            }
            Parent root = loader.load();
            changeScene(root, event);
        } catch (IOException e) {
            throw new SceneLoadingException(LOADING_SCENE, e);
        }
    }

    @FXML
    protected void goToAccountPage(MouseEvent event) {
        try {
            FXMLLoader loader;
            Parent root;
            if (session.getUser() == null) {
                goToLogin(event);
                return;
            } else if (session.getUser().getCredentialsBean().getRole().equals(Role.PATIENT)) {
                loader = new FXMLLoader(getClass().getResource(fxmlPathConfig.getFXMLPath(PATIENT_ACCOUNT_PATH)));
                loader.setControllerFactory(c -> new PatientAccountGUI(fxmlPathConfig, session));
                root = loader.load();
                ((PatientAccountGUI) loader.getController()).initializeCategories();
            } else {
                loader = new FXMLLoader(getClass().getResource(fxmlPathConfig.getFXMLPath(PSYCHOLOGIST_ACCOUNT_PATH)));
                loader.setControllerFactory(c -> new PsychologistAccountGUI(fxmlPathConfig, session));
                root = loader.load();
                ((PsychologistAccountGUI) loader.getController()).initializeMajors();
            }
            changeScene(root, event);
        } catch (IOException e) {
            throw new SceneLoadingException(LOADING_SCENE, e);
        }
    }

    @FXML
    private void goToTasks(MouseEvent event) {
        try {
            FXMLLoader loader;
            Parent root;
            if (session.getUser() == null) {
                goToLogin(event);
            } else if (session.getUser().getCredentialsBean().getRole().equals(Role.PATIENT)) {
                loader = new FXMLLoader(getClass().getResource(fxmlPathConfig.getFXMLPath(DIARY_AND_TASKS_PATH)));
                loader.setControllerFactory(c -> new DiaryAndTasksGUI(fxmlPathConfig, session));
                root = loader.load();
                changeScene(root, event);
            } else {
                goToPatientList(event);
            }
        } catch (IOException e) {
            throw new SceneLoadingException(LOADING_SCENE, e);
        }
    }

    @FXML
    protected void goToPatientList(MouseEvent event){
        try {
            FXMLLoader loader;
            if (session.getUser() == null) {
                goToLogin(event);
            } else {
                PsychologistBean psychologistBean = (PsychologistBean) session.getUser();
                List<PatientBean> patientBeans = new AccountController().retrievePatientList(psychologistBean);
                loader = new FXMLLoader(getClass().getResource(fxmlPathConfig.getFXMLPath(PATIENT_LIST_PATH)));
                loader.setControllerFactory(c -> new PatientListGUI(fxmlPathConfig, session));
                Parent root = loader.load();
                ((PatientListGUI) loader.getController()).printPatient(event, patientBeans);
                changeScene(root, event);
            }
        } catch (IOException e) {
            throw new SceneLoadingException(LOADING_SCENE, e);
        }
    }

    @FXML
    private void goToDashboard(MouseEvent event) {
        try {
            FXMLLoader loader;
            if (session.getUser() == null) {
                goToLogin(event);
                return;
            } else {
                loader = new FXMLLoader(getClass().getResource(fxmlPathConfig.getFXMLPath(DASHBOARD_PATH)));
                loader.setControllerFactory(c -> new DashboardGUI(fxmlPathConfig, session));
            }
            Parent root = loader.load();
            changeScene(root, event);
        } catch (IOException e) {
            throw new SceneLoadingException(LOADING_SCENE, e);
        }
    }

    @FXML
    private void goToPAG(MouseEvent event) {
        try {
            FXMLLoader loader;
            if (session.getUser() == null) {
                goToLogin(event);
                return;
            } else {
                loader = new FXMLLoader(getClass().getResource(fxmlPathConfig.getFXMLPath(PAG_PATH)));
                loader.setControllerFactory(c -> new PagGUI(fxmlPathConfig, session));
            }
            Parent root = loader.load();
            changeScene(root, event);
        } catch (IOException e) {
            throw new SceneLoadingException(LOADING_SCENE, e);
        }
    }

    @FXML
    protected void goToSearch(MouseEvent event) {
        try {
            FXMLLoader loader;
            if (session.getUser() == null) {
                goToLogin(event);
                return;
            }else if (session.getUser().getCredentialsBean().getRole().toString().equals("PATIENT")) {
                loader = new FXMLLoader(getClass().getResource(fxmlPathConfig.getFXMLPath(SEARCH_PATH)));
                loader.setControllerFactory(c -> new SearchGUI(fxmlPathConfig, session));
            } else {
                loader = new FXMLLoader(getClass().getResource(fxmlPathConfig.getFXMLPath(MEDICAL_OFFICE_PATH)));
                loader.setControllerFactory(c -> new MedicalOfficeGUI(fxmlPathConfig, session));
                Parent root = loader.load();
                ((MedicalOfficeGUI) loader.getController()).initializeTextFields();
                changeScene(root, event);
                return;
            }
            Parent root = loader.load();
            changeScene(root, event);
        } catch (IOException | SQLException e) {
            throw new SceneLoadingException(LOADING_SCENE, e);
        }
    }

    @FXML
    private void goToAppointment(MouseEvent event) {
        try {
            if (session.getUser() == null) {
                goToLogin(event);
            }else if(session.getUser().getCredentialsBean().getRole().equals(Role.PSYCHOLOGIST)){
                FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPathConfig.getFXMLPath(APPOINTMENTPS_PATH)));
                loader.setControllerFactory(c -> new AppointmentPsGUI(fxmlPathConfig, session));
                Parent root = loader.load();
                ((AppointmentPsGUI)loader.getController()).getAllAppointments();
                changeScene(root, event);
            }else{
                FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPathConfig.getFXMLPath(APPOINTMENTPT_PATH)));
                loader.setControllerFactory(c -> new AppointmentPtGUI(fxmlPathConfig, session));
                Parent root = loader.load();
                changeScene(root, event);
            }
        } catch (IOException e) {
            throw new SceneLoadingException(LOADING_SCENE, e);
        }
    }
    @FXML
    private void goToLogin(MouseEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPathConfig.getFXMLPath(LOGIN_PATH)));
            loader.setControllerFactory(c -> new LoginGUI(fxmlPathConfig, session));
            Parent root = loader.load();
            changeScene(root, event);
        } catch (IOException e) {
            throw new SceneLoadingException(LOADING_SCENE, e);
        }
    }
    @FXML
    protected void goToDiary(MouseEvent event ) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPathConfig.getFXMLPath(DIARY_PATH)));
            loader.setControllerFactory(c -> new DiaryControllerGUI(fxmlPathConfig, session));
            Parent root = loader.load();
            ((DiaryControllerGUI) loader.getController()).initializeDiary((PatientBean) session.getUser());
            changeScene(root, event);
        } catch (IOException e) {
            // Rilancio di un'eccezione più descrittiva
            throw new SceneLoadingException(LOADING_SCENE, e);


        }
    }

    //Metodo per cambiare finestra
    protected void changeScene(Parent root, MouseEvent event) {
        Scene scene = new Scene(root);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }
    //POP UP
    public void showMessage(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
    //INSERIRE
    public String showInputDialog(String title, String content, String defaultValue) {
        TextInputDialog dialog = new TextInputDialog(defaultValue);
        dialog.setTitle(title);
        dialog.setHeaderText(null);
        dialog.setContentText(content);
        Optional<String> result=dialog.showAndWait();
        return result.orElse(null);
    }


}