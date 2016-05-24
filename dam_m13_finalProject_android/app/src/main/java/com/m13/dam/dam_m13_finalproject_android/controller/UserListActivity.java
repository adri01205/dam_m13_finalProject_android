package com.m13.dam.dam_m13_finalproject_android.controller;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.m13.dam.dam_m13_finalproject_android.R;
import com.m13.dam.dam_m13_finalproject_android.model.dao.SynupConversor;
import com.m13.dam.dam_m13_finalproject_android.model.dao.SynupSharedPreferences;
import com.m13.dam.dam_m13_finalproject_android.model.pojo.Task;
import com.m13.dam.dam_m13_finalproject_android.model.pojo.Team;


import java.util.ArrayList;
import java.sql.Date;


public class UserListActivity extends SynupMenuSearchableActivity implements ListView.OnItemClickListener{

    ArrayList<Task> tasks;
    ArrayList<String> tasksName;

    ListView lv;
    String pattern = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_list);

        final Activity context = this;

        setContentView(R.layout.tasks_adapter);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        chargeData();

        ArrayAdapter adapter = new ArrayAdapter(this,
                android.R.layout.simple_list_item_1, tasksName);

        lv.setAdapter(adapter);
    }

    public void chargeData()
    {
        tasks = new ArrayList<>();
        tasksName = new ArrayList<>();

        SynupConversor syn = new SynupConversor(this);
        Cursor cTasks =  syn.getTasksByEmployee(SynupSharedPreferences.getUserLoged(this), pattern);

        if (cTasks.moveToFirst()) {
            do {

                Task task = new Task(
                        cTasks.getString(0),
                        cTasks.getString(1),
                        new Date(cTasks.getLong(2)),
                        cTasks.getString(3),
                        cTasks.getString(4),
                        cTasks.getString(5),
                        cTasks.getString(6));

                tasks.add(task);
                tasksName.add(task.getName());

            } while (cTasks.moveToNext());
        }
    }

    @Override
    void searchSubmit(String query) {
        pattern = query;
        chargeData();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent(this, DetailActivity.class);
        intent.putExtra("idTask", tasks.get(position).getName());
        startActivity(intent);
    }
}
