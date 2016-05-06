package com.m13.dam.dam_m13_finalproject_android.model.services;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.TextView;

import com.m13.dam.dam_m13_finalproject_android.controller.interfaces.AsyncTaskCompleteListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class UpdateLocalAsync  extends AsyncTask<String, Void, Void> {

    private String Content;
    private String error = null;
    private String serverURL = "http://androidexample.com/media/webservice/JsonReturn.php";
    private ProgressDialog progressDialog;
    private Context context;
    private AsyncTaskCompleteListener<String> listener;

    public UpdateLocalAsync(Context context, AsyncTaskCompleteListener<String> listener)
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

        /************ Make Post Call To Web Server ***********/
        BufferedReader reader=null;
        HttpURLConnection conn = null;
        // Send data
        try
        {
            // Defined URL  where to send data
            URL url = new URL(serverURL);

            // Send POST data request
            conn = (HttpURLConnection) url.openConnection();
            conn.setDoOutput(true);

            // Get the server response
            reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            StringBuilder sb = new StringBuilder();
            String line = null;

            // Read Server Response
            while((line = reader.readLine()) != null)
            {
                // Append server response in string
                sb.append(line + "");
            }

            // Append Server Response To Content String
            Content = sb.toString();
        }
        catch(Exception ex)
        {
            error = ex.getMessage();
        }
        finally
        {
            try
            {
                reader.close();
                conn.disconnect();
            }
            catch(Exception ex) {
                error = ex.getMessage();
            }
        }

        /*****************************************************/
        return null;
    }

    protected void onPostExecute() {
        // Close progress dialog
        progressDialog.dismiss();
        listener.onTaskComplete(error);

    }
}