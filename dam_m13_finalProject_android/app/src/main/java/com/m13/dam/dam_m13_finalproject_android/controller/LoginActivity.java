package com.m13.dam.dam_m13_finalproject_android.controller;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;

import com.m13.dam.dam_m13_finalproject_android.R;
import com.m13.dam.dam_m13_finalproject_android.controller.dialogs.Dialogs;
import com.m13.dam.dam_m13_finalproject_android.controller.interfaces.AsyncTaskCompleteListener;
import com.m13.dam.dam_m13_finalproject_android.model.dao.SynupConversor;
import com.m13.dam.dam_m13_finalproject_android.model.dao.SynupSharedPreferences;
import com.m13.dam.dam_m13_finalproject_android.model.pojo.Employee;
import com.m13.dam.dam_m13_finalproject_android.model.pojo.ReturnObject;
import com.m13.dam.dam_m13_finalproject_android.model.services.getUserAsync;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;


public class LoginActivity extends AppCompatActivity implements AsyncTaskCompleteListener<ReturnObject> {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        findViewById(R.id.activity_login_bt_signup).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                aceptar();
            }
        });

        SynupSharedPreferences.setUserLoged(this, "11111111S");
        if (!SynupSharedPreferences.getUserLoged(this).isEmpty()){
            Intent intent = new Intent(this, MenuActivity.class);
            this.startActivity(intent);
        }
        SynupSharedPreferences.setUpdatedData(this,"1");

        ((EditText) findViewById(R.id.activity_login_et_username)).setText(SynupSharedPreferences.getUserNameSaved(this));

    }

    public void aceptar() {
        String userName = ((EditText) findViewById(R.id.activity_login_et_username)).getText().toString();
        String password = ((EditText) findViewById(R.id.activity_login_et_password)).getText().toString();

        if(!(userName.isEmpty() || password.isEmpty())) {

            password = md5(password);
            SynupConversor sc = new SynupConversor(this);


            Employee e = sc.getEmployee(userName, password);

            if (e != null) {
                SynupSharedPreferences.setUserLoged(this,String.valueOf(e.getNif()));
                SynupSharedPreferences.setUserNameSaved(this,((CheckBox) findViewById(R.id.activity_login_cb_remember)).isChecked() ? userName : "");

                Intent intent = new Intent(this, MenuActivity.class);
                this.startActivity(intent);
            } else {
                new getUserAsync(this, this).execute(userName, password);
            }
        } else {
            Dialogs.getErrorDialog(this, getResources().getString(R.string.ERROR_FILL_ALL_FIELDS)).show();
        }
    }

    @Override
    public void onTaskComplete(ReturnObject result) {
        if(result.succes()){
            Employee e = (Employee) result.getAssociatedObject();
            if(e != null){
                SynupSharedPreferences.setUserLoged(this, String.valueOf(e.getNif().trim()));
                SynupSharedPreferences.setUserNameSaved(this, ((CheckBox) findViewById(R.id.activity_login_cb_remember)).isChecked() ? ((EditText) findViewById(R.id.activity_login_et_username)).getText().toString().trim() :"");

                Intent intent = new Intent(this, MenuActivity.class);
                this.startActivity(intent);
            } else {
                Dialogs.getErrorDialog(this, getResources().getString(R.string.ERROR_USER_NOT_EXISTS)).show();
            }
        } else if (result.getCode()==301){
            Dialogs.getErrorDialog(this, getResources().getString(R.string.ERROR_NO_CONNECTION_LOGIN)).show();
        }else {
            Log.e("SYNUP_ERROR", result.getMessage());
            Dialogs.getErrorDialog(this, getResources().getString(R.string.ERROR_USER_NOT_EXISTS)).show();
//            Dialogs.getErrorDialog(this, result).show();
        }

    }

    //Mètode per passar una string a md5
    //Modified by Jesus
    public String md5(String s) {
        try {
            // Create MD5 Hash
            MessageDigest digest = java.security.MessageDigest.getInstance("MD5");
            digest.update(s.getBytes());
            byte messageDigest[] = digest.digest();

            // Create Hex String
            StringBuffer hexString = new StringBuffer();
            for (int i=0; i<messageDigest.length; i++) {
                String hex = Integer.toHexString(0xFF & messageDigest[i]);
                if (hex.length() == 1) {
                    hexString.append('0');
                }
                hexString.append(hex);
            }

            return hexString.toString();

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return "";
    }
}
