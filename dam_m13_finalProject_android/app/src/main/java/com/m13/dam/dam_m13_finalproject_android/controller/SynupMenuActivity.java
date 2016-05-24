package com.m13.dam.dam_m13_finalproject_android.controller;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.m13.dam.dam_m13_finalproject_android.R;
import com.m13.dam.dam_m13_finalproject_android.model.dao.SynupSharedPreferences;

/**
 * Created by adri on 13/05/2016.
 */
public class SynupMenuActivity extends SynupMainMenuActivity implements  NavigationView.OnNavigationItemSelectedListener {

    protected Menu menu;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        this.menu = menu;
        getMenuInflater().inflate(R.menu.menu, menu);
        if(SynupSharedPreferences.getUpdatedData(this) == "0"){
            MenuItem item = menu.findItem(R.id.menu_error_connection);
            if(item != null)
            item.setVisible(true);
        }
        // Inflate the menu; this adds items to the action bar if it is present.
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        Intent intent;
        switch (id){
            case R.id.menu_main_menu:
                intent = new Intent(this, MenuActivity.class);
                this.startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }

    public void showErrorItemMenu(){
        if(menu != null) {
            MenuItem item = menu.findItem(R.id.menu_error_connection);
            item.setVisible(true);
        }
    }

}
