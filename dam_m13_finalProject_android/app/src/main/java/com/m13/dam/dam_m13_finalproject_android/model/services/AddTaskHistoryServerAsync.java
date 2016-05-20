package com.m13.dam.dam_m13_finalproject_android.model.services;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.database.Cursor;
import android.os.AsyncTask;
import android.view.View;

import com.m13.dam.dam_m13_finalproject_android.R;
import com.m13.dam.dam_m13_finalproject_android.controller.interfaces.AsyncTaskCompleteListener;
import com.m13.dam.dam_m13_finalproject_android.model.dao.SynupConversor;
import com.m13.dam.dam_m13_finalproject_android.model.pojo.Employee;
import com.m13.dam.dam_m13_finalproject_android.model.pojo.ReturnObject;
import com.m13.dam.dam_m13_finalproject_android.model.pojo.TaskHistory;
import com.m13.dam.dam_m13_finalproject_android.model.pojo.TaskHistoryLog;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.net.UnknownHostException;
import java.text.ParseException;
import java.util.ArrayList;

public class AddTaskHistoryServerAsync extends AsyncTask<String, Void, Void> {

    private String Content;
    private ReturnObject ret;
    private String serverURL =  "http://"+ Connection.getDomain()+ "TaskHistoryInsert/";
    private ProgressDialog progressDialog;
    private Context context;
    private AsyncTaskCompleteListener<ReturnObject> listener;

    public AddTaskHistoryServerAsync(Context context, AsyncTaskCompleteListener<ReturnObject> listener)
    {
        this.context = context;
        this.listener = listener;
        ret = new ReturnObject(200,"OK");
        progressDialog = new ProgressDialog(context);
        progressDialog.setMessage("Connecting to server..");
        progressDialog.setIndeterminate(false);
        progressDialog.setCancelable(false);

    }

    protected void onPreExecute() {
        progressDialog.setMessage("Please wait..");
        progressDialog.show();
    }

    protected Void doInBackground(String... params) {
        if(!Connection.isConnected()){
            ret.setCode(301);
            ret.setMessage(context.getResources().getString(R.string.ERROR_NO_CONNECTION));
            return null;
        }
        HttpURLConnection conn = null;
        int status = 500;
        try {
            URL url = new URL(serverURL+params[0]+"/"+params[1]);
            conn = (HttpURLConnection) url.openConnection();
            conn.setRequestProperty("Accept-Charset", "UTF-8");

            status = conn.getResponseCode();

            ArrayList<TaskHistory> taskHistories = (ArrayList<TaskHistory>) MarshallingUnmarshalling.jsonJacksonUnmarshalling(TaskHistory.class, conn.getInputStream());

            if(taskHistories != null && taskHistories.size() > 0){
                ret.setAssociatedObject(taskHistories.get(0));
            }

            conn.disconnect();

        } catch (MalformedURLException e) {
            ret.setCode(406);
            ret.setMessage(e.toString());
        } catch (UnknownHostException e) {
            ret.setCode(301);
            ret.setMessage(e.toString());
        } catch (IOException e) {
            ret.setCode(407);
            ret.setMessage("connection status: " + status + "\n" + e.toString());
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