package com.theradiary.ispwtheradiary.view.gui.account;


import com.theradiary.ispwtheradiary.controller.AccountController;
import com.theradiary.ispwtheradiary.view.gui.CommonGUI;
import com.theradiary.ispwtheradiary.view.gui.login.LoginGUI;
import com.theradiary.ispwtheradiary.view.gui.modify.ModifyPatientGUI;
import com.theradiary.ispwtheradiary.view.gui.modify.ModifyPsychologistGUI;
import com.theradiary.ispwtheradiary.engineering.enums.Category;
import com.theradiary.ispwtheradiary.engineering.enums.Major;
import com.theradiary.ispwtheradiary.engineering.enums.Role;
import com.theradiary.ispwtheradiary.exceptions.LoadingException;
import com.theradiary.ispwtheradiary.engineering.others.FXMLPathConfig;
import com.theradiary.ispwtheradiary.engineering.others.Session;
import com.theradiary.ispwtheradiary.beans.LoggedUserBean;
import com.theradiary.ispwtheradiary.beans.PatientBean;
import com.theradiary.ispwtheradiary.beans.PsychologistBean;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;


import javafx.scene.control.Label;

import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;

import java.util.HashSet;

import java.util.Set;

public abstract class AccountGUI extends CommonGUI {

    protected AccountController accountController = new AccountController();
    protected AccountGUI(FXMLPathConfig fxmlPathConfig, Session session) {
        super(fxmlPathConfig,session);
    }

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

    /*********I seguenti metodi vengono utilizzati per settare le checkbox riguardanti le category(paziente)/major(psicologi)***********/

    // Metodo astratto per recuperare le categorie o majors
    protected abstract void retrieveData(AccountController accountController, LoggedUserBean loggedUserBean);

    // Metodo astratto per ottenere le categorie o majors dal bean utente
    protected abstract <T> Iterable<T> getItems(LoggedUserBean loggedUserBean);


    //Inizializzazione delle checkbox riguardanti category/major
    @FXML
    protected void initializeItems(LoggedUserBean loggedUserBean) {
        CheckBox[] checkBoxes = new CheckBox[]{
                checkbox1, checkbox2, checkbox3, checkbox4, checkbox5,
                checkbox6, checkbox7, checkbox8, checkbox9
        };
        // Recupera i dati necessari per l'utente (categorie o majors)
        retrieveData(accountController, loggedUserBean);
        // Ottieni le categorie o majors dall'oggetto `LoggedUserBean`
        Iterable<?> items = getItems(loggedUserBean);
        if (items != null) {
            // Itera sugli elementi (categorie o majors) e seleziona le checkbox corrispondenti
            for (Object item : items) {
                //cast a Category se l'utente è un paziente, o Major se l'utente è uno psicologo
                int id = (loggedUserBean.getCredentialsBean().getRole().equals(Role.PATIENT)) ? ((Category) item).getId() : ((Major) item).getId();
                if (id > 0 && id <= checkBoxes.length) {
                    checkBoxes[id - 1].setSelected(true);
                }
            }
        }
    }

    @FXML
    protected void updateData() {
        resetMessages();
        // Creazione dell'array di CheckBox
        CheckBox[] checkboxes = createCheckBoxArray();

        // Recupera il bean utente loggato
        LoggedUserBean loggedUserBean = session.getUser();
        boolean isPatient = loggedUserBean.getCredentialsBean().getRole().equals(Role.PATIENT);

        // Ottieni gli ID degli elementi attualmente presenti
        Set<Integer> currentIds = getCurrentIds(loggedUserBean, isPatient);

        // Aggiorna gli elementi basandosi sulle selezioni delle checkbox
        boolean modified = updateItems(checkboxes, loggedUserBean, isPatient, currentIds);

        // Mostra i messaggi di successo o errore
        showMessages(modified);
    }

    // Metodo per resettare i messaggi di errore/successo
    private void resetMessages() {
        errorMessage.setVisible(false);
        successMessage.setVisible(false);
    }

    // Metodo per creare un array di checkbox
    private CheckBox[] createCheckBoxArray() {
        return new CheckBox[]{
                checkbox1, checkbox2, checkbox3, checkbox4, checkbox5,
                checkbox6, checkbox7, checkbox8, checkbox9
        };
    }

