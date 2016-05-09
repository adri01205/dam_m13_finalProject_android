package com.m13.dam.dam_m13_finalproject_android.model.dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Sergio on 28/02/2016.
 */
public class SynupSqliteHelper extends SQLiteOpenHelper {

    static String createTaskHistoryLog = "CREATE TABLE TaskHistoryLog ( "+
          " id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, "+
          " id_taskHistory INTEGER NOT NULL, "+
          " operation TEXT NOT NULL, "+
          " 'when' datetime default current_timestamp "+
          "); ";

    static String createTaskHistory = "CREATE TABLE TaskHistory ( "+
          " id INTEGER NOT NULL, "+
          " id_employee INTEGER NOT NULL, "+
          " id_task INTEGER NOT NULL, "+
          " startDate datetime, "+
          " finishDate datetime, "+
          " comment TEXT, "+
          " isFinished INTEGER, "+
          " PRIMARY KEY(id) "+
          "); ";

    static String createTask = "CREATE TABLE  Task  ( "+
          " id INTEGER NOT NULL, "+
          " id_team INTEGER NOT NULL, "+
          " code TEXT NOT NULL, "+
          " priorityDate DATE NOT NULL, "+
          " description TEXT, "+
          " localization TEXT, "+
          " project TEXT, "+
          " PRIMARY KEY(id) "+
          "); ";

    static String createLast = "CREATE TABLE Last ( "+
          " id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, "+
          " employeeLog INTEGER, "+
          " taskLog INTEGER, "+
          " employeTaskLog INTEGER "+
          "); ";

    static String createEmployee = "CREATE TABLE Employee ( "+
          " id INTEGER NOT NULL, "+
          " nif TEXT NOT NULL, "+
          " name TEXT, "+
          " surname TEXT, "+
          " phone TEXT, "+
          " email TEXT, "+
          " adress TEXT, "+
          " PRIMARY KEY(id) "+
          "); ";

    static String createTeam = "CREATE TABLE Team( " +
            "id Integer NOT NULL," +
            "code TEXT NOT NULL," +
            "name TEXT" +
            "PRIMARY KEY(id) " +
            ");";

    static String createTrTHU = "CREATE TRIGGER trTHU "+
          "  before update on TaskHistory "+
          "    for each row "+
          " begin "+
          "      INSERT INTO TaskHistoryLog(id_taskHistory, operation)  "+
          "     select new.id, 'U'; "+
          " end; ";
    
    

    public SynupSqliteHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(createTaskHistoryLog);
        db.execSQL(createTaskHistory);
        db.execSQL(createTask);
        db.execSQL(createLast);
        db.execSQL(createEmployee);
        db.execSQL(createTeam);
        db.execSQL(createTrTHU);
        db.execSQL("INSERT INTO Last VALUES (1,0,0,0);");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db,
                          int versionAnterior, int versionNueva) {
        //NOTA: Por simplicidad, se elimina la tabla existente y
        // secrea de nuevo vacía con el nuevo formato
        db.execSQL("DROP TABLE IF EXISTS TaskHistoryLog");
        db.execSQL("DROP TABLE IF EXISTS TaskHistory");
        db.execSQL("DROP TABLE IF EXISTS Task");
        db.execSQL("DROP TABLE IF EXISTS Last");
        db.execSQL("DROP TABLE IF EXISTS Employee");
        db.execSQL("DROP TABLE IF EXISTS Team");

        db.execSQL(createTaskHistoryLog);
        db.execSQL(createTaskHistory);
        db.execSQL(createTask);
        db.execSQL(createLast);
        db.execSQL(createEmployee);
        db.execSQL(createTeam);
        db.execSQL(createTrTHU);
        db.execSQL("INSERT INTO Last VALUES (1,0,0,0);");
    }
}
