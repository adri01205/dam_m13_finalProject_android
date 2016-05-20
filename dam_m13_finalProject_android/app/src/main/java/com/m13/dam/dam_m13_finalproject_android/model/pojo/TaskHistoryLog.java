package com.m13.dam.dam_m13_finalproject_android.model.pojo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.sql.Date;

/**
 * Created by adri on 04/05/2016.
 */
public class TaskHistoryLog {
    @JsonProperty("id")
    int id;
    @JsonProperty("id_taskHistory")
    int id_taskHistory;
    @JsonProperty("operation")
    String operation;
    @JsonProperty("when")
    Date when;

    public TaskHistoryLog() {
    }

    public TaskHistoryLog(int id, int id_taskHistory, String operation, Date when) {
        this.id = id;
        this.id_taskHistory = id_taskHistory;
        this.operation = operation;
        this.when = when;
    }

    public TaskHistoryLog(int id_taskHistory, String operation, Date when) {
        this.id_taskHistory = id_taskHistory;
        this.operation = operation;
        this.when = when;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId_taskHistory() {
        return id_taskHistory;
    }

    public void setId_taskHistory(int id_taskHistory) {
        this.id_taskHistory = id_taskHistory;
    }

    public String getOperation() {
        return operation;
    }

    public void setOperation(String operation) {
        this.operation = operation;
    }

    public Date getWhen() {
        return when;
    }

    public void setWhen(Date when) {
        this.when = when;
    }

    @Override
    public String toString() {
        return "TaskHistoryLog{" +
                "id=" + id +
                ", id_taskHistory=" + id_taskHistory +
                ", operation='" + operation + '\'' +
                ", when=" + when +
                '}';
    }
}

