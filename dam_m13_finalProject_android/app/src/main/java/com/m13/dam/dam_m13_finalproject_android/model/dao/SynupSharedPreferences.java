package com.m13.dam.dam_m13_finalproject_android.model.dao;

import android.app.Activity;
import android.content.SharedPreferences;


public class SynupSharedPreferences {
    public static String PREFS_NAME = "FitxerPreferencies";
    Activity context;

    public SynupSharedPreferences(Activity context) {
        this.context = context;
    }

    //USER LOGED IS THE LOGED IN THE APP
    public String getUserLoged() {
        SharedPreferences config = context.getSharedPreferences(PREFS_NAME, 0);
        return config.getString("user_loged", "");
    }
    public void updateUserLoged(String userName) {
        SharedPreferences config = context.getSharedPreferences(PREFS_NAME, 0);
        SharedPreferences.Editor editor = config.edit();
        editor.putString("user_loged", userName);
        editor.commit();

    }

    //USER NAME SAVED IN THE LOGIN LAYOUT
    public String getUserNameSaved() {
        SharedPreferences config = context.getSharedPreferences(PREFS_NAME, 0);
        return config.getString("user_saved", "");
    }

    public void updateUserNameSaved(String userName) {
        SharedPreferences config = context.getSharedPreferences(PREFS_NAME, 0);
        SharedPreferences.Editor editor = config.edit();
        editor.putString("user_saved", userName);
        editor.commit();
    }


}
