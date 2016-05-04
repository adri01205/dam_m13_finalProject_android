package com.m13.dam.dam_m13_finalproject_android.model.pojo;

/**
 * Created by adri on 04/05/2016.
 */
public class Last {

    int id;
    int employeeLog;
    int taskLog;
    int employeTaskLog;

    public Last() {
    }

    public Last(int id, int employeeLog, int taskLog, int employeTaskLog) {
        this.id = id;
        this.employeeLog = employeeLog;
        this.taskLog = taskLog;
        this.employeTaskLog = employeTaskLog;
    }

    public Last(int employeeLog, int taskLog, int employeTaskLog) {
        this.employeeLog = employeeLog;
        this.taskLog = taskLog;
        this.employeTaskLog = employeTaskLog;
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

    @Override
    public String toString() {
        return "Last{" +
                "id=" + id +
                ", employeeLog=" + employeeLog +
                ", taskLog=" + taskLog +
                ", employeTaskLog=" + employeTaskLog +
                '}';
    }
}
