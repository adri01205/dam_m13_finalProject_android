package com.m13.dam.dam_m13_finalproject_android.controller;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import com.m13.dam.dam_m13_finalproject_android.R;
import com.m13.dam.dam_m13_finalproject_android.controller.dialogs.Dialogs;
import com.m13.dam.dam_m13_finalproject_android.controller.interfaces.AsyncTaskCompleteListener;
import com.m13.dam.dam_m13_finalproject_android.model.dao.SynupConversor;
import com.m13.dam.dam_m13_finalproject_android.model.dao.SynupSharedPreferences;
import com.m13.dam.dam_m13_finalproject_android.model.pojo.Employee;
import com.m13.dam.dam_m13_finalproject_android.model.pojo.ReturnObject;
import com.m13.dam.dam_m13_finalproject_android.model.pojo.Task;
import com.m13.dam.dam_m13_finalproject_android.model.pojo.TaskHistory;
import com.m13.dam.dam_m13_finalproject_android.model.pojo.Team;
import com.m13.dam.dam_m13_finalproject_android.model.services.AddTaskHistoryServerAsync;

public class DetailActivity extends SynupMenuActivity implements AsyncTaskCompleteListener<ReturnObject> {
    Task task;
    Employee employee;
    TaskHistory taskHistory;
    Team team;
    String taskCode;
    String status;
    SynupConversor sc;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        taskCode = this.getIntent().getStringExtra("idTask");

       if(setTaskObject()) {
           setStatus();
           setTexts();
       }
    }

    private boolean setTaskObject(){
        sc = new SynupConversor(this);
        task = sc.getTask(taskCode);
        if(task == null){
            Intent i = new Intent(this, MenuActivity.class);
            i.putExtra("ERROR", getResources().getString(R.string.TASK_NOT_EXISTS));
            //startActivity(i);
            return false;

        }
        taskHistory = sc.getTaskHistoryByEmployee(taskCode);
        team = sc.getTeam(task.getId_team());
        employee = null;
        if (taskHistory != null) {
            employee = sc.getEmployee(taskHistory.getId_employee());
        }
        return true;
    }

    private void setStatus(){
        Button buttonFinish = ((Button) findViewById(R.id.activity_detail_bt_finish));
        Button buttonAbandone = ((Button) findViewById(R.id.activity_detail_bt_abandone));

//        status = "";
//        if(taskHistory == null){
//            status = getResources().getString(R.string.NEW);
//            buttonFinish.setText(getResources().getString(R.string.TAKE));
//            buttonFinish.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    take();
//                }
//            });
//
//        } else if(taskHistory.getFinishDate() == null){
//            status = getResources().getString(R.string.IN_PROGRESS);
//            buttonFinish.setText(getResources().getString(R.string.FINISH));
//            buttonFinish.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    finishTask();
//                }
//            });
//
//            buttonAbandone.setVisibility(View.VISIBLE);
//            buttonAbandone.setText(getResources().getString(R.string.ABANDONE));
//            buttonAbandone.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    abandone();
//                }
//            });
//
//        } else if (taskHistory.getIsFinished() == 1){
//            status = getResources().getString(R.string.ABANDONED);
//            buttonFinish.setText(getResources().getString(R.string.TAKE));
//            buttonFinish.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    take();
//                }
//            });
//        } else {
//            status = getResources().getString(R.string.FINISHED);
//            buttonFinish.setVisibility(View.GONE);
//        }
    }

    private void setTexts(){
        ((TextView) findViewById(R.id.activity_detail_tv_name)).setText(task.getName());
        ((TextView) findViewById(R.id.activity_detail_tv_code)).setText(task.getCode());
        ((TextView) findViewById(R.id.activity_detail_tv_description)).setText(task.getDescription());
        ((TextView) findViewById(R.id.activity_detail_tv_employee)).setText(taskHistory != null ? employee.getName() : getResources().getString(R.string.NOT_ASSIGNED));
        ((TextView) findViewById(R.id.activity_detail_tv_finish_date)).setText(taskHistory != null ? sc.dataFormat.format(taskHistory.getFinishDate()) : getResources().getString(R.string.NOT_STARTED));
        ((TextView) findViewById(R.id.activity_detail_tv_priority_date)).setText(sc.dataFormat.format(task.getPriorityDate()));
        ((TextView) findViewById(R.id.activity_detail_tv_project)).setText(task.getProject());
        ((TextView) findViewById(R.id.activity_detail_tv_start_date)).setText(taskHistory != null ? sc.dataFormat.format(taskHistory.getStartDate()) : getResources().getString(R.string.NOT_STARTED));
        ((TextView) findViewById(R.id.activity_detail_tv_status)).setText(status);
        ((CheckBox) findViewById(R.id.activity_detail_cb_finished)).setChecked(taskHistory != null && taskHistory.getFinishDate() != null);
        ((TextView) findViewById(R.id.activity_detail_tv_team)).setText(team != null ? team.getName() : "");

    }

    private void abandone() {
        taskHistory.setFinishDate(new java.sql.Date(new java.util.Date().getTime()));
        goMainMenu();
    }

    private void finishTask() {
        taskHistory.setFinishDate(new java.sql.Date(new java.util.Date().getTime()));
        goMainMenu();
    }

    public void take(){
        new AddTaskHistoryServerAsync(this,this).execute(SynupSharedPreferences.getUserLoged(this), taskCode);
    }

    @Override
    public void onTaskComplete(ReturnObject result) {
        if (result.succes()) {
            goMainMenu();
        } else {
            if(result.getCode() == 301){
                Dialogs.getErrorDialog(this, getResources().getString(R.string.ERROR_NO_CONNECTION_TAKE_TASK)).show();
            } else {
                Dialogs.getErrorDialog(this, result).show();
            }
        }
    }

    public void goMainMenu(){
        Intent i = new Intent(this, MenuActivity.class);
        startActivity(i);
    }
}
