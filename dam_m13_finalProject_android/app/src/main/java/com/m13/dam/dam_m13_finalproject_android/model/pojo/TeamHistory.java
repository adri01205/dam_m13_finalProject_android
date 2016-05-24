package com.m13.dam.dam_m13_finalproject_android.model.pojo;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.sql.Date;

/**
 * Created by jesus on 17/05/2016.
 */
public class TeamHistory
{

    @JsonProperty("id")
    private int id;
    @JsonProperty("nif")
    private String nif;
    @JsonProperty("code")
    private String code;
    @JsonProperty("entranceDay")
    private Date entranceDay;
    @JsonProperty("exitDate")
    private Date exitDate;

    public TeamHistory() { }

    public TeamHistory(int id, String nif, String code, Date entranceDay, Date exitDate)
    {
        this.id = id;
        this.nif = nif;
        this.code = code;
        this.entranceDay = entranceDay;
        this.exitDate = exitDate;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNif() {
        return nif;
    }

    public void setNif(String nif) {
        this.nif = nif;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
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
