package com.theradiary.ispwtheradiary.beans;


import java.time.LocalDate;

public class TaskBean {
    private String taskName;
    private LocalDate taskDeadline;
    private String taskStatus;

    public TaskBean(String taskName,LocalDate taskDeadline, String taskStatus) {
        this.taskName = taskName;
        this.taskDeadline = taskDeadline;
        this.taskStatus = taskStatus;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public LocalDate getTaskDeadline() {
        return taskDeadline;
    }

    public void setTaskDeadline(String taskDeadline) {
        this.taskDeadline = LocalDate.parse(taskDeadline);
    }

    public String getTaskStatus() {
        return taskStatus;
    }

    public void setTaskStatus(String taskStatus) {
        this.taskStatus = taskStatus;
    }
    @Override
    public String toString() {
        return taskName + " - " + taskDeadline + " - " + taskStatus;
    }


}
