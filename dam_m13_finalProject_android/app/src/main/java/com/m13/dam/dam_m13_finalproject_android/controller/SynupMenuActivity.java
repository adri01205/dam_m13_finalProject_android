package com.m13.dam.dam_m13_finalproject_android.controller;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

import com.m13.dam.dam_m13_finalproject_android.R;
import com.m13.dam.dam_m13_finalproject_android.model.dao.SynupSharedPreferences;

/**
 * Created by adri on 13/05/2016.
 */
public class SynupMenuActivity extends AppCompatActivity {
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        switch (id){
            case R.id.menu_exit:
                System.exit(0);
                break;
            case R.id.menu_log_out:
                SynupSharedPreferences.setUserLoged(this, "");
                Intent intent = new Intent(this, LoginActivity.class);
                this.startActivity(intent);
                break;
        }

        return super.onOptionsItemSelected(item);
    }
}
