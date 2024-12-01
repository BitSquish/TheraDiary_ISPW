package com.theradiary.ispwtheradiary.model;

import com.theradiary.ispwtheradiary.engineering.enums.Category;
import com.theradiary.ispwtheradiary.model.beans.PatientBean;
import com.theradiary.ispwtheradiary.model.beans.ToDoItemBean;
import javafx.collections.ObservableList;

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
    public void addTask(Task task){tasks.add(task);}
    public void setTasks(ObservableList<Task> tasks) {this.tasks = tasks;}
    public void removeTask(Task task){tasks.remove(task);}
    //To do
    public List<ToDoItem> getToDoList() {return toDoList;}
    public void addToDoItem(ToDoItem toDoItem){toDoList.add(toDoItem);}
    public void setToDoList(List<ToDoItem> toDoList) {this.toDoList = toDoList;}
    public void removeToDoItem(ToDoItem toDoItem){ toDoList.remove(toDoItem);}
    //Diary
    public String Diary() {
        return diary;
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

    @Override
    public PatientBean toBean() {
        return new PatientBean(getCredentials().toBean(), getName(), getSurname(), getCity(), getDescription(), isInPerson(), isOnline());
    }


    public void setDiary(String contenuto) {
        this.diary = contenuto;
    }
}
