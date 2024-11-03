package com.theradiary.ispwtheradiary.model.beans;

import com.theradiary.ispwtheradiary.engineering.enums.Category;

import java.util.ArrayList;

public class PatientBean extends LoggedUserBean{
    private ArrayList<Category> categories;
    public PatientBean(CredentialsBean credentialsBean, String name, String surname, String city, String description, boolean inPerson, boolean online, boolean pag, ArrayList<Category> categories,PsychologistBean psychologistBean) {
        super(credentialsBean, name, surname, city, description, inPerson, online, pag);
        this.categories = (categories != null) ? categories : new ArrayList<>();
    }

    public ArrayList<Category> getCategories() {
        return categories;
    }
    public void addCategory(Category category){
        categories.add(category);
    }

    public void removeCategory(Category category){
        categories.remove(category);
    }
}

