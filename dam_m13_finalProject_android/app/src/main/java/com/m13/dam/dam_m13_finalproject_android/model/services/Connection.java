package com.m13.dam.dam_m13_finalproject_android.model.services;

import java.net.InetAddress;

/**
 * Created by adri on 10/05/2016.
 */
public abstract class Connection {
    public static String host = "172.16.10.58" ;
    public static String port = "1567";

    public static String getDomain(){
        return host+":"+port + "/Synup/api/";
    }
}
