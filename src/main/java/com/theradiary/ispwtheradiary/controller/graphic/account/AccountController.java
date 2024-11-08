package com.theradiary.ispwtheradiary.controller.graphic.account;


import com.theradiary.ispwtheradiary.controller.application.Account;
import com.theradiary.ispwtheradiary.controller.graphic.CommonController;
import com.theradiary.ispwtheradiary.controller.graphic.homepage.HomepagePsController;
import com.theradiary.ispwtheradiary.controller.graphic.homepage.HomepagePtController;
import com.theradiary.ispwtheradiary.controller.graphic.login.LoginController;
import com.theradiary.ispwtheradiary.controller.graphic.modify.ModifyPatientController;
import com.theradiary.ispwtheradiary.controller.graphic.modify.ModifyPsychologistController;
import com.theradiary.ispwtheradiary.engineering.enums.Category;
import com.theradiary.ispwtheradiary.engineering.enums.Major;
import com.theradiary.ispwtheradiary.engineering.enums.Role;
import com.theradiary.ispwtheradiary.engineering.others.Session;
import com.theradiary.ispwtheradiary.model.beans.LoggedUserBean;
import com.theradiary.ispwtheradiary.model.beans.PatientBean;
import com.theradiary.ispwtheradiary.model.beans.PsychologistBean;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Button;

