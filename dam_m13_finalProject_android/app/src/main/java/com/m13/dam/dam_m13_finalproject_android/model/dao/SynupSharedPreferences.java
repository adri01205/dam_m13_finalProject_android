package com.m13.dam.dam_m13_finalproject_android.model.dao;

import android.app.Activity;
import android.content.SharedPreferences;


public abstract class SynupSharedPreferences {
    public static String PREFS_NAME = "FitxerPreferencies";

    //USER LOGED IS THE LOGED IN THE APP
    public static String getUserLoged(Activity context) {
        SharedPreferences config = context.getSharedPreferences(PREFS_NAME, 0);
        return config.getString("user_loged", "");
    }
    public static void updateUserLoged(Activity context, String userName) {
        SharedPreferences config = context.getSharedPreferences(PREFS_NAME, 0);
        SharedPreferences.Editor editor = config.edit();
        editor.putString("user_loged", userName);
        editor.commit();

    }

    //USER NAME SAVED IN THE LOGIN LAYOUT
    public static String getUserNameSaved(Activity context) {
        SharedPreferences config = context.getSharedPreferences(PREFS_NAME, 0);
        return config.getString("user_saved", "");
    }

    public static void updateUserNameSaved(Activity context, String userName) {
        SharedPreferences config = context.getSharedPreferences(PREFS_NAME, 0);
        SharedPreferences.Editor editor = config.edit();
        editor.putString("user_saved", userName);
        editor.commit();
    }

    public static String getUpdatedData(Activity context) {
        SharedPreferences config = context.getSharedPreferences(PREFS_NAME, 0);
        return config.getString("updated_data", "");
    }

    public static void setUpdatedData(Activity context, String data) {
        SharedPreferences config = context.getSharedPreferences(PREFS_NAME, 0);
        SharedPreferences.Editor editor = config.edit();
        editor.putString("updated_data", data);
        editor.commit();
    }


}
