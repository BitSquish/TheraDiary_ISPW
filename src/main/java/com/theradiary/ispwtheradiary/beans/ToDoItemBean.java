package com.theradiary.ispwtheradiary.beans;



public class ToDoItemBean {
    private String toDo;
    private boolean completed;
    public ToDoItemBean(String toDo, boolean completed) {
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
    @Override
    public String toString() {
        return (isCompleted() ? "[X]" : "[ ]") + "-" + toDo;
    }



}
