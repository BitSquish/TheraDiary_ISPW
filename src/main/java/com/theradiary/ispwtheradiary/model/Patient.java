package com.theradiary.ispwtheradiary.model;

import com.theradiary.ispwtheradiary.engineering.enums.Category;

import java.util.ArrayList;
import java.util.List;

public class Patient extends LoggedUser {
    private ArrayList<Category> categories;
    private Psychologist psychologist;
    public Patient(Credentials credentials, String name, String surname, String city, String description, boolean isInPerson, boolean isOnline) {
        super(credentials, name, surname, city, description, isInPerson, isOnline);
        this.setPag(false);
        this.categories = new ArrayList<>();
        this.psychologist = null;
    }

    public Patient(Credentials credentials) {
        super(credentials);
        this.categories = new ArrayList<>();
        this.psychologist = null;
    }

    public List<Category> getCategories() {
        return categories;
    }
    public void addCategory(Category category){
        categories.add(category);
    }

    public void removeCategory(Category category){
        categories.remove(category);
    }

    public void setCategories(List<Category> categories) {
        this.categories = (ArrayList<Category>) categories;
    }

    public Psychologist getPsychologist() {
        return psychologist;
    }

    public void setPsychologist(Psychologist psychologist) {
        this.psychologist = psychologist;
    }
}
