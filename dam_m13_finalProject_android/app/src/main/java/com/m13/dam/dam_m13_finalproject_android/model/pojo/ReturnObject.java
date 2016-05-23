package com.m13.dam.dam_m13_finalproject_android.model.pojo;

/**
 * Created by Adri on 08/05/2016.
 */
public class ReturnObject {
    public static final int UPDATE_LOCAL_CALLBACK = 0;
    public static final int UPDATE_SERVER_CALLBACK = 1;
    public static final int GET_USER_CALLBACK = 2;
    public static final int ADD_TASK_HISTORY_CALLBACK = 1;

    int code;
    String message;
    Object associatedObject;
    int callback;

    public ReturnObject(int code, String message, int callback) {
        this.code = code;
        this.message = message;
        this.callback = callback;
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

    public Object getAssociatedObject() {
        return associatedObject;
    }

    public void setAssociatedObject(Object associatedObject) {
        this.associatedObject = associatedObject;
    }

    public int getCallback() {
        return callback;
    }

    public void setCallback(int callback) {
        this.callback = callback;
    }

    @Override
    public String toString() {
        return "ReturnObject{" +
                "code=" + code +
                ", message='" + message + '\'' +
                '}';
    }
}
