package com.m13.dam.dam_m13_finalproject_android.model.pojo;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Date;

/**
 * Created by jesus on 17/05/2016.
 */
public class TeamHistory
{

    @JsonProperty("id")
    private int id;
    @JsonProperty("id_employee")
    private String id_employee;
    @JsonProperty("id_team")
    private String id_team;
    @JsonProperty("entranceDay")
    private Date entranceDay;
    @JsonProperty("exitDate")
    private Date exitDate;

    public TeamHistory() { }

    public TeamHistory(int id, String id_employee, String id_team, Date entranceDay, Date exitDate)
    {
        this.id = id;
        this.id_employee = id_employee;
        this.id_team = id_team;
        this.entranceDay = entranceDay;
        this.exitDate = exitDate;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getId_employee() {
        return id_employee;
    }

    public void setId_employee(String id_employee) {
        this.id_employee = id_employee;
    }

    public String getId_team() {
        return id_team;
    }

    public void setId_team(String id_team) {
        this.id_team = id_team;
    }

    public Date getEntranceDay() {
        return entranceDay;
    }

    public void setEntranceDay(Date entranceDay) {
        this.entranceDay = entranceDay;
    }

    public Date getExitDate() {
        return exitDate;
    }

    public void setExitDate(Date entranceDate) {
        this.exitDate = entranceDate;
    }
}
