package com.m13.dam.dam_m13_finalproject_android.model.dao;

import android.app.Activity;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.m13.dam.dam_m13_finalproject_android.model.pojo.Employee;
import com.m13.dam.dam_m13_finalproject_android.model.pojo.Last;
import com.m13.dam.dam_m13_finalproject_android.model.pojo.Task;
import com.m13.dam.dam_m13_finalproject_android.model.pojo.TaskHistory;
import com.m13.dam.dam_m13_finalproject_android.model.pojo.Team;
import com.m13.dam.dam_m13_finalproject_android.model.pojo.TeamHistory;

import java.util.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;


/**
 * Created by adri on
 */

/**
 * Modified by jesus on 12/05/2016
 *
 * Script ordered by tables in SynupSqliteHelper script
 *
 * Added get 'Last' table functions
 *      * employeeLog
 *      * teamLog
 * Added update 'Last' table functions
 *      * taskLog
 *      * taskHistoryLog
 *      * employeeLog
 *      * teamLog
 * Added CRUD Employee functions
 * Added CRUD Team functions
 *
 * Modified field 'EmployeeTaskLog' field by 'TaskHistoryLog' in 'Last' table.
 * Modified field 'id' in 'Employee' table. now 'nif' is 'id' like in centralized DB
 * Modified some warning string in catch block
 */

/* ORM Lite */
public class SynupConversor {
    public static final String BD_NAME = "SYNUP_BD55";
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


