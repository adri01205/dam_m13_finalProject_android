package com.m13.dam.dam_m13_finalproject_android.model.dao;

import android.app.Activity;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.m13.dam.dam_m13_finalproject_android.model.pojo.Last;
import com.m13.dam.dam_m13_finalproject_android.model.pojo.Task;
import com.m13.dam.dam_m13_finalproject_android.model.pojo.TaskHistory;

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



//    (SQliteDatabase) db.query(
//            "mytable" /* table */,
//            new String[] { "name" } /* columns */,
//            "id = ?" /* where or selection */,
//            new String[] { "john" } /* selectionArgs i.e. value to replace ? */,
//            null /* groupBy */,
//            null /* having */,
//            null /* orderBy */
//            );

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

    public long saveTaskHistory(TaskHistory th) {
        long index = -1;
        SQLiteDatabase db = helper.getWritableDatabase();
        ContentValues dades = new ContentValues();

        dades.put("id", th.getId());
        dades.put("id_employee", th.getId_employee());
        dades.put("id_task", th.getId_task());
        dades.put("startDate", dataFormat.format(th.getStartDate()));
        dades.put("finishDate", dataFormat.format(th.getFinishDate()));
        dades.put("comment", th.getComment());
        dades.put("isFinished", th.getIsFinished());

        try {
            index = db.insertOrThrow("TaskHistory", null, dades);
        }
        catch(Exception e) {
            Log.e("ERROR_BUG", "Error on insert the user");
        }
        return index;
    }

    public TaskHistory getTaskHistory(int id) {
        SQLiteDatabase db = helper.getReadableDatabase();

        Cursor c = db.query(true,
                "TaskHistory",
                new String[]{"id", "id_employee", "id_task", "startDate", "finishDate", "comment", "isFinished"},
                "id = ?",
                new String[]{String.valueOf(id)},
                null,
                null,
                null,
                null);

        if(c==null || c.getCount()==0){
            return null;
        }
        c.moveToFirst();

        try {
            return new TaskHistory(c.getInt(0),c.getInt(1),c.getInt(2), new java.sql.Date(dataFormat.parse(c.getString(3)).getTime()), new java.sql.Date(dataFormat.parse(c.getString(4)).getTime()),c.getString(5),c.getInt(6));
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     *
     * @param id
     * @param date
     * @param isFinished 0 -> not Finished, 1 -> Finished
     * @return
     */
    public boolean updateTaskHistory(int id, Date date, int isFinished){
        SQLiteDatabase db = helper.getReadableDatabase();

        ContentValues args = new ContentValues();
        args.put("finishDate", dataFormat.format(date));
        args.put("isFinished", isFinished);
        try {
            db.update("TaskHistory", args, "id=" + id, null);
        } catch (Exception e){
            return false;
        }
        return true;
    }

    public boolean deleteTaskHistory(int id){
        SQLiteDatabase db = helper.getReadableDatabase();

        try {
            db.delete("TaskHistory", "id=" + id, null);
        } catch (Exception e) {
            return false;
        }
        return true;
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

    public Task getTask(int id) {
        SQLiteDatabase db = helper.getReadableDatabase();

        Cursor c = db.query(true,
                "Task",
                new String[]{"id", "id_team", "code", "priorityDate", "description", "localization", "project"},
                "id = ?",
                new String[]{String.valueOf(id)},
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


    public boolean updateLast(int id, int lastTaskHistory) {
        SQLiteDatabase db = helper.getReadableDatabase();

        ContentValues args = new ContentValues();

        args.put("employeTaskLog", lastTaskHistory );
        try {
            db.update("Last", args, "id=" + id, null);
        } catch (Exception e){
            return false;
        }
        return true;
    }



}
