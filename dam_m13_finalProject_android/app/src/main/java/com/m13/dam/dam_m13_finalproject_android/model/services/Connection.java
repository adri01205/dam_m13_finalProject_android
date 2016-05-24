package com.m13.dam.dam_m13_finalproject_android.model.services;

import android.content.Context;
import android.net.ConnectivityManager;
import android.util.Log;

import java.net.HttpURLConnection;
import java.net.InetAddress;
import java.net.URL;

/**
 * Created by adri on 10/05/2016.
 */
public abstract class Connection {
    //public static String host = "172.16.10.57" ;
    public static String host = "192.168.1.113" ;
    public static String port = "1567";

    public static String getDomain(){
        return host+":"+port + "/Synup/api/";
    }
    public static boolean isConnected() {
        try {
            URL url = new URL("http://" + host+":"+port + "/Synup/");
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
