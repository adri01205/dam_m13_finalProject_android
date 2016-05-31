package com.m13.dam.dam_m13_finalproject_android.model.services;

import android.app.Application;
import android.content.Context;
import android.content.res.Resources;
import android.net.ConnectivityManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.m13.dam.dam_m13_finalproject_android.R;
import com.m13.dam.dam_m13_finalproject_android.controller.MenuActivity;

import java.net.HttpURLConnection;
import java.net.InetAddress;
import java.net.URL;

/**
 * Created by adri on 10/05/2016.
 */
public abstract class Connection {

    //IP of the server
    public static String host = MenuActivity.context.getResources().getString(R.string.host);

    //PORT of the server
    public static String port = MenuActivity.context.getResources().getString(R.string.port);

    //Shared resource for all webservice methods
    public static String enlace = MenuActivity.context.getResources().getString(R.string.enlace);

    //Minim resurce for test connection
    public static String enlaceConnection = MenuActivity.context.getResources().getString(R.string.enlaceConnection);


    /**
     * Get the domain with the shared resource of the webservice
     * @return String domain
     */
    public static String getDomain(){

        return host+":"+port + enlace;
    }


    /**
     * Test the connection between the movile and the webservice
     * @return Boolean if is connected return true, false either
     */
    public static boolean isConnected() {

        try {
            URL url = new URL("http://" + host+":"+port + enlaceConnection);
            HttpURLConnection urlc = (HttpURLConnection) url
                    .openConnection();
            urlc.setRequestProperty("Connection", "close");
            urlc.setConnectTimeout(2000); // Timeout 2 seconds.
            urlc.connect();

            return (urlc.getResponseCode() == 200);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}
