package com.m13.dam.dam_m13_finalproject_android.controller;

import android.content.Intent;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.m13.dam.dam_m13_finalproject_android.R;
import com.m13.dam.dam_m13_finalproject_android.model.dao.SynupSharedPreferences;

/**
 * Created by adri on 13/05/2016.
 */
public abstract class SynupMenuSearchableActivity extends SynupMenuActivity {

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        this.menu = menu;
        getMenuInflater().inflate(R.menu.menu_searchable, menu);
        if(SynupSharedPreferences.getUpdatedData(this) == "0"){
            MenuItem item = menu.findItem(R.id.menu_error_connection);
            if(item != null)
            item.setVisible(true);
        }


        MenuItem searchItem = menu.findItem(R.id.menu_search);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchItem);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                searchSubmit(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String query) {
                searchSubmit(query);
                return false;
            }
        });

        return true;
    }

    abstract void searchSubmit(String query);
}
