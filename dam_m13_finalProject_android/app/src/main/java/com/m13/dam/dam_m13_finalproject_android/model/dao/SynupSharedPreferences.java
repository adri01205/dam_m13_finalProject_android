package com.m13.dam.dam_m13_finalproject_android.model.dao;

import android.app.Activity;
import android.content.SharedPreferences;

/**
 * Created by Adri on 27/03/2016.
 */
public class SynupSharedPreferences {
    public static String PREFS_NAME = "FitxerPreferencies";
    Activity context;

    public SynupSharedPreferences(Activity context) {
        this.context = context;
    }

    public String getUserLoged() {
        SharedPreferences config = context.getSharedPreferences(PREFS_NAME, 0);
        return config.getString("user_loged", "");
    }

    public String getUserNameSaved() {
        SharedPreferences config = context.getSharedPreferences(PREFS_NAME, 0);
        return config.getString("user_saved", "");
    }

    public void updateUserNameSaved(String userName, boolean update) {
        SharedPreferences config = context.getSharedPreferences(PREFS_NAME, 0);
        SharedPreferences.Editor editor = config.edit();
        if (update) {
            editor.putString("user_saved", userName);
            editor.commit();
        } else {
            editor.putString("user_saved", "");
            editor.commit();
        }
    }

    public void updateUserLoged(String userName) {
        SharedPreferences config = context.getSharedPreferences(PREFS_NAME, 0);
        SharedPreferences.Editor editor = config.edit();
        editor.putString("user_loged", userName);
        editor.commit();

    }
}
