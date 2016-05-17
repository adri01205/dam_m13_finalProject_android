package com.m13.dam.dam_m13_finalproject_android.model.pojo;

import com.fasterxml.jackson.annotation.JsonProperty;

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

    public TeamHistory() { }

    public TeamHistory(int id, String nif, String code)
    {
        this.id = id;
        this.nif = nif;
        this.code = code;
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
}
