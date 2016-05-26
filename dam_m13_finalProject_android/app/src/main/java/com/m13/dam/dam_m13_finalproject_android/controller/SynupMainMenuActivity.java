package com.m13.dam.dam_m13_finalproject_android.controller;

import android.content.Intent;
import android.database.Cursor;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

import com.m13.dam.dam_m13_finalproject_android.R;
import com.m13.dam.dam_m13_finalproject_android.controller.dialogs.Dialogs;
import com.m13.dam.dam_m13_finalproject_android.model.dao.SynupConversor;
import com.m13.dam.dam_m13_finalproject_android.model.dao.SynupSharedPreferences;
import com.m13.dam.dam_m13_finalproject_android.model.pojo.Task;

/**
 * Created by adri on 13/05/2016.
 */
public class SynupMainMenuActivity extends AppCompatActivity implements  NavigationView.OnNavigationItemSelectedListener {
    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        Intent intent;
        switch (id){

            case R.id.navigation_main_menu:
                intent = new Intent(this, MenuActivity.class);
                this.startActivity(intent);
                break;
            case R.id.navigation_myTask:
                SynupConversor synupConversor = new SynupConversor(this);
                Cursor c = synupConversor.getTaskByEmployee(SynupSharedPreferences.getUserLoged(this), "");
                if(c.getCount() > 0) {
                    intent = new Intent(this, UserListActivity.class);
                    this.startActivity(intent);
                } else {
                    Dialogs.getErrorDialog(this, getResources().getString(R.string.ERROR_NO_TASK_TOOK)).show();
                }
                break;
            case R.id.navigation_tasks:
                intent = new Intent(this, TaskListActivity.class);
                startActivity(intent);
                break;
            case R.id.navigation_log_out:
                SynupSharedPreferences.setUserLoged(this, "");
                intent = new Intent(this, LoginActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK  | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                this.startActivity(intent);
                break;
            case R.id.navigation_exit:
                intent = new Intent(Intent.ACTION_MAIN);
                intent.addCategory(Intent.CATEGORY_HOME);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK  | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                break;
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }
}
