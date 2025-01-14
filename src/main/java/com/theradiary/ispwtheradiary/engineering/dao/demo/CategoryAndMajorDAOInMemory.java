package com.theradiary.ispwtheradiary.engineering.dao.demo;

import com.theradiary.ispwtheradiary.engineering.dao.CategoryAndMajorDAO;
import com.theradiary.ispwtheradiary.engineering.dao.demo.shared.SharedResources;
import com.theradiary.ispwtheradiary.engineering.enums.Category;
import com.theradiary.ispwtheradiary.engineering.enums.Major;
import com.theradiary.ispwtheradiary.model.Patient;
import com.theradiary.ispwtheradiary.model.Psychologist;

import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class CategoryAndMajorDAOInMemory implements CategoryAndMajorDAO {
    // Strutture dati in memoria

    /*****************************************Gestione categorie paziente**************************************************/
    @Override
    public void addCategory(Patient patient, Category category) {
        SharedResources.getInstance().getPatientCategories()
                .computeIfAbsent(patient.getCredentials().getMail(), k -> ConcurrentHashMap.newKeySet())
                .add(category);
    } //computeIfAbsent: se la chiave non è presente, la inserisce con il valore restituito dalla lambda

    @Override
    public void removeCategory(Patient patient, Category category) {
        Set<Category> categories = SharedResources.getInstance().getPatientCategories()
                .get(patient.getCredentials().getMail());
        if (categories != null) { //se il paziente ha delle categorie
            categories.remove(category);
            if (categories.isEmpty()) { //se il paziente non ha più categorie
                SharedResources.getInstance().getPatientCategories()
                        .remove(patient.getCredentials().getMail());
            }
        }
    }

    /*****************************************Gestione specializzazioni psicologo**************************************************/
    @Override
    public void addMajor(Psychologist psychologist, Major major) {
        SharedResources.getInstance().getPsychologistMajors()
                .computeIfAbsent(psychologist.getCredentials().getMail(), k -> ConcurrentHashMap.newKeySet())
                .add(major);
    }

    @Override
    public void removeMajor(Psychologist psychologist, Major major) {
        Set<Major> majors = SharedResources.getInstance().getPsychologistMajors()
                .get(psychologist.getCredentials().getMail());
        if (majors != null) {
            majors.remove(major);
            if (majors.isEmpty()) {
                SharedResources.getInstance().getPsychologistMajors()
                        .remove(psychologist.getCredentials().getMail());
            }
        }
    }

}
