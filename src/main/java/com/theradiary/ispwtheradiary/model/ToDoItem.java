package com.theradiary.ispwtheradiary.model;

public class ToDoItem {
    private  String toDo;
    private  boolean completed;

    public ToDoItem(String toDo, boolean completed) {
        this.toDo = toDo;
        this.completed = completed;
    }
    public String getToDo() {
        return toDo;
    }
    public boolean isCompleted() {
        return completed;
    }

}
