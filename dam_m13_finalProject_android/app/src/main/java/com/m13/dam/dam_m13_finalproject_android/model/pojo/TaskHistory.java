package com.m13.dam.dam_m13_finalproject_android.model.pojo;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.sql.Date;

/**
 * Created by adri on 04/05/2016.
 */
public class TaskHistory {

    @JsonProperty("id")
    int id;
    @JsonProperty("id_employee")
    int id_employee;
    @JsonProperty("id_task")
    int id_task;
    @JsonProperty("startDate")
    Date startDate;
    @JsonProperty("finishDate")
    Date finishDate;
    @JsonProperty("comment")
    String comment;
    @JsonProperty("isFinished")
    int isFinished;

    public TaskHistory() {
    }

    public TaskHistory(int id, int id_employee, int id_task, Date startDate, Date finishDate, String comment, int isFinished) {
        this.id = id;
        this.id_employee = id_employee;
        this.id_task = id_task;
        this.startDate = startDate;
        this.finishDate = finishDate;
        this.comment = comment;
        this.isFinished = isFinished;
    }

    public TaskHistory(int id_employee, int id_task, Date startDate, Date finishDate, String comment, int isFinished) {
        this.id_employee = id_employee;
        this.id_task = id_task;
        this.startDate = startDate;
        this.finishDate = finishDate;
        this.comment = comment;
        this.isFinished = isFinished;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId_employee() {
        return id_employee;
    }

    public void setId_employee(int id_employee) {
        this.id_employee = id_employee;
    }

    public int getId_task() {
        return id_task;
    }

    public void setId_task(int id_task) {
        this.id_task = id_task;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getFinishDate() {
        return finishDate;
    }

    public void setFinishDate(Date finishDate) {
        this.finishDate = finishDate;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public int getIsFinished() {
        return isFinished;
    }

    public void setIsFinished(int isFinished) {
        this.isFinished = isFinished;
    }

    @Override
    public String toString() {
        return "TaskHistory{" +
                "id=" + id +
                ", id_employee=" + id_employee +
                ", id_task=" + id_task +
                ", startDate=" + startDate +
                ", finishDate=" + finishDate +
                ", comment='" + comment + '\'' +
                ", isFinished=" + isFinished +
                '}';
    }
}