    // Metodo per ottenere gli ID correnti
    private Set<Integer> getCurrentIds(LoggedUserBean loggedUserBean, boolean isPatient) {
        Set<Integer> currentIds = new HashSet<>();
        Iterable<?> currentItems = getItems(loggedUserBean);

        // Usa un Set per evitare duplicati
        if (currentItems != null) {
            for (Object item : currentItems) {
                int id = isPatient ? ((Category) item).getId() : ((Major) item).getId();
                currentIds.add(id);
            }
        }
        return currentIds;
    }

    // Metodo per aggiornare gli elementi selezionati
    private boolean updateItems(CheckBox[] checkboxes, LoggedUserBean loggedUserBean, boolean isPatient, Set<Integer> currentIds) {
        boolean modified = false;

        // Itera sulle checkbox e aggiorna gli elementi
        for (int i = 0; i < checkboxes.length; i++) {
            int id=i+1;
            
            boolean isSelected = checkboxes[i].isSelected();

            // Recupera l'elemento corrispondente (Category o Major)
            Object item = isPatient? Category.convertIntToCategory(id) : Major.convertIntToMajor(id);

            // Verifica se è necessario aggiungere o rimuovere l'elemento
            if (isSelected && !currentIds.contains(id)) {
                addItem(loggedUserBean, isPatient, item);
                modified = true;
            } else if (!isSelected && currentIds.contains(id)) {
                removeItem(loggedUserBean, isPatient, item);
                modified = true;
            }
        }

        return modified;
    }

    // Metodo per ottenere un oggetto `Category` o `Major` in base all'ID

    // Metodo per aggiungere un elemento
    private void addItem(LoggedUserBean loggedUserBean, boolean isPatient, Object item) {
        if (isPatient) {
            PatientAccountGUI.addCategory((PatientBean) loggedUserBean, (Category) item);
        } else {
            PsychologistAccountGUI.addMajor((PsychologistBean) loggedUserBean, (Major) item);
        }
    }

    // Metodo per rimuovere un elemento
    private void removeItem(LoggedUserBean loggedUserBean, boolean isPatient, Object item) {
        if (isPatient) {
            PatientAccountGUI.removeCategory((PatientBean) loggedUserBean, (Category) item);
        } else {
            PsychologistAccountGUI.removeMajor((PsychologistBean) loggedUserBean, (Major) item);
        }
    }

    // Metodo per mostrare i messaggi di successo o errore
    private void showMessages(boolean modified) {
        if (modified) {
            successMessage.setVisible(true);
        } else {
            errorMessage.setVisible(true);
        }
    }

/*******Fine metodi category/major********/

    // Metodo per andare alla schermata di modifica dei dati immessi in registrazione
    @FXML
    protected void goToModifyScreen(MouseEvent event) {
        try {
            FXMLLoader loader;
            Parent root;
            if(session.getUser()==null){
                loader = new FXMLLoader(getClass().getResource(fxmlPathConfig.getFXMLPath(LOGIN_PATH)));
                loader.setControllerFactory(c -> new LoginGUI(fxmlPathConfig, session));
                root = loader.load();
            }else if (session.getUser().getCredentialsBean().getRole().equals(Role.PATIENT)) {
                loader = new FXMLLoader(getClass().getResource(fxmlPathConfig.getFXMLPath(MODIFY_PATIENT_PATH)));
                loader.setControllerFactory(c -> new ModifyPatientGUI(fxmlPathConfig,session));
                root = loader.load();
                ((ModifyPatientGUI)loader.getController()).loadUserData();
            }else{
                loader = new FXMLLoader(getClass().getResource(fxmlPathConfig.getFXMLPath(MODIFY_PSYCHOLOGIST_PATH)));
                loader.setControllerFactory(c -> new ModifyPsychologistGUI(fxmlPathConfig,session));
                root = loader.load();
                ((ModifyPsychologistGUI)loader.getController()).loadUserData();
            }
            changeScene(root,event);
        } catch (IOException e) {
            throw new LoadingException(LOADING_SCENE, e);
        }
    }

    // Logout
    @FXML
    protected void logout(MouseEvent event) throws IOException {
        //rimuove l'utente dalla sessione
        if(session!=null) {
            session.setUser(null);
        }
        //carica la schermata di login
        FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPathConfig.getFXMLPath(LOGIN_PATH)));
        loader.setControllerFactory(c -> new LoginGUI(fxmlPathConfig, session));
        Parent root = loader.load();
        //cambia scena
        Stage stage=(Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.show();
    }

}