import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public abstract class AccountController extends CommonController {

    protected AccountController(Session session) {
        super(session);
    }

    @FXML
    ImageView photo;
    @FXML
    Label errorMessage;
    @FXML
    Label successMessage;
    @FXML
    CheckBox checkbox1;
    @FXML
    CheckBox checkbox2;
    @FXML
    CheckBox checkbox3;
    @FXML
    CheckBox checkbox4;
    @FXML
    CheckBox checkbox5;
    @FXML
    CheckBox checkbox6;
    @FXML
    CheckBox checkbox7;
    @FXML
    CheckBox checkbox8;
    @FXML
    CheckBox checkbox9;




    // Metodo astratto per recuperare le categorie o majors
    protected abstract void retrieveData(Account account, LoggedUserBean loggedUserBean);

    // Metodo astratto per ottenere le categorie o majors dal bean utente
    protected abstract Iterable<?> getItems(LoggedUserBean loggedUserBean);

    @FXML
    protected void initializeItems(LoggedUserBean loggedUserBean) {
        CheckBox[] checkBoxes = new CheckBox[]{
                checkbox1, checkbox2, checkbox3, checkbox4, checkbox5,
                checkbox6, checkbox7, checkbox8, checkbox9
        };

        // Recupera i dati necessari per l'utente (categorie o majors)
        retrieveData(new Account(), loggedUserBean);

        // Ottieni le categorie o majors dall'oggetto `LoggedUserBean`
        Iterable<?> items = getItems(loggedUserBean);
        if (items != null) {
            // Itera sugli elementi (categorie o majors) e seleziona le checkbox corrispondenti
            for (Object item : items) {
                int id = (loggedUserBean.getCredentialsBean().getRole().equals(Role.PATIENT)) ? ((Category) item).getId() : ((Major) item).getId();
                if (id > 0 && id <= checkBoxes.length) {
                    checkBoxes[id - 1].setSelected(true);
                }
            }
        }
    }

    @FXML
    protected void updateData() {

        // Reset messaggi di errore/successo
        errorMessage.setVisible(false);
        successMessage.setVisible(false);

        // Creazione dell'array di CheckBox
        CheckBox[] checkboxes = new CheckBox[]{
                checkbox1, checkbox2, checkbox3, checkbox4, checkbox5,
                checkbox6, checkbox7, checkbox8, checkbox9
        };

        // Recupera il bean utente loggato
        LoggedUserBean loggedUserBean = session.getUser();
        boolean isPatient = loggedUserBean.getCredentialsBean().getRole().equals(Role.PATIENT);

        // Ottieni le categorie o majors attuali
        Iterable<?> currentItems = getItems(loggedUserBean);
        Set<Integer> currentIds = new HashSet<>();

        // Usa un Set per evitare duplicati e rendere più efficiente la ricerca
        if (currentItems != null) {
            for (Object item : currentItems) {
                int id = isPatient ? ((Category) item).getId() : ((Major) item).getId();
                currentIds.add(id);
            }
        }

        boolean modified = false;

        try {
            // Gestione degli aggiornamenti in un'unica lista
            for (int i = 0; i < checkboxes.length; i++) {
                int id = i + 1;
                boolean isSelected = checkboxes[i].isSelected();
                Object item = isPatient ? Category.convertIntToCategory(id) : Major.convertIntToMajor(id);

                if (isSelected && !currentIds.contains(id)) {
                    // Aggiungi l'elemento se è selezionato ma non è già presente
                    if (isPatient) {
                        PatientAccountController.addCategory((PatientBean) loggedUserBean, (Category) item);
                    } else {
                        PsychologistAccountController.addMajor((PsychologistBean) loggedUserBean, (Major) item);
                    }
                    modified = true;
                } else if (!isSelected && currentIds.contains(id)) {
                    // Rimuovi l'elemento se non è selezionato ma è già presente
                    if (isPatient) {
                        PatientAccountController.removeCategory((PatientBean) loggedUserBean,(Category) item);
                    } else {
                        PsychologistAccountController.removeMajor((PsychologistBean) loggedUserBean, (Major) item);
                    }
                    modified = true;
                }
            }

            // Mostra messaggi di successo o errore
            if (modified) {
                successMessage.setVisible(true);
            } else {
                errorMessage.setVisible(true);
            }

        } catch (Exception e) {
            errorMessage.setVisible(true);
        }
    }


        /*// Reset messaggi di errore/successo
        errorMessage.setVisible(false);
        successMessage.setVisible(false);

        // Creazione dell'array di CheckBox
        CheckBox[] checkboxes = new CheckBox[]{
                checkbox1, checkbox2, checkbox3, checkbox4, checkbox5,
                checkbox6, checkbox7, checkbox8, checkbox9
        };

        // Recupera il bean utente loggato
        LoggedUserBean loggedUserBean = session.getUser();
        boolean isPatient = loggedUserBean.getCredentialsBean().getRole().equals(Role.PATIENT);

        // Ottieni le categorie o majors attuali
        Iterable<?> currentItems = getItems(loggedUserBean);
        List<Integer> currentIds = new ArrayList<>();

        if (currentItems != null) {
            for (Object item : currentItems) {
                int id = isPatient ? ((Category) item).getId() : ((Major) item).getId();
                currentIds.add(id);
            }
        }

        // Liste per aggiungere e rimuovere elementi
        List<Object> itemsToAdd = new ArrayList<>();
        List<Object> itemsToRemove = new ArrayList<>();

        // Verifica le selezioni nei checkbox
        for (int i = 0; i < checkboxes.length; i++) {
            int id = i + 1;
            boolean isSelected = checkboxes[i].isSelected();
            boolean isInCurrentItems = currentIds.contains(id);

            Object item = isPatient ? Category.convertIntToCategory(id) : Major.convertIntToMajor(id);

            if (isSelected && !isInCurrentItems) {
                itemsToAdd.add(item);
            } else if (!isSelected && isInCurrentItems) {
                itemsToRemove.add(item);
            }
        }

        boolean modified = false;

        try {
            if (isPatient) {
                List<Category> categoriesToAdd = (List<Category>) (List<?>) itemsToAdd;
                List<Category> categoriesToRemove = (List<Category>) (List<?>) itemsToRemove;
                PatientAccountController.updateCategories((PatientBean) loggedUserBean, categoriesToAdd, categoriesToRemove);
            } else {
                List<Major> majorsToAdd = (List<Major>) (List<?>) itemsToAdd;
                List<Major> majorsToRemove = (List<Major>) (List<?>) itemsToRemove;
                PsychologistAccountController.updateMajors((PsychologistBean) loggedUserBean, majorsToAdd, majorsToRemove);
            }

            if (!itemsToAdd.isEmpty() || !itemsToRemove.isEmpty()) {
                modified = true;
            }

            // Mostra messaggi di successo o errore
            if (modified) {

                successMessage.setVisible(true);
            } else {
                errorMessage.setVisible(true);
            }

        } catch (Exception e) {
            errorMessage.setVisible(true);
        }
    }
*/




    @FXML
    protected void goToModifyScreen(MouseEvent event) {
        try {
            FXMLLoader loader;
            Parent root;
            if(session.getUser()==null){
                loader = new FXMLLoader(getClass().getResource("/com/theradiary/ispwtheradiary/view/Login.fxml"));
                loader.setControllerFactory(c -> new LoginController(session));
                root = loader.load();
            }else if (session.getUser().getCredentialsBean().getRole().equals(Role.PATIENT)) {
                loader = new FXMLLoader(getClass().getResource("/com/theradiary/ispwtheradiary/view/ModifyPatient.fxml"));
                loader.setControllerFactory(c -> new ModifyPatientController(session));
                root = loader.load();
                ((ModifyPatientController)loader.getController()).loadUserData();
            }else{
                loader = new FXMLLoader(getClass().getResource("/com/theradiary/ispwtheradiary/view/ModifyPsychologist.fxml"));
                loader.setControllerFactory(c -> new ModifyPsychologistController(session));
                root = loader.load();
                ((ModifyPsychologistController)loader.getController()).loadUserData();
            }
            changeScene(root,event);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    protected void logout(MouseEvent event) throws IOException {
        session.setUser(null);
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/theradiary/ispwtheradiary/view/Login.fxml"));
        loader.setControllerFactory(c -> new LoginController(session));
        Parent root = loader.load();
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.show();

    }


    @FXML
    protected void goBack(MouseEvent event) {
        try {
            FXMLLoader loader;
            if(session.getUser()==null){
                loader = new FXMLLoader(getClass().getResource("/com/theradiary/ispwtheradiary/view/Login.fxml"));
                loader.setControllerFactory(c -> new LoginController(session));
            }else if (session.getUser().getCredentialsBean().getRole().toString().equals("PATIENT")) {
                loader = new FXMLLoader(getClass().getResource("/com/theradiary/ispwtheradiary/view/HomepageLoggedPt.fxml"));
                loader.setControllerFactory(c -> new HomepagePtController(session));
            } else {
                loader = new FXMLLoader(getClass().getResource("/com/theradiary/ispwtheradiary/view/HomepageLoggedPs.fxml"));
                loader.setControllerFactory(c -> new HomepagePsController(session));
            }
            Parent root = loader.load();
            changeScene(root,event);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}




