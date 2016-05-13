package com.m13.dam.dam_m13_finalproject_android.model.services;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

import com.m13.dam.dam_m13_finalproject_android.controller.interfaces.AsyncTaskCompleteListener;
import com.m13.dam.dam_m13_finalproject_android.model.dao.SynupConversor;
import com.m13.dam.dam_m13_finalproject_android.model.pojo.Employee;
import com.m13.dam.dam_m13_finalproject_android.model.pojo.LastServer;
import com.m13.dam.dam_m13_finalproject_android.model.pojo.ReturnObject;
import com.m13.dam.dam_m13_finalproject_android.model.pojo.Task;
import com.m13.dam.dam_m13_finalproject_android.model.pojo.TaskHistory;
import com.m13.dam.dam_m13_finalproject_android.model.pojo.Team;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class UpdateLocalAsync  extends AsyncTask<Void, Void, Void> {

    private String Content;
    private ReturnObject ret;

    private String serverURLLastServer = "http://"+ Connection.getDomain()+"Last/1";
    private String serverURLTasksI = "http://androidexample.com/media/webservice/JsonReturn.php";
    private String serverURLTasksU = "http://androidexample.com/media/webservice/JsonReturn.php";
    private String serverURLTasksD = "http://androidexample.com/media/webservice/JsonReturn.php";
    private String serverURLTeamI = "http://androidexample.com/media/webservice/JsonReturn.php";
    private String serverURLTeamU = "http://androidexample.com/media/webservice/JsonReturn.php";
    private String serverURLTeamD = "http://androidexample.com/media/webservice/JsonReturn.php";
    private String serverURLEmployeeI = "http://androidexample.com/media/webservice/JsonReturn.php";
    private String serverURLEmployeeU = "http://androidexample.com/media/webservice/JsonReturn.php";
    private String serverURLEmployeeD = "http://androidexample.com/media/webservice/JsonReturn.php";
    private String serverURLTasksHistoryI = "http://androidexample.com/media/webservice/JsonReturn.php";
    private String serverURLTasksHistoryU = "http://androidexample.com/media/webservice/JsonReturn.php";
    private String serverURLTasksHistoryD = "http://androidexample.com/media/webservice/JsonReturn.php";
    private ProgressDialog progressDialog;
    private Context context;
    private AsyncTaskCompleteListener<ReturnObject> listener;
    private SynupConversor conversor;

    public UpdateLocalAsync(Context context, AsyncTaskCompleteListener<ReturnObject> listener)
    {
        this.context = context;
        this.listener = listener;
        ret = new ReturnObject(200,"OK");
        progressDialog = new ProgressDialog(context);
        progressDialog.setMessage("Connecting to server..");
        progressDialog.setIndeterminate(false);
        progressDialog.setCancelable(true);
        conversor = new SynupConversor((Activity) context);
    }

    protected void onPreExecute() {
        // NOTE: You can call UI Element here.

        progressDialog.setMessage("Please wait..");
        progressDialog.show();
    }

    // Call after onPreExecute method
    protected Void doInBackground(Void... urls) {
        HttpURLConnection conn = null;
        boolean smtingDone = false;
        try {
            conn = (HttpURLConnection) new URL(serverURLLastServer).openConnection();
            conn.setDoOutput(true);
            ArrayList<LastServer> lastServer = (ArrayList<LastServer>) MarshallingUnmarshalling.jsonJacksonUnmarshalling(LastServer.class, conn.getInputStream());
            conn.disconnect();

            if (lastServer.get(0).getTasklogId() > conversor.getLastLocalTask()) {
                updateTask(lastServer.get(0).getTasklogId());
                smtingDone = true;
            }

            if(lastServer.get(0).getTaskhistlogId() > conversor.getLastLocalTaskHistoryLog()) {
                updateTaskHistory(lastServer.get(0).getTaskhistlogId());
                 smtingDone = true;
            }

            if (lastServer.get(0).getTeamlogId() > conversor.getLastLocalTeam()) {
                updateTaskHistory(lastServer.get(0).getTeamlogId());
                smtingDone = true;
            }

            if(lastServer.get(0).getEmplogId() > conversor.getLastLocalEmployee()) {
                updateEmployee(lastServer.get(0).getEmplogId());
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
            ret.setMessage(e.toString());
        } catch (Exception e) {
            ret.setCode(301);
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

    private void updateTask(int last) throws Exception {
        updateITask(last);
        updateUTask(last);
        updateDTask(last);

        conversor.updateLastTask(last);

    }

    private void updateITask(int last) throws IOException {
        // Defined URL  where to send data
        URL url = new URL(serverURLTasksI);

        // Send POST data request
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setDoOutput(true);

        ArrayList<Task> tasks = (ArrayList<Task>) MarshallingUnmarshalling.jsonJacksonUnmarshalling(Task.class, conn.getInputStream());


        for (Task t : tasks) {
            conversor.saveTask(t);
        }

        conn.disconnect();
    }

    private void updateUTask(int last) throws Exception {
        // Defined URL  where to send data

        URL url = new URL(serverURLTasksU);

        // Send POST data request
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setDoOutput(true);

        ArrayList<Task> tasks = (ArrayList<Task>) MarshallingUnmarshalling.jsonJacksonUnmarshalling(Task.class, conn.getInputStream());


        for (Task t : tasks) {
            conversor.updateTask(t);
        }

        conn.disconnect();
    }

    private void updateDTask(int last) throws Exception {
        // Defined URL  where to send data
        URL url = new URL(serverURLTasksD);

        // Send POST data request
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setDoOutput(true);

        ArrayList<Task> tasks = (ArrayList<Task>) MarshallingUnmarshalling.jsonJacksonUnmarshalling(Task.class, conn.getInputStream());


        for (Task t : tasks) {
            conversor.deleteTask(t.getCode());
        }

        conn.disconnect();
    }

    private void updateTaskHistory(int last) throws Exception {
        updateITaskHistory(last);
        updateUTaskHistory(last);
        updateDTaskHistory(last);

        conversor.updateLastTaskHistory(last);

    }

    private void updateITaskHistory(int last) throws IOException {
        // Defined URL  where to send data
        URL url = new URL(serverURLTasksHistoryI);

        // Send POST data request
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setDoOutput(true);

        ArrayList<TaskHistory> tasks = (ArrayList<TaskHistory>) MarshallingUnmarshalling.jsonJacksonUnmarshalling(TaskHistory.class, conn.getInputStream());

        for (TaskHistory t : tasks) {
            conversor.saveTaskHistory(t);
        }
    }

    private void updateUTaskHistory(int last) throws Exception {
        // Defined URL  where to send data

        URL url = new URL(serverURLTasksHistoryU);

        // Send POST data request
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setDoOutput(true);

        ArrayList<TaskHistory> tasks = (ArrayList<TaskHistory>) MarshallingUnmarshalling.jsonJacksonUnmarshalling(TaskHistory.class, conn.getInputStream());


        for (TaskHistory t : tasks) {
            conversor.updateTaskHistory(t.getId(), t.getFinishDate(), t.getIsFinished());
        }

        conn.disconnect();
    }

    private void updateDTaskHistory(int last) throws Exception {
        // Defined URL  where to send data
        URL url = new URL(serverURLTasksHistoryD);

        // Send POST data request
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setDoOutput(true);

        ArrayList<TaskHistory> tasks = (ArrayList<TaskHistory>) MarshallingUnmarshalling.jsonJacksonUnmarshalling(TaskHistory.class, conn.getInputStream());


        for (TaskHistory t : tasks) {
            conversor.deleteTaskHistory(t.getId());
        }

        conn.disconnect();
    }

    private void updateTeam(int last) throws Exception {
        updateITeam(last);
        updateUTeam(last);
        updateDTeam(last);

        conversor.updateLastTeam(last);
    }

    private void updateITeam(int last) throws IOException {
        // Defined URL  where to send data
        URL url = new URL(serverURLTeamI);

        // Send POST data request
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setDoOutput(true);

        ArrayList<Team> tasks = (ArrayList<Team>) MarshallingUnmarshalling.jsonJacksonUnmarshalling(Team.class, conn.getInputStream());

        for (Team t : tasks) {
            conversor.saveTeam(t);
        }
    }

    private void updateUTeam(int last) throws Exception {
        // Defined URL  where to send data

        URL url = new URL(serverURLTeamU);

        // Send POST data request
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setDoOutput(true);

        ArrayList<Team> tasks = (ArrayList<Team>) MarshallingUnmarshalling.jsonJacksonUnmarshalling(Team.class, conn.getInputStream());


        for (Team t : tasks) {
            conversor.updateTeam(t);
        }

        conn.disconnect();
    }

    private void updateDTeam(int last) throws Exception {
        // Defined URL  where to send data
        URL url = new URL(serverURLTeamD);

        // Send POST data request
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setDoOutput(true);

        ArrayList<Team> tasks = (ArrayList<Team>) MarshallingUnmarshalling.jsonJacksonUnmarshalling(Team.class, conn.getInputStream());


        for (Team t : tasks) {
            conversor.deleteTeam(t.getCode());
        }

        conn.disconnect();
    }
    private void updateEmployee(int last) throws Exception {
        updateIEmployee(last);
        updateUEmployee(last);
        updateDEmployee(last);

        conversor.updateLastEmployee(last);
    }

    private void updateIEmployee(int last) throws IOException {
        // Defined URL  where to send data
        URL url = new URL(serverURLEmployeeI);

        // Send POST data request
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setDoOutput(true);

        ArrayList<Employee> tasks = (ArrayList<Employee>) MarshallingUnmarshalling.jsonJacksonUnmarshalling(Employee.class, conn.getInputStream());

        for (Employee t : tasks) {
            conversor.saveEmployee(t);
        }
    }

    private void updateUEmployee(int last) throws Exception {
        // Defined URL  where to send data

        URL url = new URL(serverURLEmployeeU);

        // Send POST data request
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setDoOutput(true);

        ArrayList<Employee> tasks = (ArrayList<Employee>) MarshallingUnmarshalling.jsonJacksonUnmarshalling(Employee.class, conn.getInputStream());


        for (Employee t : tasks) {
            conversor.updateEmployee(t);
        }

        conn.disconnect();
    }

    private void updateDEmployee(int last) throws Exception {
        // Defined URL  where to send data
        URL url = new URL(serverURLEmployeeD);

        // Send POST data request
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setDoOutput(true);

        ArrayList<Employee> tasks = (ArrayList<Employee>) MarshallingUnmarshalling.jsonJacksonUnmarshalling(Employee.class, conn.getInputStream());


        for (Employee t : tasks) {
            conversor.deleteEmployee(t.getNif());
        }

        conn.disconnect();
    }

}