package com.theradiary.ispwtheradiary.controller.graphic;

import com.theradiary.ispwtheradiary.engineering.others.Session;
import com.theradiary.ispwtheradiary.model.beans.MedicalOfficeBean;
import com.theradiary.ispwtheradiary.model.beans.PsychologistBean;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PsychologistsListController extends CommonController{
    PsychologistsListController(Session session) {
        super(session);
    }
    @FXML
    private ListView<String> listPsychologist;

    @FXML
    public void printPsychologists(MouseEvent event, List<PsychologistBean> psychologistBeans, ArrayList<MedicalOfficeBean> medicalOfficeBeans) throws SQLException {
        List<String> psychologists = new ArrayList<>();
        for (int i = 0; i < psychologistBeans.size(); i++) {
            String psychologistInfo = "Dott./Dott.ssa " +
                    psychologistBeans.get(i).getName() +
                    " " +
                    psychologistBeans.get(i).getSurname() +
                    "\nCittà: " +
                    psychologistBeans.get(i).getCity() +
                    "\nDescrizione: " +
                    psychologistBeans.get(i).getDescription() +
                    "\nModalità di visita: " +
                    (psychologistBeans.get(i).isInPerson() ? "In presenza" : "") +
                    (psychologistBeans.get(i).isOnline() ? " Online" : "") +
                    (psychologistBeans.get(i).isPag() ? " Aderisce al PAG" : " Non aderisce al PAG")
                    + "\nStudio medico: " +
                    medicalOfficeBeans.get(i).getCity() + ", " + "CAP: " + medicalOfficeBeans.get(i).getPostCode() + ", "
                    + "\n Indirizzo: " +medicalOfficeBeans.get(i).getAddress() + ", " + medicalOfficeBeans.get(i).getOtherInfo();
                    //MANCANO I MAJOR
            psychologists.add(psychologistInfo);

            //retrieve informazioni studio medico se presenti
        }
        ObservableList<String> items = listPsychologist.getItems();
        items.addAll(psychologists);
        listPsychologist.setItems(items);
    }


}
