package com.m13.dam.dam_m13_finalproject_android.model.pojo;

/**
 * Created by adri on 04/05/2016.
 */

/**
 *  Modified by jesus on 10/06/2016
 *
 *  Added 'teamLog' field
 */
public class Last {

    int id;
    int employeeLog;
    int taskLog;
    int employeTaskLog;
    int teamLog;

    public Last() {
    }

    public Last(int id, int employeeLog, int taskLog, int employeTaskLog, int teamLog) {
        this.id = id;
        this.employeeLog = employeeLog;
        this.taskLog = taskLog;
        this.employeTaskLog = employeTaskLog;
        this.teamLog = teamLog;
    }

    public Last(int employeeLog, int taskLog, int employeTaskLog, int teamLog) {
        this.employeeLog = employeeLog;
        this.taskLog = taskLog;
        this.employeTaskLog = employeTaskLog;
        this.teamLog = teamLog;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getEmployeeLog() {
        return employeeLog;
    }

    public void setEmployeeLog(int employeeLog) {
        this.employeeLog = employeeLog;
    }

    public int getTaskLog() {
        return taskLog;
    }

    public void setTaskLog(int taskLog) {
        this.taskLog = taskLog;
    }

    public int getEmployeTaskLog() {
        return employeTaskLog;
    }

    public void setEmployeTaskLog(int employeTaskLog) {
        this.employeTaskLog = employeTaskLog;
    }

    public int getTeamLog() {
        return teamLog;
    }

    public void setTeamLog(int teamLog) {
        this.teamLog = teamLog;
    }

    @Override
    public String toString() {
        return "Last{" +
                "id=" + id +
                ", employeeLog=" + employeeLog +
                ", taskLog=" + taskLog +
                ", employeTaskLog=" + employeTaskLog +
                ", teamLog=" + teamLog +
                '}';
    }
}
