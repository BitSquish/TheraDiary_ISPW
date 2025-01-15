package com.theradiary.ispwtheradiary.model;

import com.theradiary.ispwtheradiary.engineering.enums.Category;


import java.util.ArrayList;
import java.util.List;

public class Patient extends LoggedUser {
    private ArrayList<Category> categories;
    private Psychologist psychologist;
    private List<Task> tasks;
    private String diary;
    private List<ToDoItem> toDoList;
    public Patient(Credentials credentials, String name, String surname, String city, String description, boolean isInPerson, boolean isOnline) {
        super(credentials, name, surname, city, description, isInPerson, isOnline);
        this.setPag(false);
        this.categories = new ArrayList<>();
        this.psychologist = null;
        this.tasks = new ArrayList<>();
        this.diary = "";
        this.toDoList = new ArrayList<>();
    }

    public Patient(Credentials credentials) {
        super(credentials);
        this.categories = new ArrayList<>();
        this.psychologist = null;
        this.tasks = new ArrayList<>();
        this.diary = "";
        this.toDoList = new ArrayList<>();

    }
    //Task
    public List<Task> getTasks() {return tasks;}

    //To do
    public List<ToDoItem> getToDoList() {return toDoList;}



    public List<Category> getCategories() {
        return categories;
    }
    public void addCategory(Category category){
        categories.add(category);
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



    public void setDiary(String content) {
        this.diary = content;
    }
}
