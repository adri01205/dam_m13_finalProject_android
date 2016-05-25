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
import com.m13.dam.dam_m13_finalproject_android.model.pojo.Last;
import com.m13.dam.dam_m13_finalproject_android.model.pojo.ReturnObject;
import com.m13.dam.dam_m13_finalproject_android.model.pojo.TaskHistory;
import com.m13.dam.dam_m13_finalproject_android.model.pojo.TaskHistoryLog;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.text.ParseException;
import java.util.ArrayList;

public class UpdateServerAsync  extends AsyncTask<String , Void, Void> {


    private String Content;
    private ReturnObject ret;
    private String serverURL =  "http://"+ Connection.getDomain()+ "TaskHistoryUpdate/";
    private ProgressDialog progressDialog;
    private Context context;
    private AsyncTaskCompleteListener<ReturnObject> listener;
    String code;

    public UpdateServerAsync(Context context, AsyncTaskCompleteListener<ReturnObject> listener)
    {
        this.context = context;
        this.listener = listener;
        ret = new ReturnObject(200,"OK",ReturnObject.UPDATE_SERVER_CALLBACK);
        progressDialog = new ProgressDialog(context);
        progressDialog.setMessage(context.getResources().getString(R.string.CONNECTING));
        progressDialog.setIndeterminate(false);
        progressDialog.setCancelable(false);

    }

    protected void onpreexecute() {
        // NOTE: You can call UI Element here.

        //Start Progress Dialog (Message)
        progressDialog.setMessage(context.getResources().getString(R.string.PLEASE_WAIT));
        progressDialog.show();
    }

    // Call after onPreExecute method
    protected Void doInBackground(String... params) {if(!Connection.isConnected()){
            ret.setCode(301);
            ret.setMessage(context.getResources().getString(R.string.ERROR_NO_CONNECTION));
            return null;
        }
        HttpURLConnection urlConnection = null;
        try {
            SynupConversor conversor = new SynupConversor((Activity)context);

            code = params[0];
            Last lastLocal = conversor.getLast(code);

            int first = lastLocal.getTaskHistoryLogServer();
            int last = conversor.getLastLocalTaskHistoryLog();
            if(last > first) {

                Cursor c = conversor.getCursorTaskHistoryLog(first);
                ArrayList<TaskHistory> list = new ArrayList();
                if (c != null) {
                    while (c.moveToNext()) {
                        list.add(conversor.getTaskHistory(c.getInt(1)));
                    }
                    c.close();
                } else {
                    throw new Exception("Empty Cursor");
                }

                URL urlToRequest = new URL(serverURL);
                urlConnection =
                        (HttpURLConnection) urlToRequest.openConnection();
                urlConnection.setDoOutput(true);
                urlConnection.setRequestMethod("PUT");
                urlConnection.setRequestProperty("Content-Type", "application/json");
                urlConnection.setRequestProperty("Accept", "application/json");
                //urlConnection.setFixedLengthStreamingMode(postParameters.getBytes().length);
                OutputStream out = urlConnection.getOutputStream();
                MarshallingUnmarshalling.jsonJacksonMarshalling(list, out);
                out.close();
                urlConnection.getResponseCode();
                urlConnection.disconnect();


                conversor.updateTaskHistoryLogServer(code, last);
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
        } finally {
            try {
                urlConnection.disconnect();
            } catch (Exception e) { }
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