package com.m13.dam.dam_m13_finalproject_android.controller;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ExpandableListView;

/**
 * Created by jesus on 10/05/2016.
 */
public class TaskListActivity extends AppCompatActivity
        implements ExpandableListView.OnGroupClickListener, ExpandableListView.OnChildClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);



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
