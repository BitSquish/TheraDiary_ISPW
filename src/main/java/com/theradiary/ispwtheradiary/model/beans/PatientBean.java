package com.theradiary.ispwtheradiary.model.beans;

import com.theradiary.ispwtheradiary.engineering.enums.Category;
import javafx.collections.ObservableList;

import java.util.ArrayList;
import java.util.List;

public class PatientBean extends LoggedUserBean{
    private ArrayList<Category> categories;
    private PsychologistBean psychologistBean;
    private List<TaskBean> tasks;
    private String diary;
    private List<ToDoItemBean> toDoList;
    public PatientBean(CredentialsBean credentialsBean, String name, String surname, String city, String description, boolean inPerson, boolean online) {
        super(credentialsBean, name, surname, city, description, inPerson, online);
        this.setPag(false);
        this.categories = new ArrayList<>();
        this.psychologistBean = null;
        this.tasks = new ArrayList<>();
        this.diary = "";
        this.toDoList = new ArrayList<>();
    }

    public PatientBean(CredentialsBean credentialsBean) {
        super(credentialsBean);
        this.categories = new ArrayList<>();
        this.psychologistBean = null;
        this.tasks = new ArrayList<>();
        this.diary = "";
        this.toDoList = new ArrayList<>();
    }
    //Task
    public List<TaskBean> getTasks() {
        return tasks;
    }
    public void addTask(TaskBean task){
        tasks.add(task);
    }
    public void setTasks(List<TaskBean> tasks) {
        this.tasks = tasks;
    }
    public void removeTask(TaskBean task){tasks.remove(task);}
    //To do
    public List<ToDoItemBean> getToDoList() {return toDoList;}
    public void addToDoItem(ToDoItemBean toDoItem){toDoList.add(toDoItem);}
    public void setToDoList(List<ToDoItemBean> toDoList) {this.toDoList = toDoList;}
    public void removeToDoItem(ToDoItemBean toDoItem){ toDoList.remove(toDoItem);}

    public PsychologistBean getPsychologistBean(){
        return psychologistBean;
    }

    public void setPsychologistBean(PsychologistBean psychologistBean) {
        this.psychologistBean = psychologistBean;
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
    public String getDiary() {
        return diary;
    }
    public void setDiary(String diary) {
        this.diary = diary;
    }


}

