package com.m13.dam.dam_m13_finalproject_android.controller;

import android.content.Intent;
import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polygon;
import com.google.android.gms.maps.model.PolygonOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.m13.dam.dam_m13_finalproject_android.R;
import com.m13.dam.dam_m13_finalproject_android.controller.dialogs.Dialogs;
import com.m13.dam.dam_m13_finalproject_android.controller.interfaces.AsyncTaskCompleteListener;
import com.m13.dam.dam_m13_finalproject_android.model.dao.SynupConversor;
import com.m13.dam.dam_m13_finalproject_android.model.dao.SynupSharedPreferences;
import com.m13.dam.dam_m13_finalproject_android.model.pojo.Employee;
import com.m13.dam.dam_m13_finalproject_android.model.pojo.ReturnObject;
import com.m13.dam.dam_m13_finalproject_android.model.pojo.Task;
import com.m13.dam.dam_m13_finalproject_android.model.pojo.TaskHistory;
import com.m13.dam.dam_m13_finalproject_android.model.pojo.Team;
import com.m13.dam.dam_m13_finalproject_android.model.services.AddTaskHistoryServerAsync;
import com.m13.dam.dam_m13_finalproject_android.model.services.UpdateLocalAsync;
import com.m13.dam.dam_m13_finalproject_android.model.services.UpdateServerAsync;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class DetailActivity extends SynupMenuActivity implements AsyncTaskCompleteListener<ReturnObject> {
    Task task;
    Employee employee;
    TaskHistory taskHistory;
    Team team;
    String taskCode;
    String status;
    SynupConversor sc;
    GoogleMap mMap;
    private String dialogMessage = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        taskCode = this.getIntent().getStringExtra("idTask");

       if(setTaskObject()) {
           setStatus();
           setTexts();
           configurarMapa();
       }
    }

    private void configurarMapa() {
        // Fer una comprovació de l'objecte map amb null per confirmar
        // que no l'hàgim instanciat prèviament
        if (mMap == null) {
            SupportMapFragment mapFrag = (SupportMapFragment) getSupportFragmentManager()
                    .findFragmentById(R.id.map);
            mMap = mapFrag.getMap();
        }
        // Comprovar si s'ha obtingut correctament l'objecte
        if (mMap != null) {
            // El mapa s'ha comprovat. Ara es pot manipular
            mMap.setMyLocationEnabled(true);
            mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
            mMap.getUiSettings().setAllGesturesEnabled(true);
            mMap.setMyLocationEnabled(true);
            mMap.getUiSettings().setMyLocationButtonEnabled(true);
            mMap.getUiSettings().setZoomControlsEnabled(true);

            if(!task.getLocalization().equals("")) {
                try {
                    Geocoder geocoder = new Geocoder(this);
                    List<Address> addressList = geocoder.getFromLocationName(task.getLocalization(), 1);
                    Address a = addressList.get(0);
                    LatLng positon = new LatLng(a.getLatitude(), a.getLongitude());

                    mMap.addMarker(new MarkerOptions()
                            .position(positon)
                            .title(task.getLocalization())
//                            .snippet(task.getLocalization())
                            .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)));

                    mMap.animateCamera(
                            CameraUpdateFactory.newLatLngZoom(positon, 30),
                            2000, null);
                }catch (Exception e){}
            }
        }

    }

    private boolean setTaskObject(){
        sc = new SynupConversor(this);
        task = sc.getTask(taskCode);
        if(task == null){
            Intent i = new Intent(this, MenuActivity.class);
            i.putExtra("ERROR", getResources().getString(R.string.TASK_NOT_EXISTS));
            startActivity(i);
            return false;

        }
        taskHistory = sc.getTaskHistoryByTask(taskCode);
        team = sc.getTeam(task.getId_team());
        employee = null;
        if (taskHistory != null) {
            employee = sc.getEmployee(taskHistory.getId_employee());
        }
        return true;
    }

    private void setStatus(){
        Button buttonFinish = ((Button) findViewById(R.id.activity_detail_bt_finish));
        Button buttonAbandone = ((Button) findViewById(R.id.activity_detail_bt_abandone));
        status = "";
        switch (task.getState()){
            case Task.UNSELECTED:
                status = getResources().getString(R.string.NEW);
                buttonFinish.setText(getResources().getString(R.string.TAKE));
                buttonFinish.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        take();
                    }
                });
                break;
            case Task.ONGOING:
                status = getResources().getString(R.string.IN_PROGRESS);
                if(employee != null && employee.getNif().equals(SynupSharedPreferences.getUserLoged(this))) {
                    buttonFinish.setText(getResources().getString(R.string.FINISH));
                    buttonFinish.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            finishTask();
                        }
                    });

                    buttonAbandone.setVisibility(View.VISIBLE);
                    buttonAbandone.setText(getResources().getString(R.string.ABANDONE));
                    buttonAbandone.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            abandone();
                        }
                    });
                } else {
                    buttonFinish.setVisibility(View.GONE);
                }
                break;
            case Task.ABANDONED:
                status = getResources().getString(R.string.ABANDONED);
                buttonFinish.setText(getResources().getString(R.string.TAKE));
                buttonFinish.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        take();
                    }
                });
                break;
            case Task.FINISHED:
                status = getResources().getString(R.string.FINISHED);
                buttonFinish.setVisibility(View.GONE);
                break;
            case Task.CANCELED:
                status = getResources().getString(R.string.CANCELED);
                buttonFinish.setVisibility(View.GONE);
                break;
        }

    }

    private void setTexts(){

        if(task.getState() == Task.FINISHED && taskHistory != null && taskHistory.getFinishDate() != null && taskHistory.getIsFinished() == 1){
            ((TextView) findViewById(R.id.activity_detail_tv_finish_date)).setText(sc.dataFormat.format(taskHistory.getFinishDate()));
        } else if(task.getState() == Task.UNSELECTED){
            ((TextView) findViewById(R.id.activity_detail_tv_finish_date)).setText(getResources().getString(R.string.NOT_STARTED));
        }else{
            ((TextView) findViewById(R.id.activity_detail_tv_finish_date)).setText(getResources().getString(R.string.NOT_FINISHED));
        }

        ((TextView) findViewById(R.id.activity_detail_tv_name)).setText(task.getName());
        ((TextView) findViewById(R.id.activity_detail_tv_code)).setText(task.getCode());
        ((TextView) findViewById(R.id.activity_detail_tv_description)).setText(task.getDescription());
        ((TextView) findViewById(R.id.activity_detail_tv_employee)).setText(taskHistory != null ? employee.getName() : getResources().getString(R.string.NOT_ASSIGNED));
        ((TextView) findViewById(R.id.activity_detail_tv_priority_date)).setText(sc.dataFormat.format(task.getPriorityDate()));
        ((TextView) findViewById(R.id.activity_detail_tv_project)).setText(task.getProject());
        ((TextView) findViewById(R.id.activity_detail_tv_start_date)).setText(taskHistory != null ? sc.dataFormat.format(taskHistory.getStartDate()) : getResources().getString(R.string.NOT_STARTED));
        ((TextView) findViewById(R.id.activity_detail_tv_status)).setText(status);
        ((CheckBox) findViewById(R.id.activity_detail_cb_finished)).setChecked(taskHistory != null && taskHistory.getFinishDate() != null && taskHistory.getIsFinished() == 1);
        ((TextView) findViewById(R.id.activity_detail_tv_team)).setText(team != null ? team.getName() : "");
        ((TextView) findViewById(R.id.activity_detail_tv_priority)).setText(String.valueOf(task.getPriority()));



    }

    private void abandone() {
        task.setState(Task.ABANDONED);

        Date ourJavaDateObject = new Date();

        SynupConversor conversor = new SynupConversor(this);
        conversor.updateTaskHistory(taskHistory.getId(), ourJavaDateObject, 0);
        conversor.updateTask(task);
        dialogMessage = getResources().getString(R.string.TASK_ABANDONED);
        new UpdateServerAsync(this,this).execute(SynupSharedPreferences.getUserLoged(this));
    }

    private void finishTask() {
        task.setState(Task.FINISHED);
        Date ourJavaDateObject = new Date();
        SynupConversor conversor = new SynupConversor(this);
        conversor.updateTaskHistory(taskHistory.getId(), ourJavaDateObject, 1);
        conversor.updateTask(task);
        dialogMessage = getResources().getString(R.string.TASK_FINISHED);
        new UpdateServerAsync(this,this).execute(SynupSharedPreferences.getUserLoged(this));
    }

    public void take(){
        dialogMessage = getResources().getString(R.string.TASK_TOOK);
        new AddTaskHistoryServerAsync(this,this).execute(SynupSharedPreferences.getUserLoged(this), taskCode);
    }

    @Override
    public void onTaskComplete(ReturnObject result) {
        if (result.succes()) {
                switch (result.getCallback()){
                case ReturnObject.ADD_TASK_HISTORY_CALLBACK:
                    if(result.getAssociatedObject() == null) {
                        dialogMessage = "ERROR";
                        hideErrorItemMenu();
                        SynupSharedPreferences.setUpdatedData(this, "1");
                        goMainMenu();
                    } else {
                        new UpdateServerAsync(this, this).execute(SynupSharedPreferences.getUserLoged(this));
                    }
                    break;
                case ReturnObject.UPDATE_LOCAL_CALLBACK:
                    hideErrorItemMenu();
                    SynupSharedPreferences.setUpdatedData(this, "1");
                    goMainMenu();
                    break;
                case ReturnObject.UPDATE_SERVER_CALLBACK:
                    new UpdateLocalAsync(this, this).execute(SynupSharedPreferences.getUserLoged(this));
                    break;
            }
        } else {
            if(result.getCode() == 301){
                switch (result.getCallback()){
                    case ReturnObject.ADD_TASK_HISTORY_CALLBACK:
                        Dialogs.getErrorDialog(this, getResources().getString(R.string.ERROR_NO_CONNECTION_TAKE_TASK)).show();
                        showErrorItemMenu();
                        SynupSharedPreferences.setUpdatedData(this, "0");
                        break;
                    default:
                        showErrorItemMenu();
                        SynupSharedPreferences.setUpdatedData(this, "0");
                        goMainMenu();
                }

            } else {
                Log.e("SYNUP_ERROR", result.getMessage());
                Dialogs.getErrorDialog(this,getResources().getString(R.string.ERROR_INESPERADO)).show();
            }
        }
    }

    public void goMainMenu(){
        Dialogs.getMessageDialogClickActivity(this, dialogMessage, MenuActivity.class).show();
    }
}
