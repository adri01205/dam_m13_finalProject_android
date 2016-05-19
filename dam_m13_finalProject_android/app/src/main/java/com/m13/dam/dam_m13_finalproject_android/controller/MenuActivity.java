package com.m13.dam.dam_m13_finalproject_android.controller;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.m13.dam.dam_m13_finalproject_android.R;
import com.m13.dam.dam_m13_finalproject_android.controller.dialogs.Dialogs;
import com.m13.dam.dam_m13_finalproject_android.controller.interfaces.AsyncTaskCompleteListener;
import com.m13.dam.dam_m13_finalproject_android.model.dao.SynupConversor;
import com.m13.dam.dam_m13_finalproject_android.model.dao.SynupSharedPreferences;
import com.m13.dam.dam_m13_finalproject_android.model.pojo.ReturnObject;
import com.m13.dam.dam_m13_finalproject_android.model.pojo.Task;
import com.m13.dam.dam_m13_finalproject_android.model.services.UpdateLocalAsync;

public class MenuActivity extends AppCompatActivity implements AsyncTaskCompleteListener<ReturnObject> {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        final Activity context = this;

        String errors = getIntent().getStringExtra("ERROR");


        SynupSharedPreferences.setUpdatedData(this,"0");
        if (!SynupSharedPreferences.getUpdatedData(this).equals("1")) {
            new UpdateLocalAsync(this, this).execute();
        }

        findViewById(R.id.activity_menu_bt_my_task).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SynupConversor synupConversor = new SynupConversor(context);
//                Task t = synupConversor.getTaskAcctived(SynupSharedPreferences.getUserLoged(context));
//                if(t!= null) {
                    Intent intent = new Intent(context, DetailActivity.class);
//                    intent.putExtra("idTask", t.getCode());
                    intent.putExtra("idTask", "task1");
                    context.startActivity(intent);
//                }

            }
        });

        findViewById(R.id.activity_menu_bt_tasks).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, TaskListActivity.class);
                startActivity(intent);
            }
        });

        findViewById(R.id.activity_menu_bt_log_out).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SynupSharedPreferences.setUserLoged(context, "");
                Intent intent = new Intent(context, LoginActivity.class);
                context.startActivity(intent);
            }
        });

    }

    @Override
    public void onTaskComplete(ReturnObject result) {
        if (result.succes()) {
            SynupSharedPreferences.setUpdatedData(this,"1");
        } else {
            Dialogs.getErrorDialog(this,result).show();
            findViewById(R.id.activity_menu_ll_not_updated).setVisibility(View.VISIBLE);

        }
    }

}

