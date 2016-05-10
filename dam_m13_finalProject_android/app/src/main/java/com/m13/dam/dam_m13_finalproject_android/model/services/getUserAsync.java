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

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class getUserAsync extends AsyncTask<String, Void, Void> {

    private String host = "172.16.10.9" ;
    private String port = "1567";

    private String Content;
    private ReturnObject ret;
    private String serverURL = "http://"+host+":"+port+"/api/Last/1";
    private ProgressDialog progressDialog;
    private Context context;
    private AsyncTaskCompleteListener<ReturnObject> listener;
    private SynupConversor conversor;

    public getUserAsync(Context context, AsyncTaskCompleteListener<ReturnObject> listener)
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
    protected Void doInBackground(String... params) {
        HttpURLConnection conn = null;
        try {
            // Defined URL  where to send data
            URL url = new URL(serverURL+params[0]+"/"+params[1]);

            // Send POST data request
            conn = (HttpURLConnection) url.openConnection();
            conn.setDoOutput(true);

            ArrayList<Employee> employees = (ArrayList<Employee>) MarshallingUnmarshalling.jsonJacksonUnmarshalling(Employee.class, conn.getInputStream());

            if(employees != null){
                ret.setAssociatedObject(employees.get(0));
            }

            conn.disconnect();

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