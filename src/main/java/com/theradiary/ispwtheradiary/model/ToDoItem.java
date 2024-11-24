package com.theradiary.ispwtheradiary.model;

public class ToDoItem {
    private String toDo;
    private boolean completed;

    public ToDoItem(String toDo, boolean completed) {
        this.toDo = toDo;
        this.completed = completed;
    }

    public String getToDo() {
        return toDo;
    }

    public void setToDo(String toDo) {
        this.toDo = toDo;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }
}
