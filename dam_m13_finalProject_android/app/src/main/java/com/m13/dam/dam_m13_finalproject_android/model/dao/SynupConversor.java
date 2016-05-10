package com.m13.dam.dam_m13_finalproject_android.model.dao;

import android.app.Activity;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.m13.dam.dam_m13_finalproject_android.model.pojo.Employee;
import com.m13.dam.dam_m13_finalproject_android.model.pojo.Task;
import com.m13.dam.dam_m13_finalproject_android.model.pojo.TaskHistory;

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class SynupConversor {
    public static final String BD_NAME = "SYNUP_BD5";
    public static final int BD_VERSION = 1;
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

    public Cursor getCursorTaskHistoryLog (int first) {
        SQLiteDatabase db = helper.getReadableDatabase();

        return db.query(true,
                "TaskHistoryLog",
                new String[]{"id", "id_taskHistory", "operation", "when"},
                "id > ?",
                new String[] { String.valueOf(first)},
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

    public void updateTask(Task t) throws Exception{
        SQLiteDatabase db = helper.getReadableDatabase();

        ContentValues args = new ContentValues();

        args.put("id_team", t.getId_team());
        args.put("code", t.getCode());
        args.put("priorityDate", dataFormat.format(t.getPriorityDate()));
        args.put("description", t.getDescription());
        args.put("localization", t.getLocalization());
        args.put("project", t.getProject());
        db.update("TaskHistory", args, "id=" + t.getId(), null);

    }

    public void deleteTask(int id) throws Exception{
        SQLiteDatabase db = helper.getReadableDatabase();
        db.delete("Task", "id=" + id, null);
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
            Log.e("ERROR_BUG", "Error on insert the user");
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


    public boolean updateLastTaskHistory(int lastTaskHistory) {
        SQLiteDatabase db = helper.getReadableDatabase();

        ContentValues args = new ContentValues();

        args.put("employeTaskLog", lastTaskHistory);
        try {
            db.update("Last", args, "id=0", null);
        } catch (Exception e){
            return false;
        }
        return true;
    }

    public int getLastServerTaskHistoryLog() {
        SQLiteDatabase db = helper.getReadableDatabase();

        Cursor c = db.query(true,
                "Last",
                new String[]{"employeTaskLog"},
                "id = ?",
                new String[]{String.valueOf(0)},
                null,
                null,
                null,
                null);

        if(c==null || c.getCount()==0){
            return -1;
        }
        c.moveToFirst();
        return c.getInt(0);

    }

    public int getLastLocalTaskHistoryLog() {
        SQLiteDatabase db = helper.getReadableDatabase();

        Cursor c = db.query(true,
                "TaskHistoryLog",
                new String[]{"max(id)"},
                null,
                null,
                null,
                null,
                null,
                null);

        if(c==null || c.getCount()==0){
            return -1;
        }
        c.moveToFirst();
        return c.getInt(0);

    }

    public int getLastLocalTask() {
        SQLiteDatabase db = helper.getReadableDatabase();

        Cursor c = db.query(true,
                "Last",
                new String[]{"employeeLog"},
                "id = ?",
                new String[]{String.valueOf(0)},
                null,
                null,
                null,
                null);

        if(c==null || c.getCount()==0){
            return -1;
        }
        c.moveToFirst();
        return c.getInt(0);
    }

    public Employee getEmployee(int id) {
        SQLiteDatabase db = helper.getReadableDatabase();

        Cursor c = db.query(true,
                "Employee",
                new String[]{"id", "nif", "name", "surname", "phone", "email", "adress","username","password"},
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

        return new Employee(c.getInt(0),c.getString(1),c.getString(2), c.getString(3),c.getString(4),c.getString(5),c.getString(6),c.getString(7),c.getString(8));

    }


    public Employee getEmployee(String userName, String password) {
        SQLiteDatabase db = helper.getReadableDatabase();

        Cursor c = db.query(true,
                "Employee",
                new String[]{"id", "nif", "name", "surname", "phone", "email", "adress","username","password"},
                "username = ? and password = ? ",
                new String[]{userName, password},
                null,
                null,
                null,
                null);

        if(c==null || c.getCount()==0){
            return null;
        }
        c.moveToFirst();

        return new Employee(c.getInt(0),c.getString(1),c.getString(2), c.getString(3),c.getString(4),c.getString(5),c.getString(6),c.getString(7),c.getString(8));

    }
}
