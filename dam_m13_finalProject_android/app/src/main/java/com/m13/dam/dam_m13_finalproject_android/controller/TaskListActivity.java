package com.m13.dam.dam_m13_finalproject_android.controller;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ExpandableListView;
import android.widget.Toast;

import com.m13.dam.dam_m13_finalproject_android.R;
import com.m13.dam.dam_m13_finalproject_android.controller.adapters.TeamTaskAdapter;
import com.m13.dam.dam_m13_finalproject_android.model.dao.SynupConversor;
import com.m13.dam.dam_m13_finalproject_android.model.dao.SynupSharedPreferences;
import com.m13.dam.dam_m13_finalproject_android.model.pojo.Task;
import com.m13.dam.dam_m13_finalproject_android.model.pojo.Team;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

/**
 * Created by jesus on 10/05/2016.
 */

public class TaskListActivity extends SynupMenuSearchableActivity
        implements ExpandableListView.OnGroupClickListener, ExpandableListView.OnChildClickListener {


    ArrayList<Team> teams;
    HashMap<Team, ArrayList<Task>> tasksMap;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

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

        ExpandableListView elv = (ExpandableListView)findViewById(R.id.lstLlistaExpandible);
        TeamTaskAdapter expandableAdapter = new TeamTaskAdapter(this, teams, tasksMap);

        elv.setAdapter(expandableAdapter);
        //registerForContextMenu(elv);

        elv.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                Intent intent = new Intent(context, DetailActivity.class);
                intent.putExtra("idTask", tasksMap.get(teams.get(groupPosition)).get(childPosition).getCode());
                startActivity(intent);

                return false;
            }
        });

        elv.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {
            @Override
            public void onGroupExpand(int groupPosition) {
            }
        });

        elv.setOnGroupCollapseListener(new ExpandableListView.OnGroupCollapseListener() {
            @Override
            public void onGroupCollapse(int groupPosition) {

            }
        });


    }



    public void chargeData()
    {
        teams = new ArrayList<>();
        tasksMap = new HashMap<>();
        SynupConversor syn = new SynupConversor(this);
        Cursor cTeams =  syn.getTeamsByUser(SynupSharedPreferences.getUserLoged(this));

        if (cTeams.moveToFirst()){
            do{
                Team team = new Team(cTeams.getString(0), cTeams.getString(1));
                teams.add(team);
                ArrayList<Task> teamTasks = new ArrayList<>();

                Cursor cTasks = syn.getTaskByTeam(team.getCode());

                if (cTasks.moveToFirst()){
                    do{
                        teamTasks.add(new Task(cTasks.getString(0),
                                cTasks.getString(1),
                                new Date(cTasks.getLong(2)),
                                cTasks.getString(3),
                                cTasks.getString(4),
                                cTasks.getString(5),
                                cTasks.getString(6)));
                    }while(cTasks.moveToNext());
                }

                tasksMap.put(team, teamTasks);
            } while (cTeams.moveToNext ());
        }
    }


    @Override
    public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
        return false;
    }

    @Override
    public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
        return false;
    }

    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.tasklistcontextmenu, menu);

    }

    /**
     * Respon a l'event d'haver escollit una opció del menú contextual
     */
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo)item.getMenuInfo();

        switch (item.getItemId()) {
            case R.id.selectTaskContextMenu:
                //TODO: FUNCIÓN SELECCIONAR TASCA
                return true;
            default:
                break;
        }
        return false;
    }

    @Override
    void searchSubmit(String query) {
        Toast.makeText(TaskListActivity.this, query, Toast.LENGTH_SHORT).show();
    }
}
