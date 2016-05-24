package com.m13.dam.dam_m13_finalproject_android.model.services;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

import com.m13.dam.dam_m13_finalproject_android.R;
import com.m13.dam.dam_m13_finalproject_android.controller.interfaces.AsyncTaskCompleteListener;
import com.m13.dam.dam_m13_finalproject_android.model.dao.SynupConversor;
import com.m13.dam.dam_m13_finalproject_android.model.dao.SynupSharedPreferences;
import com.m13.dam.dam_m13_finalproject_android.model.pojo.Employee;
import com.m13.dam.dam_m13_finalproject_android.model.pojo.Last;
import com.m13.dam.dam_m13_finalproject_android.model.pojo.LastServer;
import com.m13.dam.dam_m13_finalproject_android.model.pojo.ReturnObject;
import com.m13.dam.dam_m13_finalproject_android.model.pojo.Task;
import com.m13.dam.dam_m13_finalproject_android.model.pojo.TaskHistory;
import com.m13.dam.dam_m13_finalproject_android.model.pojo.Team;
import com.m13.dam.dam_m13_finalproject_android.model.pojo.TeamHistory;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class UpdateLocalAsync  extends AsyncTask<String, Void, Void> {


    private String Content;
    private ReturnObject ret;

    private String employeeId;
    private String serverURLLastServer = "http://"+ Connection.getDomain()+"Last/";
    private String serverURLTasksI = "http://"+ Connection.getDomain()+"TaskInserted/";
    private String serverURLTasksU = "http://"+ Connection.getDomain()+"TaskUpdated/";
    private String serverURLTasksD = "http://"+ Connection.getDomain()+"TaskDeleted/";
    private String serverURLTeamI = "http://"+ Connection.getDomain()+"TeamInserted/";
    private String serverURLTeamU = "http://"+ Connection.getDomain()+"TeamUpdated/";
    private String serverURLTeamD = "http://"+ Connection.getDomain()+"TeamDeleted/";
    private String serverURLEmployeeI = "http://"+ Connection.getDomain()+"EmployeeInserted/";
    private String serverURLEmployeeU = "http://"+ Connection.getDomain()+"EmployeeUpdated/";
    private String serverURLEmployeeD = "http://"+ Connection.getDomain()+"EmployeeDeleted/";
    private String serverURLTasksHistoryI = "http://"+ Connection.getDomain()+"TaskHistoryInserted/";
    private String serverURLTasksHistoryU = "http://"+ Connection.getDomain()+"TaskHistoryUpdated/";
    private String serverURLTasksHistoryD = "http://"+ Connection.getDomain()+"TaskHistoryDeleted/";
    private String serverURLTeamHistoryI = "http://"+ Connection.getDomain()+"TeamHistoryInserted/";
    private String serverURLTeamHistoryU = "http://"+ Connection.getDomain()+"TeamHistoryUpdated/";
    private String serverURLTeamHistoryD = "http://"+ Connection.getDomain()+"TeamHistoryDeleted/";

    private ProgressDialog progressDialog;
    private Activity context;
    private AsyncTaskCompleteListener<ReturnObject> listener;
    private SynupConversor conversor;
    private String code = "";

    public UpdateLocalAsync(Activity context, AsyncTaskCompleteListener<ReturnObject> listener)
    {
        this.context = context;
        this.listener = listener;
        this.employeeId = SynupSharedPreferences.getUserLoged(context);
        ret = new ReturnObject(200,"OK",ReturnObject.UPDATE_LOCAL_CALLBACK);
        progressDialog = new ProgressDialog(context);
        progressDialog.setMessage("Connecting to server..");
        progressDialog.setIndeterminate(false);
        progressDialog.setCancelable(false);
        conversor = new SynupConversor(context);
    }

    protected void onPreExecute() {
        // NOTE: You can call UI Element here.

        progressDialog.setMessage("Please wait..");
        progressDialog.show();
    }

    // Call after onPreExecute method
        protected Void doInBackground(String... params) {
            if(!Connection.isConnected()){
                ret.setCode(301);
                ret.setMessage(context.getResources().getString(R.string.ERROR_NO_CONNECTION));
                return null;
            }

        HttpURLConnection conn = null;
        boolean smtingDone = false;
        int status = 500;
        try {
            code = params[0];
            Last lastLocal = conversor.getLast(code);

            conn = (HttpURLConnection) new URL(serverURLLastServer).openConnection();
            conn.setRequestProperty("Accept-Charset", "UTF-8");

            status = conn.getResponseCode();

            ArrayList<LastServer> lastServer = (ArrayList<LastServer>) MarshallingUnmarshalling.jsonJacksonUnmarshalling(LastServer.class, conn.getInputStream());
            conn.disconnect();
            int first = lastLocal.getTaskLog();
            int last = lastServer.get(0).getTasklogId();
            if (last > first) {
                updateTask(first, last);
                smtingDone = true;
            }

            first = lastLocal.getTaskHistoryLog();
            last = lastServer.get(0).getTaskhistlogId();
            if (last > first) {
                updateTaskHistory(first, last);
                 smtingDone = true;
            }

            first = lastLocal.getTeamLog();
            last = lastServer.get(0).getTeamlogId();
            if (last > first) {
                updateTeam(first, last);
                smtingDone = true;
            }

            first = lastLocal.getEmployeeLog();
            last = lastServer.get(0).getEmplogId();
            if (last > first) {
                updateEmployee(first, last);
                smtingDone = true;
            }

            first = lastLocal.getTeamHistoryLog();
            last = lastServer.get(0).getTeamhistlogId();
            if (last > first) {
                updateTeamHistory(first, last);
                smtingDone = true;
            }

            if(!smtingDone){
                ret.setCode(201);
            }

        } catch (MalformedURLException e) {
            ret.setCode(406);
            ret.setMessage(e.toString());
        } catch (IOException e) {
            ret.setCode(407);
            ret.setMessage("connection status: " + status + "\n" + e.toString());
        } catch (Exception e) {
            ret.setCode(408);
            ret.setMessage(e.toString());
        } finally
        {

            try {
                conn.disconnect();
            } catch (Exception e) {

            }
        }

        return null;
    }

    @Override
    protected void onPostExecute(Void v) {
        // Close progress dialog
        progressDialog.dismiss();
        listener.onTaskComplete(ret);
    }

    private void updateTask(int first, int last) throws Exception {
        updateITask(first, last);
        updateUTask(first, last);
        updateDTask(first, last);

        conversor.updateLastTask(code, last);

    }

    private void updateITask(int first, int last) throws IOException {
        // Defined URL  where to send data
        URL url = new URL(serverURLTasksI + employeeId + "/" + first + "/" + last);

        // Send POST data request
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestProperty("Accept-Charset", "UTF-8");

        ArrayList<Task> tasks = (ArrayList<Task>) MarshallingUnmarshalling.jsonJacksonUnmarshalling(Task.class, conn.getInputStream());


        for (Task t : tasks) {
            conversor.saveTask(t);
        }

        conn.disconnect();
    }

    private void updateUTask(int first, int last) throws Exception {
        // Defined URL  where to send data

        URL url = new URL(serverURLTasksU + employeeId + "/" + first + "/" + last);

        // Send POST data request
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestProperty("Accept-Charset", "UTF-8");

        ArrayList<Task> tasks = (ArrayList<Task>) MarshallingUnmarshalling.jsonJacksonUnmarshalling(Task.class, conn.getInputStream());


        for (Task t : tasks) {
            conversor.updateTask(t);
        }

        conn.disconnect();
    }

    private void updateDTask(int first, int last) throws Exception {
        // Defined URL  where to send data
        URL url = new URL(serverURLTasksD + employeeId + "/" + first + "/" + last);

        // Send POST data request
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestProperty("Accept-Charset", "UTF-8");

        ArrayList<Task> tasks = (ArrayList<Task>) MarshallingUnmarshalling.jsonJacksonUnmarshalling(Task.class, conn.getInputStream());


        for (Task t : tasks) {
            conversor.deleteTask(t.getCode());
        }

        conn.disconnect();
    }

    private void updateTaskHistory(int first, int last) throws Exception {
        updateITaskHistory(first, last);
        updateUTaskHistory(first, last);
        updateDTaskHistory(first, last);

        conversor.updateLastTaskHistory(code, last);

    }

    private void updateITaskHistory(int first, int last) throws IOException {
        // Defined URL  where to send data
        URL url = new URL(serverURLTasksHistoryI + employeeId + "/" + first + "/" + last);

        // Send POST data request
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestProperty("Accept-Charset", "UTF-8");

        ArrayList<TaskHistory> tasks = (ArrayList<TaskHistory>) MarshallingUnmarshalling.jsonJacksonUnmarshalling(TaskHistory.class, conn.getInputStream());

        for (TaskHistory t : tasks) {
            conversor.saveTaskHistory(t);
        }
    }

    private void updateUTaskHistory(int first, int last) throws Exception {
        // Defined URL  where to send data

        URL url = new URL(serverURLTasksHistoryU + employeeId + "/" + first + "/" + last);

        // Send POST data request
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestProperty("Accept-Charset", "UTF-8");

        ArrayList<TaskHistory> tasks = (ArrayList<TaskHistory>) MarshallingUnmarshalling.jsonJacksonUnmarshalling(TaskHistory.class, conn.getInputStream());


        for (TaskHistory t : tasks) {
            conversor.updateTaskHistory(t.getId(), t.getFinishDate(), t.getIsFinished());
        }

        conn.disconnect();
    }

    private void updateDTaskHistory(int first, int last) throws Exception {
        // Defined URL  where to send data
        URL url = new URL(serverURLTasksHistoryD + employeeId + "/" + first + "/" + last);

        // Send POST data request
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestProperty("Accept-Charset", "UTF-8");

        ArrayList<TaskHistory> tasks = (ArrayList<TaskHistory>) MarshallingUnmarshalling.jsonJacksonUnmarshalling(TaskHistory.class, conn.getInputStream());


        for (TaskHistory t : tasks) {
            conversor.deleteTaskHistory(t.getId());
        }

        conn.disconnect();
    }

    private void updateTeam(int first, int last) throws Exception {
        updateITeam(first, last);
        updateUTeam(first, last);
        updateDTeam(first, last);

        conversor.updateLastTeam(code, last);
    }

    private void updateITeam(int first, int last) throws IOException {
        // Defined URL  where to send data
        URL url = new URL(serverURLTeamI + employeeId + "/" + first + "/" + last);

        // Send POST data request
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestProperty("Accept-Charset", "UTF-8");

        ArrayList<Team> tasks = (ArrayList<Team>) MarshallingUnmarshalling.jsonJacksonUnmarshalling(Team.class, conn.getInputStream());

        for (Team t : tasks) {
            conversor.saveTeam(t);
        }
    }

    private void updateUTeam(int first, int last) throws Exception {
        // Defined URL  where to send data

        URL url = new URL(serverURLTeamU + employeeId + "/" + first + "/" + last);

        // Send POST data request
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestProperty("Accept-Charset", "UTF-8");

        ArrayList<Team> tasks = (ArrayList<Team>) MarshallingUnmarshalling.jsonJacksonUnmarshalling(Team.class, conn.getInputStream());


        for (Team t : tasks) {
            conversor.updateTeam(t);
        }

        conn.disconnect();
    }

    private void updateDTeam(int first, int last) throws Exception {
        // Defined URL  where to send data
        URL url = new URL(serverURLTeamD + employeeId + "/" + first + "/" + last);

        // Send POST data request
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestProperty("Accept-Charset", "UTF-8");
        int s = conn.getResponseCode();

        ArrayList<Team> tasks = (ArrayList<Team>) MarshallingUnmarshalling.jsonJacksonUnmarshalling(Team.class, conn.getInputStream());


        for (Team t : tasks) {
            conversor.deleteTeam(t.getCode());
        }

        conn.disconnect();
    }
    private void updateEmployee(int first, int last) throws Exception {
        updateIEmployee(first, last);
        updateUEmployee(first, last);
        updateDEmployee(first, last);

        conversor.updateLastEmployee(code, last);
    }

    private void updateIEmployee(int first, int last) throws IOException {
        // Defined URL  where to send data
        URL url = new URL(serverURLEmployeeI + first + "/" + last);

        // Send POST data request
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestProperty("Accept-Charset", "UTF-8");

        ArrayList<Employee> tasks = (ArrayList<Employee>) MarshallingUnmarshalling.jsonJacksonUnmarshalling(Employee.class, conn.getInputStream());

        for (Employee t : tasks) {
            conversor.saveEmployee(t);
        }
    }

    private void updateUEmployee(int first, int last) throws Exception {
        // Defined URL  where to send data

        URL url = new URL(serverURLEmployeeU + first + "/" + last);

        // Send POST data request
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestProperty("Accept-Charset", "UTF-8");

        ArrayList<Employee> tasks = (ArrayList<Employee>) MarshallingUnmarshalling.jsonJacksonUnmarshalling(Employee.class, conn.getInputStream());


        for (Employee t : tasks) {
            conversor.updateEmployee(t);
        }

        conn.disconnect();
    }

    private void updateDEmployee(int first, int last) throws Exception {
        // Defined URL  where to send data
        URL url = new URL(serverURLEmployeeD + first + "/" + last);

        // Send POST data request
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestProperty("Accept-Charset", "UTF-8");

        ArrayList<Employee> tasks = (ArrayList<Employee>) MarshallingUnmarshalling.jsonJacksonUnmarshalling(Employee.class, conn.getInputStream());


        for (Employee t : tasks) {
            conversor.deleteEmployee(t.getNif());
        }

        conn.disconnect();
    }

    private void updateTeamHistory(int first, int last) throws Exception {
        updateITeamHistory(first, last);
        updateUTeamHistory(first, last);
        updateDTeamHistory(first, last);

        conversor.updateLastTeamHistory(code, last);
    }

    private void updateITeamHistory(int first, int last) throws IOException {
        // Defined URL  where to send data
        URL url = new URL(serverURLTeamHistoryI + employeeId + "/" + first + "/" + last);

        // Send POST data request
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestProperty("Accept-Charset", "UTF-8");

        ArrayList<TeamHistory> teamHistories = (ArrayList<TeamHistory>) MarshallingUnmarshalling.jsonJacksonUnmarshalling(TeamHistory.class, conn.getInputStream());

        for (TeamHistory t : teamHistories) {
            conversor.saveTeamHistory(t);
        }
    }

    private void updateUTeamHistory(int first, int last) throws Exception {
        // Defined URL  where to send data

        URL url = new URL(serverURLTeamHistoryU + employeeId + "/" + first + "/" + last);

        // Send POST data request
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestProperty("Accept-Charset", "UTF-8");

        ArrayList<TeamHistory> teamHistories = (ArrayList<TeamHistory>) MarshallingUnmarshalling.jsonJacksonUnmarshalling(TeamHistory.class, conn.getInputStream());


        for (TeamHistory t : teamHistories) {
            conversor.updateTeamHistory(t);
        }

        conn.disconnect();
    }

    private void updateDTeamHistory(int first, int last) throws Exception {
        // Defined URL  where to send data
        URL url = new URL(serverURLTeamHistoryD + employeeId + "/" + first + "/" + last);

        // Send POST data request
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestProperty("Accept-Charset", "UTF-8");
        int s = conn.getResponseCode();

        ArrayList<TeamHistory> teamHistories = (ArrayList<TeamHistory>) MarshallingUnmarshalling.jsonJacksonUnmarshalling(TeamHistory.class, conn.getInputStream());


        for (TeamHistory t : teamHistories) {
            conversor.deleteTeamHistory(t.getId());
        }

        conn.disconnect();
    }

}