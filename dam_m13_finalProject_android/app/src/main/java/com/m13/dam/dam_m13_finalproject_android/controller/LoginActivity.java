package com.m13.dam.dam_m13_finalproject_android.controller;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
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

        SynupSharedPreferences ssp = new SynupSharedPreferences(this);

        if (!ssp.getUserLoged().isEmpty()){
            Intent intent = new Intent(this, MenuActivity.class);
            this.startActivity(intent);
        }

        ((EditText) findViewById(R.id.activity_login_et_username)).setText(ssp.getUserNameSaved());

    }

    public void aceptar() {
        String userName = ((EditText) findViewById(R.id.activity_login_et_username)).getText().toString();
        String password = ((EditText) findViewById(R.id.activity_login_et_password)).getText().toString();

        if(!(userName.isEmpty() || password.isEmpty())) {

            password = md5(password);
            SynupConversor sc = new SynupConversor(this);


            Employee e = sc.getEmployee(userName, password);

            if (e != null) {
                SynupSharedPreferences ssp = new SynupSharedPreferences(this);
                ssp.updateUserLoged(String.valueOf(e.getId()));
                ssp.updateUserNameSaved(((CheckBox) findViewById(R.id.activity_login_cb_remember)).isChecked() ? userName : "");

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
                SynupSharedPreferences ssp = new SynupSharedPreferences(this);
                ssp.updateUserLoged(String.valueOf(e.getId()));
                ssp.updateUserNameSaved(((CheckBox) findViewById(R.id.activity_login_cb_remember)).isChecked() ? ((EditText) findViewById(R.id.activity_login_et_username)).getText().toString() :"");

                Intent intent = new Intent(this, MenuActivity.class);
                this.startActivity(intent);
            } else {
                Dialogs.getErrorDialog(this, getResources().getString(R.string.ERROR_USER_NOT_EXISTS)).show();
            }
        } else {
            Dialogs.getErrorDialog(this, result).show();
        }

    }

    public String md5(String s) {
        try {
            // Create MD5 Hash
            MessageDigest digest = java.security.MessageDigest.getInstance("MD5");
            digest.update(s.getBytes());
            byte messageDigest[] = digest.digest();

            // Create Hex String
            StringBuffer hexString = new StringBuffer();
            for (int i=0; i<messageDigest.length; i++)
                hexString.append(Integer.toHexString(0xFF & messageDigest[i]));
            return hexString.toString();

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return "";
    }
}
