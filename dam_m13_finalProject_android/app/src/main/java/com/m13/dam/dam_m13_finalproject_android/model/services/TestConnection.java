package com.m13.dam.dam_m13_finalproject_android.model.services;

import java.net.InetAddress;

/**
 * Created by adri on 10/05/2016.
 */
public abstract class TestConnection {
    public static String host = "172.16.10.9" ;
    public static String port = "1567";

    public static String getDomain(){
        return host+":"+port;
    }

    public static boolean testConnection(){
        try {
            InetAddress ipAddr = InetAddress.getByName(getDomain()); //You can replace it with your name

            if (ipAddr.equals("")) {
                return false;
            } else {
                return true;
            }

        } catch (Exception e) {
            return false;
        }
    }
}
