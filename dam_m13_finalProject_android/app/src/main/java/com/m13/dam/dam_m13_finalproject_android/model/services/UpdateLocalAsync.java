package com.m13.dam.dam_m13_finalproject_android.model.services;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.m13.dam.dam_m13_finalproject_android.controller.interfaces.AsyncTaskCompleteListener;
import com.m13.dam.dam_m13_finalproject_android.model.dao.SynupConversor;
import com.m13.dam.dam_m13_finalproject_android.model.pojo.Employee;
import com.m13.dam.dam_m13_finalproject_android.model.pojo.LastServer;
import com.m13.dam.dam_m13_finalproject_android.model.pojo.ReturnObject;
import com.m13.dam.dam_m13_finalproject_android.model.pojo.Task;
import com.m13.dam.dam_m13_finalproject_android.model.pojo.TaskHistory;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.GenericArrayType;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class UpdateLocalAsync  extends AsyncTask<Void, Void, Void> {

    private String host = "172.16.10.9" ;
    private String port = "1567";

    private String Content;
    private ReturnObject ret;
    private String serverURLLastTasks = "http://"+host+":"+port+"/api/Last/1";
    private String serverURLTasksI = "http://androidexample.com/media/webservice/JsonReturn.php";
    private String serverURLTasksU = "http://androidexample.com/media/webservice/JsonReturn.php";
    private String serverURLTasksD = "http://androidexample.com/media/webservice/JsonReturn.php";
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
            conn = (HttpURLConnection) new URL(serverURLLastTasks).openConnection();
            conn.setDoOutput(true);
            ArrayList<LastServer> lastServer = (ArrayList<LastServer>) MarshallingUnmarshalling.jsonJacksonUnmarshalling(LastServer.class, conn.getInputStream());
            conn.disconnect();


            if (lastServer.get(0).getTasklogId() > conversor.getLastLocalTask()) {
                updateTask();
                smtingDone = true;
            }

             if(lastServer.get(0).getTaskhistlogId() > conversor.getLastLocalTask()) {
                 updateTaskHistory();
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

    private void updateTask() throws Exception {
        updateITask();
        updateUTask();
        updateDTask();

    }

    private void updateITask() throws IOException {
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

    private void updateUTask() throws Exception {
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

    private void updateDTask() throws Exception {
        // Defined URL  where to send data
        URL url = new URL(serverURLTasksD);

        // Send POST data request
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setDoOutput(true);

        ArrayList<Task> tasks = (ArrayList<Task>) MarshallingUnmarshalling.jsonJacksonUnmarshalling(Task.class, conn.getInputStream());


        for (Task t : tasks) {
            conversor.deleteTask(t.getId());
        }

        conn.disconnect();
    }

    private void updateTaskHistory() throws Exception {
        updateITaskHistory();
        updateUTaskHistory();
        updateDTaskHistory();

    }

    private void updateITaskHistory() throws IOException {
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

    private void updateUTaskHistory() throws Exception {
        // Defined URL  where to send data

        URL url = new URL(serverURLTasksHistoryU);

        // Send POST data request
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setDoOutput(true);

        ArrayList<TaskHistory> tasks = (ArrayList<TaskHistory>) MarshallingUnmarshalling.jsonJacksonUnmarshalling(TaskHistory.class, conn.getInputStream());


        for (TaskHistory t : tasks) {
            conversor.updateTaskHistory(t.getId(),t.getFinishDate(),t.getIsFinished());
        }

        conn.disconnect();
    }

    private void updateDTaskHistory() throws Exception {
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
}