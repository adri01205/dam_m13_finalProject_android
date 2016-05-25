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
    String employee;
    int employeeLog;
    int taskLog;
    int taskHistoryLog;
    int teamLog;
    int teamHistoryLog;
    int taskHistoryLogServer;

    public Last() {
    }

    public Last(int id, String employee, int employeeLog, int taskLog, int taskHistoryLog, int teamLog, int teamHistoryLog, int taskHistoryLogServer) {
        this.id = id;
        this.employee = employee;
        this.employeeLog = employeeLog;
        this.taskLog = taskLog;
        this.taskHistoryLog = taskHistoryLog;
        this.teamLog = teamLog;
        this.teamHistoryLog = teamHistoryLog;
        this.taskHistoryLogServer = taskHistoryLogServer;
    }

    public Last(String employee, int employeeLog, int taskLog, int taskHistoryLog, int teamLog, int teamHistoryLog, int taskHistoryLogServer) {
        this.employee = employee;
        this.employeeLog = employeeLog;
        this.taskLog = taskLog;
        this.taskHistoryLog = taskHistoryLog;
        this.teamLog = teamLog;
        this.teamHistoryLog = teamHistoryLog;
        this.taskHistoryLogServer = taskHistoryLogServer;
    }

    public int getTaskHistoryLogServer() {
        return taskHistoryLogServer;
    }

    public void setTaskHistoryLogServer(int taskHistoryLogServer) {
        this.taskHistoryLogServer = taskHistoryLogServer;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEmployee() {
        return employee;
    }

    public void setEmployee(String employee) {
        this.employee = employee;
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

    public int getTaskHistoryLog() {
        return taskHistoryLog;
    }

    public void setTaskHistoryLog(int taskHistoryLog) {
        this.taskHistoryLog = taskHistoryLog;
    }

    public int getTeamLog() {
        return teamLog;
    }

    public void setTeamLog(int teamLog) {
        this.teamLog = teamLog;
    }

    public int getTeamHistoryLog() {
        return teamHistoryLog;
    }

    public void setTeamHistoryLog(int teamHistoryLog) {
        this.teamHistoryLog = teamHistoryLog;
    }

    @Override
    public String toString() {
        return "Last{" +
                "id=" + id +
                ", employee='" + employee + '\'' +
                ", employeeLog=" + employeeLog +
                ", taskLog=" + taskLog +
                ", taskHistoryLog=" + taskHistoryLog +
                ", teamLog=" + teamLog +
                ", teamHistoryLog=" + teamHistoryLog +
                '}';
    }
}
