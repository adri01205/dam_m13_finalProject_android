package com.m13.dam.dam_m13_finalproject_android.controller.dialogs;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;

import com.m13.dam.dam_m13_finalproject_android.R;
import com.m13.dam.dam_m13_finalproject_android.model.pojo.ReturnObject;

public abstract class Dialogs {
    public static AlertDialog getRunAddedDialog(Activity context) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);

//        builder.setTitle(R.string.runAdded);
//        builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
//            public void onClick(DialogInterface dialog, int id) {
//                Intent intent = new Intent(context, MenuActivity.class);
//                context.startActivity(intent);
//
//            }
//        });

       return builder.create();
    }

    public static AlertDialog getErrorNoUserDialog(Activity context) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);

//        builder.setTitle(R.string.noUserTitle)
//                .setMessage(R.string.noUserMessage);
//        builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
//            public void onClick(DialogInterface dialog, int id) {
//
//            }
//        });

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
    }
}
