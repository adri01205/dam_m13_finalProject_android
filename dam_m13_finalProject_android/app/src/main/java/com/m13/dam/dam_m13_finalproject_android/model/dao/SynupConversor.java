package com.m13.dam.dam_m13_finalproject_android.model.dao;

import android.app.Activity;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.m13.dam.dam_m13_finalproject_android.model.pojo.Task;

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;


/**
 * Created by Sergio on 28/02/2016.
 */
public class SynupConversor {
    public static final String BD_NAME = "SYNUP_BD4";
    public static final int BD_VERSION = 3;
    private SynupSqliteHelper helper;
    public static SimpleDateFormat dataFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    Activity context;

    public SynupConversor(Activity context) {
        this.context = context;
        this.helper = new SynupSqliteHelper(context, SynupConversor.BD_NAME , null, SynupConversor.BD_VERSION);
    }

    public long saveTask(Task t) {
        long index = -1;
        SQLiteDatabase db = helper.getWritableDatabase();
        ContentValues dades = new ContentValues();

        dades.put("id", t.getId());
        dades.put("id_team", t.getId_team());
        dades.put("code", t.getCode());
        dades.put("priorityDate", dataFormat.format(t.getPriorityDate()));
        dades.put("description", t.getDescription());
        dades.put("localization", t.getLocalization());
        dades.put("project", t.getProject());

        try {
            index = db.insertOrThrow("Task", null, dades);
        }
        catch(Exception e) {
            Log.e("ERROR_BUG","Error on insert the user");
        }
        return index;
    }

//    (SQliteDatabase) db.query(
//            "mytable" /* table */,
//            new String[] { "name" } /* columns */,
//            "id = ?" /* where or selection */,
//            new String[] { "john" } /* selectionArgs i.e. value to replace ? */,
//            null /* groupBy */,
//            null /* having */,
//            null /* orderBy */
//            );

    public Task getTask(int id) {
        SQLiteDatabase db = helper.getReadableDatabase();

        Cursor c = db.query(true,
                "Task",
                new String[]{"id", "id_team", "code", "priorityDate", "description", "localization", "project"},
                "id = ?",
                new String[] { String.valueOf(id) },
                null,
                null,
                null,
                null);

        if(c==null || c.getCount()==0){
            return null;
        }
        c.moveToFirst();

        try {
            return new Task(c.getInt(0),c.getInt(1),c.getString(2), new java.sql.Date(dataFormat.parse(c.getString(3)).getTime()),c.getString(4),c.getString(5),c.getString(6));
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    public Cursor getCursorTaskHistoryLog (int first, int last) {
        SQLiteDatabase db = helper.getReadableDatabase();

        return db.query(true,
                "TaskHistoryLog",
                new String[]{"id", "id_taskHistory", "operation", "when"},
                "id > ? and id <= ?",
                new String[] { String.valueOf(first), String.valueOf(last)},
                null,
                null,
                null,
                null);
    }

//    public Task getTask(String name) {
//        if(name.trim().equals("")){
//            return null;
//        }
//        Cursor c = getCursorUsersByName(name);
//        if(c==null || c.getCount()==0){
//            return null;
//        }
//        c.moveToFirst();
//        return new User(c.getInt(0),c.getString(1));
//    }

/*
    public long saveUser(User user) {
        long index = -1;
        SQLiteDatabase db = helper.getWritableDatabase();
        ContentValues dades = new ContentValues();

        dades.put("name", user.getName());


        try {
            index = db.insertOrThrow("user", null, dades);
        }
        catch(Exception e) {
            Log.e("ERROR_BUG","Error on insert the user");
        }
        return index;
    }

    public long saveRun(Run run) {
        long index = -1;
        SQLiteDatabase db = helper.getWritableDatabase();
        ContentValues dades = new ContentValues();

        dades.put("run_time", run.getRun_time());
        dades.put("run_date", run.getRun_date());
        dades.put("id_user", run.getId_user());


        try {
            index = db.insertOrThrow("run", null, dades);
        }
        catch(Exception e) {
            Log.e("ERROR_BUG","Error on insert the run");
        }
        return index;
    }

    public User getUser(String name) {
        if(name.trim().equals("")){
            return null;
        }
        Cursor c = getCursorUsersByName(name);
        if(c==null || c.getCount()==0){
            return null;
        }
        c.moveToFirst();
        return new User(c.getInt(0),c.getString(1));
    }

    public Cursor getCursorUsersByName(String name) {
        SQLiteDatabase db = helper.getReadableDatabase();

        return db.query(true,
                "user",
                new String[]{"id","name"},
                "name = ?",
                new String[] { name },
                null,
                null,
                null,
                null);
    }

    public Cursor getCursorRunsByUser(User user) {
        SQLiteDatabase db = helper.getReadableDatabase();
        return db.query(true,
                "run",
                new String[]{"id", "run_time", "run_date", "id_user"},
                "id_user = ?",
                new String[] { user.getId() + "" },
                null,
                null,
                null,
                "run_date");
    }

    public Cursor getCursorRunsByUser(String userName) {
        SQLiteDatabase db = helper.getReadableDatabase();
        User user = this.getUser(userName);
        int id;
        if(user != null) {
             id = user.getId();
        }else{
            id = 0;
        }

        return db.query(true,
                "run",
                new String[]{"id", "run_time", "run_date", "id_user"},
                "id_user = ?",
                new String[] { id + "" },
                null,
                null,
                null,
                null);
    }

    public void saveRunWithUserName(String userName, String run_time, String run_date) throws NotUserException{
        User user = this.getUser(userName);
        if(user == null) {
            throw new NotUserException();
        }

        Run r = new Run(run_time,run_date,user.getId());
        this.saveRun(r);
    }
    */
}
