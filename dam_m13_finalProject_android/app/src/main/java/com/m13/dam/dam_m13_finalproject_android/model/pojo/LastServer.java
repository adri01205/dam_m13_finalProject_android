package com.m13.dam.dam_m13_finalproject_android.model.pojo;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by adri on 09/05/2016.
 */
public class LastServer {
    @JsonProperty("emplogId")
    private int emplogId;
    @JsonProperty("tasklogId")
    private int tasklogId;
    @JsonProperty("taskhistlogId")
    private int taskhistlogId;
    @JsonProperty("teamlogId")
    private int teamlogId;

    public int getEmplogId() {
        return emplogId;
    }

    public void setEmplogId(int emplogId) {
        this.emplogId = emplogId;
    }

    public int getTasklogId() {
        return tasklogId;
    }

    public void setTasklogId(int tasklogId) {
        this.tasklogId = tasklogId;
    }

    public int getTaskhistlogId() {
        return taskhistlogId;
    }

    public void setTaskhistlogId(int taskhistlogId) {
        this.taskhistlogId = taskhistlogId;
    }

    public int getTeamlogId() {
        return teamlogId;
    }

    public void setTeamlogId(int teamlogId) {
        this.teamlogId = teamlogId;
    }
}
