package com.m13.dam.dam_m13_finalproject_android.model.pojo;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.sql.Date;

/**
 * Created by adri on 04/05/2016.
 */
public class Task {
    @JsonProperty("id")
    int id;
    @JsonProperty("id_team")
    int id_team;
    @JsonProperty("code")
    String code;
    @JsonProperty("priorityDate")
    Date priorityDate;
    @JsonProperty("description")
    String description;
    @JsonProperty("localization")
    String localization;
    @JsonProperty("project")
    String project;

    public Task() {
    }

    public Task(int id, int id_team, String code, Date priorityDate, String description, String localization, String project) {
        this.id = id;
        this.id_team = id_team;
        this.code = code;
        this.priorityDate = priorityDate;
        this.description = description;
        this.localization = localization;
        this.project = project;
    }

    public Task(int id_team, String code, Date priorityDate, String description, String localization, String project) {
        this.id_team = id_team;
        this.code = code;
        this.priorityDate = priorityDate;
        this.description = description;
        this.localization = localization;
        this.project = project;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId_team() {
        return id_team;
    }

    public void setId_team(int id_team) {
        this.id_team = id_team;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Date getPriorityDate() {
        return priorityDate;
    }

    public void setPriorityDate(Date priorityDate) {
        this.priorityDate = priorityDate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLocalization() {
        return localization;
    }

    public void setLocalization(String localization) {
        this.localization = localization;
    }

    public String getProject() {
        return project;
    }

    public void setProject(String project) {
        this.project = project;
    }

    @Override
    public String toString() {
        return "Task{" +
                "id=" + id +
                ", id_team=" + id_team +
                ", code='" + code + '\'' +
                ", priorityDate=" + priorityDate +
                ", description='" + description + '\'' +
                ", localization='" + localization + '\'' +
                ", project='" + project + '\'' +
                '}';
    }
}
