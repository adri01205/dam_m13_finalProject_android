package com.m13.dam.dam_m13_finalproject_android.model.pojo;

/**
 * Created by Adri on 08/05/2016.
 */
public class ReturnObject {
    int code;
    String message;

    public ReturnObject(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean succes(){
        return this.getCode()/100 == 2;
    }

    @Override
    public String toString() {
        return "ReturnObject{" +
                "code=" + code +
                ", message='" + message + '\'' +
                '}';
    }
}
