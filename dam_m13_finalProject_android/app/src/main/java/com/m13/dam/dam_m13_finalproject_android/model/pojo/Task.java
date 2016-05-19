package com.m13.dam.dam_m13_finalproject_android.model.pojo;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Date;

/**
 * Created by adri on 04/05/2016.
 */

/**
 * Modified by jesus on 10/05/2016
 *
 * Removed 'id' Field
 * Updated 'id_team' Type
 */

public class Task {

    @JsonProperty("id_team")
    String id_team;
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
    @JsonProperty("name")
    String name;
    @JsonProperty("priority")
    int priority;
    @JsonProperty("state")
    int state;


    public Task() {
    }

    // Modified by jesus on 10/05/2016


    public Task(String id_team, String code, Date priorityDate, String description, String localization, String project, String name) {
        this.id_team = id_team;
        this.code = code;
        this.priorityDate = priorityDate;
        this.description = description;
        this.localization = localization;
        this.project = project;
        this.name = name;
    }

    public String getId_team() {
        return id_team;
    }

    public void setId_team(String id_team) {
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    @Override
    public String toString() {
        return "Task{" +
                "id_team='" + id_team + '\'' +
                ", code='" + code + '\'' +
                ", priorityDate=" + priorityDate +
                ", description='" + description + '\'' +
                ", localization='" + localization + '\'' +
                ", project='" + project + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
