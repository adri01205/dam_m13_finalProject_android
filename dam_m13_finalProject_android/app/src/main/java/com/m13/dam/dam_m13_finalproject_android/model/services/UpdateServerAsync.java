package com.m13.dam.dam_m13_finalproject_android.model.services;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.database.Cursor;
import android.os.AsyncTask;
import android.util.Log;

import com.m13.dam.dam_m13_finalproject_android.R;
import com.m13.dam.dam_m13_finalproject_android.controller.interfaces.AsyncTaskCompleteListener;
import com.m13.dam.dam_m13_finalproject_android.model.dao.SynupConversor;
import com.m13.dam.dam_m13_finalproject_android.model.pojo.ReturnObject;
import com.m13.dam.dam_m13_finalproject_android.model.pojo.TaskHistoryLog;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.text.ParseException;
import java.util.ArrayList;

public class UpdateServerAsync  extends AsyncTask<Void, Void, Void> {


    private String Content;
    private ReturnObject ret;
    private String serverURL =  "http://"+ Connection.getDomain()+ "TaskHistoryUpdate/";
    private ProgressDialog progressDialog;
    private Context context;
    private AsyncTaskCompleteListener<ReturnObject> listener;

    public UpdateServerAsync(Context context, AsyncTaskCompleteListener<ReturnObject> listener)
    {
        this.context = context;
        this.listener = listener;
        ret = new ReturnObject(200,"OK",ReturnObject.UPDATE_SERVER_CALLBACK);
        progressDialog = new ProgressDialog(context);
        progressDialog.setMessage("Connecting to server..");
        progressDialog.setIndeterminate(false);
        progressDialog.setCancelable(false);

    }

    protected void onPreExecute() {
        // NOTE: You can call UI Element here.

        //Start Progress Dialog (Message)
        progressDialog.setMessage("Please wait..");
        progressDialog.show();
    }

    // Call after onPreExecute method
    protected Void doInBackground(Void... urls) {
        if(!Connection.isConnected()){
            ret.setCode(301);
            ret.setMessage(context.getResources().getString(R.string.ERROR_NO_CONNECTION));
            return null;
        }
        try {
            SynupConversor conversor = new SynupConversor((Activity)context);

            int serverLast = conversor.getLastServerTaskHistoryLog();
            if(conversor.getLastLocalTaskHistoryLog() > serverLast) {

                Cursor c = conversor.getCursorTaskHistoryLog(serverLast);
                ArrayList<TaskHistoryLog> list = new ArrayList();
                if (c != null) {
                    while (c.moveToNext()) {
                        list.add(new TaskHistoryLog(c.getInt(0), c.getInt(1), c.getString(2), new java.sql.Date(SynupConversor.dataFormat.parse(c.getString(3)).getTime())));
                    }
                    c.close();
                } else {
                    throw new Exception("Empty Cursor");
                }

                URL urlToRequest = new URL(serverURL);
                HttpURLConnection urlConnection =
                        (HttpURLConnection) urlToRequest.openConnection();
                urlConnection.setDoOutput(true);
                urlConnection.setRequestMethod("POST");
                urlConnection.setRequestProperty("Content-Type",
                        "application/x-www-form-urlencoded");

                //urlConnection.setFixedLengthStreamingMode(postParameters.getBytes().length);
                MarshallingUnmarshalling.jsonJacksonMarshalling(list, urlConnection.getOutputStream());
            } else {
                ret.setCode(201);
            }

        } catch (MalformedURLException e) {
            ret.setCode(401);
            ret.setMessage(e.toString());
        } catch (ProtocolException e) {
            ret.setCode(402);
            ret.setMessage(e.toString());
        } catch (IOException e) {
            ret.setCode(403);
            ret.setMessage(e.toString());
        } catch (ParseException e) {
            ret.setCode(404);
            ret.setMessage(e.toString());
        } catch (Exception e) {
            ret.setCode(405);
            ret.setMessage(e.toString());
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