    //TASK HISTORY LOG -> INTERNAL LOG ABOUT TASK HISTORY MODIFIED BY USER
    public Cursor getCursorTaskHistoryLog (int first) {
        SQLiteDatabase db = helper.getReadableDatabase();

        return db.query(true,
                "TaskHistoryLog",
                new String[]{"id", "id_taskHistory", "operation", "'when'"},
                "id > ?",
                new String[]{String.valueOf(first)},
                null,
                null,
                null,
                null);
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


    // TASK HISTORY LOCAL DB FUNCTIONS
    // GET BY ID
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
            return new TaskHistory(c.getInt(0),c.getString(1),c.getString(2),
                    new Date(dataFormat.parse(c.getString(3)).getTime()),
                    c.getString(4) != null ? new Date(dataFormat.parse(c.getString(4)).getTime()) : null,
                    c.getString(5), c.getInt(6));

        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    public TaskHistory getTaskHistoryByTask(String code) {
        SQLiteDatabase db = helper.getReadableDatabase();

        Cursor c = db.query(true,
                "TaskHistory",
                new String[]{"id", "id_employee", "id_task", "startDate", "finishDate", "comment", "isFinished"},
                "id_task = ?",
                new String[]{code},
                null,
                null,
                "id DESC",
                null);

        if(c==null || c.getCount()==0){
            return null;
        }
        c.moveToFirst();

        try {
            return new TaskHistory(
                    c.getInt(0),
                    c.getString(1),
                    c.getString(2),
                    new Date(dataFormat.parse(c.getString(3)).getTime()),
                    c.getString(4) != null ? new Date(dataFormat.parse(c.getString(4)).getTime()) : null,
                    c.getString(5) != null ? c.getString(5) : "" ,
                    c.getInt(6));

        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    //SAVE -> INSERT
    public long saveTaskHistory(TaskHistory th) {
        long index = -1;
        SQLiteDatabase db = helper.getWritableDatabase();
        if(getTaskHistory(th.getId()) == null) {
            ContentValues dades = new ContentValues();

            dades.put("id", th.getId());
            if(th.getId_employee() != null)
            dades.put("id_employee", th.getId_employee());
            if(th.getId_task() != null)
            dades.put("id_task", th.getId_task());
            if(th.getStartDate() != null)
            dades.put("startDate", dataFormat.format(th.getStartDate()));
            if(th.getFinishDate() != null)
            dades.put("finishDate", dataFormat.format(th.getFinishDate()));
            if(th.getComment() != null)
            dades.put("comment", th.getComment());
            dades.put("isFinished", th.getIsFinished());

            try {
                index = db.insertOrThrow("TaskHistory", null, dades);
            } catch (Exception e) {
                Log.e("ERROR_BUG", "Error on insert the task history register");
            }
        }
        return index;
    }

    /**
     * UPDATE
     * @param id
     * @param date
     * @return
     */

    public boolean updateTaskHistory(int id, Date date, int finish){
        SQLiteDatabase db = helper.getReadableDatabase();

        ContentValues args = new ContentValues();
        args.put("finishDate", dataFormat.format(date));
        args.put("isFinished", finish);
        try {
            db.update("TaskHistory", args, "id=" + id, null);
        } catch (Exception e){
            return false;
        }
        return true;
    }

    /**
     * DELETE
     * @param id
     * @return
     */
    public boolean deleteTaskHistory(int id){
        SQLiteDatabase db = helper.getReadableDatabase();

        try {
            db.delete("TaskHistory", "id=" + id, null);
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    public Task getTaskAcctived(String code) {

        SQLiteDatabase db = helper.getReadableDatabase();

        String sql = "SELECT t.id_team, t.code, t.priorityDate, t.description, t.localization, t.project, t.name, t.priority, t.state " +
                "FROM Task t " +
                "INNER JOIN TaskHistory th ON th.id_task=t.code " +
                "WHERE th.finishDate is null and th.id_employee = ? ORDER BY t.name";

        Cursor c = db.rawQuery(sql, new String[]{code});

        if(c==null || c.getCount()==0){
            return null;
        }
        c.moveToFirst();
        try {
            return new Task(c.getString(0),c.getString(1), new Date(dataFormat.parse(c.getString(2)).getTime()),
                    c.getString(3),c.getString(4),c.getString(5),c.getString(6), c.getInt(7), c.getInt(8));
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    // TASK LOCAL DB FUNCTIONS
    // GET BY ID
    public Task getTask(String code) {
        SQLiteDatabase db = helper.getReadableDatabase();

        Cursor c = db.query(true,
                "Task",
                new String[]{"id_team", "code", "priorityDate", "description", "localization", "project", "name","priority", "state"},
                "code = ?",
                new String[]{String.valueOf(code)},
                null,
                null,
                null,
                null);

        if(c==null || c.getCount()==0){
            return null;
        }
        c.moveToFirst();

        try {
            return new Task(c.getString(0),c.getString(1), new Date(dataFormat.parse(c.getString(2)).getTime()),
                    c.getString(3),c.getString(4),c.getString(5),c.getString(6),c.getInt(7),c.getInt(8));
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    //GET BY TEAM
    public Cursor getTaskByTeam(String code, String taskName)
    {
        SQLiteDatabase db = helper.getReadableDatabase();

        Cursor c = db.query(true,
                "Task",
                new String[]{"id_team", "code", "priorityDate", "description", "localization", "project", "name", "priority", "state"},
                "id_team = ? and name LIKE ? ",
                new String[]{code, "%" + taskName + "%"},
                null,
                null,
                "priority DESC",
                null);

        return c;
    }

    public Cursor getTaskByEmployee(String nif, String taskName){


        SQLiteDatabase db = helper.getReadableDatabase();

        String sql = "SELECT t.id_team, t.code, t.priorityDate, t.description, t.localization, t.project, t.name, t.priority, t.state " +
                "FROM Task t INNER JOIN TaskHistory th ON th.id_task=t.code " +
                "WHERE th.finishDate is null and th.id_employee = ? and t.name LIKE ? " +
                "ORDER BY t.priority DESC";

        Cursor c = db.rawQuery(sql, new String[]{nif, "%" + taskName + "%"});

        return c;
    }


    //SAVE -> INSERT
    public long saveTask(Task t) {
        long index = -1;
        SQLiteDatabase db = helper.getWritableDatabase();

        if(getTask(t.getCode()) == null) {

            ContentValues dades = new ContentValues();

            if(t.getId_team() != null)
            dades.put("id_team", t.getId_team());
            if(t.getCode() != null)
            dades.put("code", t.getCode());
            if(t.getPriorityDate() != null)
            dades.put("priorityDate", dataFormat.format(t.getPriorityDate()));
            if(t.getDescription() != null)
            dades.put("description", t.getDescription());
            if(t.getLocalization() != null)
            dades.put("localization", t.getLocalization());
            if(t.getProject() != null)
            dades.put("project", t.getProject());
            if(t.getName() != null)
            dades.put("name", t.getName());
            dades.put("priority", t.getPriority());
            dades.put("state", t.getState());

            try {
                index = db.insertOrThrow("Task", null, dades);
            } catch (Exception e) {
                Log.e("ERROR_BUG", "Error on insert the task");
            }
        }
        return index;
    }

    // UPDATE
    public void updateTask(Task t){
        SQLiteDatabase db = helper.getReadableDatabase();

        ContentValues args = new ContentValues();

        args.put("id_team", t.getId_team());
        args.put("code", t.getCode());
        args.put("priorityDate", dataFormat.format(t.getPriorityDate()));
        args.put("description", t.getDescription());
        args.put("localization", t.getLocalization());
        args.put("project", t.getProject());
        args.put("name", t.getName());
        args.put("priority", t.getPriority());
        args.put("state", t.getState());
        db.update("Task", args, "code='" + t.getCode() + "'", null);

    }

    //DELETE TASK
    public void deleteTask(String code) throws Exception{
        SQLiteDatabase db = helper.getReadableDatabase();
        db.delete("Task", "code='" + code + "'", null);
    }

    //EMPLOYEE LOCAL DB FUNCTIONS
    //GET BY ID
    public Employee getEmployee(String nif) {
        SQLiteDatabase db = helper.getReadableDatabase();

        Cursor c = db.query(true,
                "Employee",
                new String[]{"nif", "name", "surname", "phone", "email", "adress", "username", "password"},
                "nif = ?",
                new String[]{nif},
                null,
                null,
                null,
                null);

        if(c==null || c.getCount()==0){
            return null;
        }
        c.moveToFirst();

        return new Employee(c.getString(0),c.getString(1),
                c.getString(2),c.getString(3),c.getString(4),c.getString(5),c.getString(6),c.getString(7));
    }

    //GET BY USERNAME AND LOGIN
    public Employee getEmployee(String userName, String password) {
        SQLiteDatabase db = helper.getReadableDatabase();

        Cursor c = db.query(true,
                "Employee",
                new String[]{"nif", "name", "surname", "phone", "email", "adress", "username", "password"},
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

        return new Employee(c.getString(0),c.getString(1), c.getString(2),c.getString(3),
                c.getString(4),c.getString(5),c.getString(6),c.getString(7));
    }

    //SAVE -> INSERT
    public long saveEmployee(Employee e) {
        long index = -1;
        SQLiteDatabase db = helper.getWritableDatabase();
        if(getEmployee(e.getNif()) == null) {
            ContentValues args = new ContentValues();

            if(e.getNif() != null)
            args.put("nif", e.getNif());
            if(e.getName() != null)
            args.put("name", e.getName());
            if(e.getSurname() != null)
            args.put("surname", e.getSurname());
            if(e.getAdress() != null)
            args.put("adress", e.getAdress());
            if(e.getEmail() != null)
            args.put("email", e.getEmail());
            if(e.getPassword() != null)
            args.put("password", e.getPassword());
            if(e.getUsername() != null)
            args.put("username", e.getUsername());
            if(e.getPhone() != null)
            args.put("phone", e.getPhone());

            try {
                index = db.insertOrThrow("Employee", null, args);
            } catch (Exception ex) {
                Log.e("ERROR_BUG", "Error on insert the Employee");
            }
        }
        return index;
    }

    //UPDATE
    public void updateEmployee(Employee e) throws Exception{
        SQLiteDatabase db = helper.getReadableDatabase();

        ContentValues args = new ContentValues();

        args.put("nif", e.getNif());
        args.put("name", e.getName());
        args.put("surname", e.getSurname());
        args.put("adress", e.getAdress());
        args.put("email",e.getEmail());
        args.put("password", e.getPassword());
        args.put("username", e.getUsername());
        args.put("phone", e.getPhone());

        db.update("Employee", args, "nif='" + e.getNif() + "'", null);
    }

    //DELETE
    public void deleteEmployee(String nif) throws Exception{
        SQLiteDatabase db = helper.getReadableDatabase();
        db.delete("Employee", "nif='" + nif + "'", null);
    }

    //TEAM LOCAL DB FUNCTIONS
    //GET BY 'CODE'
    public Team getTeam(String code) {
        SQLiteDatabase db = helper.getReadableDatabase();

        Cursor c = db.query(true,
                "Team",
                new String[]{"code", "name"},
                "code = ?",
                new String[]{String.valueOf(code)},
                null,
                null,
                null,
                null);

        if(c==null || c.getCount()==0){
            return null;
        }
        c.moveToFirst();

        return new Team(c.getString(0),c.getString(1));
    }

    //GET BY USER
    public Cursor getTeamsByUser(String nif, String taskName)
    {
        SQLiteDatabase db = helper.getReadableDatabase();

        String sql = "SELECT te.code, te.name " +
                "FROM Team te " +
                "INNER JOIN TeamHistory teh ON te.code = teh.id_team " +
                "INNER JOIN Task t ON t.id_team = te.code " +
                "WHERE teh.id_employee = ? and t.name LIKE ? and teh.exitDate is null " +
                "GROUP BY te.code, te.name";

        Cursor c = db.rawQuery(sql, new String[]{nif, "%" + taskName + "%"});

        return c;
    }

    //SAVE -> INSERT
    public long saveTeam(Team t) {
        long index = -1;
        SQLiteDatabase db = helper.getWritableDatabase();
        if(getTeam(t.getCode()) == null) {
            ContentValues args = new ContentValues();

            if(t.getCode() != null)
            args.put("code", t.getCode());
            if(t.getName() != null)
            args.put("name", t.getName());

            try {
                index = db.insertOrThrow("Team", null, args);
            } catch (Exception ex) {
                Log.e("ERROR_BUG", "Error on insert the Team");
            }
        }
        return index;
    }

    //UPDATE
    public void updateTeam(Team t) throws Exception{
        SQLiteDatabase db = helper.getReadableDatabase();

        ContentValues args = new ContentValues();

        args.put("code", t.getCode());
        args.put("name", t.getName());
        db.update("Team", args, "code='" + t.getCode() + "'", null);
    }

    //DELETE
    public void deleteTeam(String code) throws Exception{
        SQLiteDatabase db = helper.getReadableDatabase();
        db.delete("Team", "code='" + code + "'", null);
    }

    //TEAMHISTORY LOCAL DB FUNCTIONS
    public TeamHistory getTeamHitory(int id){
        SQLiteDatabase db = helper.getReadableDatabase();

        Cursor c = db.query(true,
                "TeamHistory",
                new String[]{"id", "id_employee", "id_team", "entranceDay", "exitDate"},
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

        try{
            return new TeamHistory(c.getInt(0),c.getString(1),c.getString(2),
                    new Date(dataFormat.parse(c.getString(3)).getTime()),
                    new Date(dataFormat.parse(c.getString(4)).getTime()));
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }
    //SAVE -> INSERT
    public long saveTeamHistory(TeamHistory teh) {
        long index = -1;
        SQLiteDatabase db = helper.getWritableDatabase();
        if(getTeamHitory(teh.getId()) == null) {
            ContentValues args = new ContentValues();

            args.put("id", teh.getId());
            if(teh.getId_employee() != null)
                args.put("id_employee", teh.getId_employee());
            if(teh.getId_team() != null)
                args.put("id_team", teh.getId_team());
            if(teh.getEntranceDay() != null)
                args.put("entranceDay", dataFormat.format(teh.getEntranceDay()));
            if(teh.getExitDate() != null)
                args.put("exitDate", dataFormat.format(teh.getExitDate()));

            try {
                index = db.insertOrThrow("TeamHistory", null, args);
            } catch (Exception ex) {
                Log.e("ERROR_BUG", "Error on insert the Team");
            }
        }
        return index;
    }

    //UPDATE
    public void updateTeamHistory(TeamHistory teh) throws Exception{
        SQLiteDatabase db = helper.getReadableDatabase();

        ContentValues args = new ContentValues();

        args.put("id_employee", teh.getId_employee());
        args.put("id_team", teh.getId_team());

        db.update("TeamHistory", args, "id=" + teh.getId(), null);
    }

    //DELETE
    public void deleteTeamHistory(int id) throws Exception{
        SQLiteDatabase db = helper.getReadableDatabase();
        db.delete("TeamHistory", "id=" + id, null);
    }

    //LAST LOCAL DB FUNCTIONS -- ONLY GET/UPDATE

    public Last saveLast(String code) throws Exception{

        SQLiteDatabase db = helper.getReadableDatabase();

        ContentValues args = new ContentValues();

        args.put("employee", code);
        db.insertOrThrow("Last", null, args);

        Cursor c = db.query(true,
                "Last",
                new String[]{"id","employee","employeeLog","taskLog","taskHistoryLog","teamLog","teamHistoryLog","taskHistoryLogServer"},
                "employee = ?",
                new String[]{code},
                null,
                null,
                null,
                null);

        c.moveToFirst();
        return new Last(c.getInt(0),c.getString(1),c.getInt(2), c.getInt(3),c.getInt(4),c.getInt(5),c.getInt(6),c.getInt(7));
    }

    //GET
    public Last getLast(String code) throws Exception{
        SQLiteDatabase db = helper.getReadableDatabase();

        Cursor c = db.query(true,
                "Last",
                new String[]{"id","employee","employeeLog","taskLog","taskHistoryLog","teamLog","teamHistoryLog","taskHistoryLogServer"},
                "employee = ?",
                new String[]{code},
                null,
                null,
                null,
                null);

        if(c==null || c.getCount()==0){
            return saveLast(code);
        }
        c.moveToFirst();
        return new Last(c.getInt(0),c.getString(1),c.getInt(2), c.getInt(3),c.getInt(4),c.getInt(5),c.getInt(6),c.getInt(7));
    }


    public int getLastServerTaskHistoryLog() {
        SQLiteDatabase db = helper.getReadableDatabase();

        Cursor c = db.query(true,
                "Last",
                new String[]{"taskHistoryLog"},
                "id = ?",
                new String[]{String.valueOf(1)},
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

    //UPDATE
    public boolean updateLastTaskHistory(String code, int lastTaskHistory) {
        SQLiteDatabase db = helper.getReadableDatabase();

        ContentValues args = new ContentValues();

        args.put("taskHistoryLog", lastTaskHistory);
        try {
            db.update("Last", args, "employee='"+code+"'", null);
        } catch (Exception e){
            return false;
        }
        return true;
    }

    public boolean updateLastTask(String code, int lastTask) {
        SQLiteDatabase db = helper.getReadableDatabase();

        ContentValues args = new ContentValues();

        args.put("taskLog", lastTask);
        try {
            db.update("Last", args, "employee='"+code+"'", null);
        } catch (Exception e){
            return false;
        }
        return true;
    }

    public boolean updateLastTeam(String code, int lastTeam) {
        SQLiteDatabase db = helper.getReadableDatabase();

        ContentValues args = new ContentValues();

        args.put("teamLog", lastTeam);
        try {
            db.update("Last", args, "employee='"+code+"'", null);
        } catch (Exception e){
            return false;
        }
        return true;
    }

    public boolean updateLastEmployee(String code, int lastEmployee) {
        SQLiteDatabase db = helper.getReadableDatabase();

        ContentValues args = new ContentValues();

        args.put("employeeLog", lastEmployee);
        try {
            db.update("Last", args, "employee='"+code+"'", null);
        } catch (Exception e){

            return false;
        }
        return true;
    }

    public boolean updateLastTeamHistory(String code, int lastTeamHistory) {
        SQLiteDatabase db = helper.getReadableDatabase();

        ContentValues args = new ContentValues();

        args.put("teamHistoryLog", lastTeamHistory);

        try {
            db.update("Last", args, "employee='"+code+"'", null);
        } catch (Exception e){
            return false;
        }
        return true;
    }

    public boolean updateTaskHistoryLogServer(String code, int lastTaskHistoryLogServer) {
        SQLiteDatabase db = helper.getReadableDatabase();

        ContentValues args = new ContentValues();

        args.put("taskHistoryLogServer", lastTaskHistoryLogServer);

        try {
            db.update("Last", args, "employee='"+code+"'", null);
        } catch (Exception e){
            return false;
        }
        return true;
    }
}
