package com.m13.dam.dam_m13_finalproject_android.model.services;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import com.m13.dam.dam_m13_finalproject_android.controller.interfaces.AsyncTaskCompleteListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

public class UpdateServerAsync  extends AsyncTask<String, Void, Void> {

    private String Content;
    private String error = null;
    private String serverURL = "http://androidexample.com/media/webservice/JsonReturn.php";
    private ProgressDialog progressDialog;
    private Context context;
    private AsyncTaskCompleteListener<String> listener;

    public UpdateServerAsync(Context context, AsyncTaskCompleteListener<String> listener)
    {
        this.context = context;
        this.listener = listener;
        progressDialog = new ProgressDialog(context);
        progressDialog.setMessage("Connecting to server..");
        progressDialog.setIndeterminate(false);
        progressDialog.setCancelable(true);

    }

    protected void onPreExecute() {
        // NOTE: You can call UI Element here.

        //Start Progress Dialog (Message)
        progressDialog.setMessage("Please wait..");
        progressDialog.show();
    }

    // Call after onPreExecute method
    protected Void doInBackground(String... urls) {

        try {

            URL urlToRequest = new URL(serverURL);
            HttpURLConnection urlConnection =
                    (HttpURLConnection) urlToRequest.openConnection();
            urlConnection.setDoOutput(true);
            urlConnection.setRequestMethod("POST");
            urlConnection.setRequestProperty("Content-Type",
                    "application/x-www-form-urlencoded");

            //urlConnection.setFixedLengthStreamingMode(postParameters.getBytes().length);
            PrintWriter out = new PrintWriter(urlConnection.getOutputStream());
            //out.print(postParameters);
            out.close();

        } catch (MalformedURLException e) {
            error = e.toString();
        } catch (ProtocolException e) {
            error = e.toString();
        } catch (IOException e) {
            error = e.toString();
        }

        return null;
    }

    protected void onPostExecute() {
        // Close progress dialog
        progressDialog.dismiss();
        listener.onTaskComplete(error);

    }
}