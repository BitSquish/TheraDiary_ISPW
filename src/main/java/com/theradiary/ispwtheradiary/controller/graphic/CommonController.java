package com.theradiary.ispwtheradiary.controller.graphic;

import com.theradiary.ispwtheradiary.controller.application.Account;
import com.theradiary.ispwtheradiary.controller.graphic.account.PatientAccountController;
import com.theradiary.ispwtheradiary.controller.graphic.account.PsychologistAccountController;
import com.theradiary.ispwtheradiary.controller.graphic.homepage.HomepageController;
import com.theradiary.ispwtheradiary.controller.graphic.homepage.HomepagePsController;
import com.theradiary.ispwtheradiary.controller.graphic.homepage.HomepagePtController;
import com.theradiary.ispwtheradiary.controller.graphic.login.LoginController;
import com.theradiary.ispwtheradiary.controller.graphic.task.DiaryAndTasksController;
import com.theradiary.ispwtheradiary.engineering.enums.Role;
import com.theradiary.ispwtheradiary.engineering.others.FXMLPathConfig;
import com.theradiary.ispwtheradiary.engineering.others.Session;
import com.theradiary.ispwtheradiary.engineering.others.beans.PatientBean;
import com.theradiary.ispwtheradiary.engineering.others.beans.PsychologistBean;
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
import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.util.List;

public abstract class CommonController {
    protected Session session;
    protected FXMLPathConfig fxmlPathConfig; // Nuovo campo per gestire i percorsi


    protected CommonController(FXMLPathConfig fxmlPathConfig,Session session){
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
    protected static final String APPOINTMENT_PATH = "APPOINTMENT_PATH";
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
                loader.setControllerFactory(c -> new HomepageController(fxmlPathConfig, session));
            } else if (session.getUser().getCredentialsBean().getRole().equals(Role.PATIENT)) {
                loader = new FXMLLoader(getClass().getResource(fxmlPathConfig.getFXMLPath(HOMEPAGE_LOGGED_PT_PATH)));
                loader.setControllerFactory(c -> new HomepagePtController(fxmlPathConfig, session));
            } else {
                loader = new FXMLLoader(getClass().getResource(fxmlPathConfig.getFXMLPath(HOMEPAGE_LOGGED_PS_PATH)));
                loader.setControllerFactory(c -> new HomepagePsController(fxmlPathConfig, session));
            }
            Parent root = loader.load();
            changeScene(root, event);
        } catch (IOException e) {
            throw new RuntimeException(e);
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
                loader.setControllerFactory(c -> new PatientAccountController(fxmlPathConfig, session));
                root = loader.load();
                ((PatientAccountController) loader.getController()).initializeCategories();
            } else {
                loader = new FXMLLoader(getClass().getResource(fxmlPathConfig.getFXMLPath(PSYCHOLOGIST_ACCOUNT_PATH)));
                loader.setControllerFactory(c -> new PsychologistAccountController(fxmlPathConfig, session));
                root = loader.load();
                ((PsychologistAccountController) loader.getController()).initializeMajors();
            }
            changeScene(root, event);
        } catch (IOException e) {
            throw new RuntimeException(e);
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
                loader.setControllerFactory(c -> new DiaryAndTasksController(fxmlPathConfig, session));
                root = loader.load();
                changeScene(root, event);
            } else {
                goToPatientList(event);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
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
                List<PatientBean> patientBeans = new Account().retrievePatientList(psychologistBean);
                loader = new FXMLLoader(getClass().getResource(fxmlPathConfig.getFXMLPath(PATIENT_LIST_PATH)));
                loader.setControllerFactory(c -> new PatientListController(fxmlPathConfig, session));
                Parent root = loader.load();
                ((PatientListController) loader.getController()).printPatient(event, patientBeans);
                changeScene(root, event);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
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
                loader.setControllerFactory(c -> new DashboardController(fxmlPathConfig, session));
            }
            Parent root = loader.load();
            changeScene(root, event);
        } catch (IOException e) {
            throw new RuntimeException(e);
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
                loader.setControllerFactory(c -> new PAGController(fxmlPathConfig, session));
            }
            Parent root = loader.load();
            changeScene(root, event);
        } catch (IOException e) {
            throw new RuntimeException(e);
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
                loader.setControllerFactory(c -> new SearchController(fxmlPathConfig, session));
            } else {
                loader = new FXMLLoader(getClass().getResource(fxmlPathConfig.getFXMLPath(MEDICAL_OFFICE_PATH)));
                loader.setControllerFactory(c -> new MedicalOfficeController(fxmlPathConfig, session));
                Parent root = loader.load();
                ((MedicalOfficeController) loader.getController()).initializeTextFields();
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
            if (session.getUser() == null) {
                goToLogin(event);
            }else {
                FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPathConfig.getFXMLPath(APPOINTMENT_PATH)));
                loader.setControllerFactory(c -> new AppointmentController(fxmlPathConfig, session));
                Parent root = loader.load();
                changeScene(root, event);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void goToLogin(MouseEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPathConfig.getFXMLPath(LOGIN_PATH)));
            loader.setControllerFactory(c -> new LoginController(fxmlPathConfig, session));
            Parent root = loader.load();
            changeScene(root, event);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    //Metodo per cambiare finestra
    protected void changeScene(Parent root, MouseEvent event) {
        Scene scene = new Scene(root);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }


}