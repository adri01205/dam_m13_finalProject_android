package com.m13.dam.dam_m13_finalproject_android.model.services;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

import com.m13.dam.dam_m13_finalproject_android.R;
import com.m13.dam.dam_m13_finalproject_android.controller.interfaces.AsyncTaskCompleteListener;
import com.m13.dam.dam_m13_finalproject_android.model.dao.SynupConversor;
import com.m13.dam.dam_m13_finalproject_android.model.pojo.Employee;
import com.m13.dam.dam_m13_finalproject_android.model.pojo.ReturnObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.ArrayList;

public class getUserAsync extends AsyncTask<String, Void, Void> {

    private String Content;
    private ReturnObject ret;
    private String serverURL =  "http://"+ Connection.getDomain()+ "Login/";
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
        progressDialog.setCancelable(false);
        conversor = new SynupConversor((Activity) context);
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
        int status = 500;
        try {
            // Defined URL  where to send data
            URL url = new URL(serverURL+params[0]+"/"+params[1]);

            // Send POST data request
            conn = (HttpURLConnection) url.openConnection();
            conn.setRequestProperty("Accept-Charset", "UTF-8");
            //conn.setRequestMethod("GET");
            //conn.setDoOutput(true);
            status = conn.getResponseCode();

            ArrayList<Employee> employees = (ArrayList<Employee>) MarshallingUnmarshalling.jsonJacksonUnmarshalling(Employee.class, conn.getInputStream());

            if(employees != null && employees.size() > 0){
                ret.setAssociatedObject(employees.get(0));
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