package com.theradiary.ispwtheradiary.engineering.dao;

import com.theradiary.ispwtheradiary.engineering.enums.Category;
import com.theradiary.ispwtheradiary.engineering.enums.Major;
import com.theradiary.ispwtheradiary.model.Patient;
import com.theradiary.ispwtheradiary.model.Psychologist;

public interface CategoryAndMajorDAO {
    //Metodi per aggiungere e rimuovere categorie e specializzazioni
    void addCategory(Patient patient, Category category);
    void addMajor(Psychologist psychologist, Major major);
    void removeCategory(Patient patient, Category category);
    void removeMajor(Psychologist psychologist, Major major);

}
