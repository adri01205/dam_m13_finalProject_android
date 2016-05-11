package com.m13.dam.dam_m13_finalproject_android.controller;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.m13.dam.dam_m13_finalproject_android.R;
import com.m13.dam.dam_m13_finalproject_android.controller.dialogs.Dialogs;
import com.m13.dam.dam_m13_finalproject_android.controller.interfaces.AsyncTaskCompleteListener;
import com.m13.dam.dam_m13_finalproject_android.model.dao.SynupSharedPreferences;
import com.m13.dam.dam_m13_finalproject_android.model.pojo.ReturnObject;
import com.m13.dam.dam_m13_finalproject_android.model.services.UpdateLocalAsync;

public class MenuActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if(!SynupSharedPreferences.getUpdatedData(this).equals("1")){
            findViewById(R.id.activity_menu_ll_not_updated).setVisibility(View.VISIBLE);
        }

        findViewById(R.id.activity_menu_bt_my_task).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        findViewById(R.id.activity_menu_bt_tasks).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        findViewById(R.id.activity_menu_bt_log_out).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.menu_exit) {
            return true;
        }
        if (id == R.id.menu_log_out) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
