package com.m13.dam.dam_m13_finalproject_android.controller;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

import com.m13.dam.dam_m13_finalproject_android.R;
import com.m13.dam.dam_m13_finalproject_android.model.dao.SynupConversor;
import com.m13.dam.dam_m13_finalproject_android.model.pojo.Employee;
import com.m13.dam.dam_m13_finalproject_android.model.pojo.Task;
import com.m13.dam.dam_m13_finalproject_android.model.pojo.TaskHistory;
import com.m13.dam.dam_m13_finalproject_android.model.pojo.Team;

public class DetailActivity extends SynupMenuActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        String taskCode = this.getIntent().getStringExtra("idTask");

        SynupConversor sc = new SynupConversor(this);
        Task task = sc.getTask(taskCode);
        TaskHistory taskHistory = sc.getTaskHistoryByEmployee(taskCode);
        Team team = sc.getTeam(task.getId_team());
        Employee employee = null;
        if(taskHistory != null) {
            employee = sc.getEmployee(taskHistory.getId_employee());
        }

        String status = "";
        if(taskHistory == null){
            status = getResources().getString(R.string.NEW);
        } else if(taskHistory.getFinishDate() == null){
            status = getResources().getString(R.string.IN_PROGRESS);
        } else if (taskHistory.getIsFinished() == 1){
            status = getResources().getString(R.string.ABANDONED);
        } else {
            status = getResources().getString(R.string.FINISHED);
        }

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

        Button buttonFinish = ((Button) findViewById(R.id.activity_detail_bt_finish));
        Button buttonAbandone = ((Button) findViewById(R.id.activity_detail_bt_abandone));
//        if(status)
//        buttonAbandone.setVisibility(View.VISIBLE);

    }

}
