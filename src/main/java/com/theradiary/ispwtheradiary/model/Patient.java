package com.theradiary.ispwtheradiary.model;

import com.theradiary.ispwtheradiary.engineering.enums.Category;

import java.util.ArrayList;
import java.util.List;

public class Patient extends LoggedUser {
    private ArrayList<Category> categories;
    private Psychologist psychologist;
    public Patient(Credentials credentials, String name, String surname, String city, String description, boolean isInPerson, boolean isOnline, boolean isPAG) {
        super(credentials, name, surname, city, description, isInPerson, isOnline, false);
        this.categories = new ArrayList<>();
        this.psychologist = null;
    }
    public List<Category> getCategories() {
        return categories;
    }

    public void addCategory(Category category){
        this.categories.add(category);
    }
}
