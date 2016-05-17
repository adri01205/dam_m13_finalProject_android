package com.m13.dam.dam_m13_finalproject_android.controller;

import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ExpandableListView;

import com.m13.dam.dam_m13_finalproject_android.R;
import com.m13.dam.dam_m13_finalproject_android.controller.adapters.TeamTaskAdapter;
import com.m13.dam.dam_m13_finalproject_android.model.dao.SynupConversor;
import com.m13.dam.dam_m13_finalproject_android.model.dao.SynupSharedPreferences;
import com.m13.dam.dam_m13_finalproject_android.model.pojo.Task;
import com.m13.dam.dam_m13_finalproject_android.model.pojo.Team;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by jesus on 10/05/2016.
 */

public class TaskListActivity extends SynupMenuActivity
        implements ExpandableListView.OnGroupClickListener, ExpandableListView.OnChildClickListener {


    ArrayList<Team> teams;
    HashMap<Team, ArrayList<Task>> tasksMap;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        setContentView(R.layout.tasks_adapter);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        chargeData();

        ExpandableListView elv = (ExpandableListView)findViewById(R.id.lstLlistaExpandible);
        TeamTaskAdapter adaptador = new TeamTaskAdapter(this, teams, tasksMap);


    }

    public void chargeData()
    {
        teams = new ArrayList<>();
        tasksMap = new HashMap<>();
        SynupConversor syn = new SynupConversor(this);
        Cursor cTeams =  syn.getTeamsByUser(SynupSharedPreferences.getUserLoged(this));

        if (cTeams.moveToFirst()){
            do{
                Team team = (Team) cTeams;
                teams.add(team);
                ArrayList<Task> teamTasks = new ArrayList<>();

                Cursor tasks = syn.getTaskByTeam(team.getCode());

                if (tasks.moveToFirst()){
                    do{
                        teamTasks.add((Task) tasks);
                    }while(tasks.moveToFirst());
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
}
