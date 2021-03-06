package com.m13.dam.dam_m13_finalproject_android.model.pojo;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by jesus on 09/05/2016.
 */
public class Team
{
    @JsonProperty("code")
    private String code;
    @JsonProperty("name")
    private String name;

    public Team(){ }

    public Team(String code, String name) {
        this.code = code;
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Team{" +
                "code='" + code + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
