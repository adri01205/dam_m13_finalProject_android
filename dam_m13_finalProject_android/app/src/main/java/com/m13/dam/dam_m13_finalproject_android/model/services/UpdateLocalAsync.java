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

    private String Content;
    private ReturnObject ret;
    private String serverURLLastTasks = "http://androidexample.com/media/webservice/JsonReturn.php";
    private String serverURLTasks = "http://androidexample.com/media/webservice/JsonReturn.php";
    private String serverURLTasksHistory = "http://androidexample.com/media/webservice/JsonReturn.php";
    private ProgressDialog progressDialog;
    private Context context;
    private AsyncTaskCompleteListener<ReturnObject> listener;

    public UpdateLocalAsync(Context context, AsyncTaskCompleteListener<ReturnObject> listener)
    {
        this.context = context;
        this.listener = listener;
        ret = new ReturnObject(200,"OK");
        progressDialog = new ProgressDialog(context);
        progressDialog.setMessage("Connecting to server..");
        progressDialog.setIndeterminate(false);
        progressDialog.setCancelable(true);

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
        SynupConversor conversor = new SynupConversor((Activity) context);
        try {
            conn = (HttpURLConnection) new URL(serverURLLastTasks).openConnection();
            conn.setDoOutput(true);
            ArrayList<LastServer> lastServer = (ArrayList<LastServer>) MarshallingUnmarshalling.jsonJacksonUnmarshalling(LastServer.class, conn.getInputStream());
            conn.disconnect();


            if (lastServer.get(0).getLastTask() > conversor.getLastLocalTask()) {
                // Defined URL  where to send data
                URL url = new URL(serverURLTasks);

                // Send POST data request
                conn = (HttpURLConnection) url.openConnection();
                conn.setDoOutput(true);

                ArrayList<Task> tasks = (ArrayList<Task>) MarshallingUnmarshalling.jsonJacksonUnmarshalling(Task.class, conn.getInputStream());


                for (Task t : tasks) {
                    conversor.saveTask(t);
                }

                smtingDone = true;
                conn.disconnect();
            }

             if(lastServer.get(0).getLastTaskHistory() > conversor.getLastLocalTask()) {
                // Defined URL  where to send data
                URL url = new URL(serverURLTasksHistory);

                // Send POST data request
                conn = (HttpURLConnection) url.openConnection();
                conn.setDoOutput(true);

                ArrayList<TaskHistory> tasks2 = (ArrayList<TaskHistory>) MarshallingUnmarshalling.jsonJacksonUnmarshalling(TaskHistory.class, conn.getInputStream());

                for (TaskHistory t : tasks2) {
                    conversor.saveTaskHistory(t);
                }
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
}