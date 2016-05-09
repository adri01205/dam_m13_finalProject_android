package com.m13.dam.dam_m13_finalproject_android.model.pojo;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by adri on 09/05/2016.
 */
public class LastServer {
    @JsonProperty("lastTask")
    private int lastTask;

    @JsonProperty("lastTaskHistory")
    private int lastTaskHistory;

    public int getLastTask() {
        return lastTask;
    }

    public void setLastTask(int lastTask) {
        this.lastTask = lastTask;
    }

    public int getLastTaskHistory() {
        return lastTaskHistory;
    }

    public void setLastTaskHistory(int lastTaskHistory) {
        this.lastTaskHistory = lastTaskHistory;
    }
}
