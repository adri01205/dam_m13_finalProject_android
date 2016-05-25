package com.m13.dam.dam_m13_finalproject_android.controller.dialogs;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;

import com.m13.dam.dam_m13_finalproject_android.R;
import com.m13.dam.dam_m13_finalproject_android.model.pojo.ReturnObject;

public abstract class Dialogs {
    public static AlertDialog getMessageDialogClickActivity(final Activity context, String message ,final Class clas) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);

        builder.setTitle(R.string.SUCCES)
                .setMessage(message);
        builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                Intent intent = new Intent(context, clas);
                context.startActivity(intent);

            }
        });

       return builder.create();
    }

    public static AlertDialog getErrorDialog(Activity context, ReturnObject returnObject) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);

        builder.setTitle(R.string.error)
                .setMessage(returnObject.getCode() + ": " + returnObject.getMessage());
        builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {

            }
        });

        return builder.create();
    }    public static AlertDialog getErrorDialog(Activity context, String error) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);

        builder.setTitle(R.string.error)
                .setMessage(error);
        builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {

            }
        });

        return builder.create();
    }
}
