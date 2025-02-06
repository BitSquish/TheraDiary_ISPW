package com.theradiary.ispwtheradiary.dao.demo;

import com.theradiary.ispwtheradiary.dao.CategoryAndMajorDAO;
import com.theradiary.ispwtheradiary.dao.demo.shared.SharedResources;
import com.theradiary.ispwtheradiary.engineering.enums.Category;
import com.theradiary.ispwtheradiary.engineering.enums.Major;
import com.theradiary.ispwtheradiary.model.Patient;
import com.theradiary.ispwtheradiary.model.Psychologist;
import java.util.concurrent.ConcurrentHashMap;

public class CategoryAndMajorDAOInMemory implements CategoryAndMajorDAO {
    // Strutture dati in memoria

    /*****************************************Gestione categorie paziente**************************************************/
    @Override
    public void addCategory(Patient patient, Category category) {
        if (patient == null || category == null) {
            throw new IllegalArgumentException("Patient and Category must not be null");
        }
        SharedResources.getInstance().getPatientCategories()
                .computeIfAbsent(patient.getCredentials().getMail(), k -> ConcurrentHashMap.newKeySet())
                .add(category);
    }

    @Override
    public void removeCategory(Patient patient, Category category) {
        if (patient == null || category == null) {
            throw new IllegalArgumentException("Patient and Category must not be null");
        }
        SharedResources.getInstance().getPatientCategories()
                .computeIfPresent(patient.getCredentials().getMail(), (key, categories) -> {
                    categories.remove(category);
                    return categories.isEmpty() ? null : categories;
                });
    }
    /********************************************Gestione specializzazioni***********************************************************************/
    @Override
    public void addMajor(Psychologist psychologist, Major major) {
        if (psychologist == null || major == null) {
            throw new IllegalArgumentException("Psychologist and Major must not be null");
        }
        SharedResources.getInstance().getPsychologistMajors()
                .computeIfAbsent(psychologist.getCredentials().getMail(), k -> ConcurrentHashMap.newKeySet())
                .add(major);
    }

    @Override
    public void removeMajor(Psychologist psychologist, Major major) {
        if (psychologist == null || major == null) {
            throw new IllegalArgumentException("Psychologist and Major must not be null");
        }
        SharedResources.getInstance().getPsychologistMajors()
                .computeIfPresent(psychologist.getCredentials().getMail(), (key, majors) -> {
                    majors.remove(major);
                    return majors.isEmpty() ? null : majors;
                });
    }
}