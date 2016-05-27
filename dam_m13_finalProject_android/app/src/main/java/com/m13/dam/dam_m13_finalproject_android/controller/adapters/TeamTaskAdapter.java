package com.m13.dam.dam_m13_finalproject_android.controller.adapters;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.m13.dam.dam_m13_finalproject_android.R;
import com.m13.dam.dam_m13_finalproject_android.controller.TaskListActivity;
import com.m13.dam.dam_m13_finalproject_android.model.pojo.Task;
import com.m13.dam.dam_m13_finalproject_android.model.pojo.Team;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by jesus on 10/05/2016.
 */
public class TeamTaskAdapter extends BaseExpandableListAdapter {

    private Context context;
    private ArrayList<Team> headers;
    private HashMap<Team, ArrayList<Task>> childs;

    public TeamTaskAdapter(Context context, ArrayList<Team> headers,
                           HashMap<Team, ArrayList<Task>> childs) {

        this.context = context;
        this.headers = headers;
        this.childs = childs;

    }


    @Override
    public Object getChild(int groupPosition, int childPosititon) {
        return this.childs.get(this.headers.get(groupPosition))
                .get(childPosititon);
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public View getChildView(int groupPosition, final int childPosition,
                             boolean isLastChild, View convertView, ViewGroup parent) {

        final Task le_task = (Task) getChild(groupPosition, childPosition);
        final SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm");

        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) this.context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.list_child, null);
        }

        LinearLayout linearLayout = (LinearLayout) convertView.findViewById(R.id.mainLinearLayout);
        TextView txtListChild = (TextView) convertView.findViewById(R.id.lblListItem);
        ImageView imgViewChild = (ImageView) convertView.findViewById(R.id.iconStatus);
        int imgWidth = 40;/*imgViewChild.getWidth() + imgViewChild.getPaddingLeft() + imgViewChild.getPaddingRight();*/

        TextView txtDateChild = (TextView) convertView.findViewById(R.id.lblListItemDate);
        txtDateChild.setText(context.getResources().getString(R.string.PRIORITY_DATE) + " " +
                formatter.format(le_task.getPriorityDate()));

        txtListChild.setText(le_task.getName());

        linearLayout.setBackgroundColor(getColorByPriority((le_task.getPriority())));
        txtListChild.setTextColor(getTextColorByPriority(le_task.getPriority()));
        txtDateChild.setTextColor(getTextColorByPriority(le_task.getPriority()));

        switch (le_task.getState()){

            case Task.CANCELED:
            case Task.FINISHED:
                imgViewChild.setImageResource(R.drawable.icon_tick);
                break;
            case Task.ONGOING:
                imgViewChild.setImageResource(R.drawable.icon_time);
                break;
            default:
                imgViewChild.setVisibility(View.GONE);
                txtDateChild.setPaddingRelative(0,0,imgWidth + txtDateChild.getPaddingRight(),0);
                break;
        }

        return convertView;
    }

    /**
     * Return color depending on task's priority
     * @param priority priority range between 1 and 5
     * @return a well taste color in integer value
     */
    public static int getColorByPriority(int priority){
        int color = 1;

        switch(priority){
            case 1:
                color = Color.rgb(189,232,139);
                break;
            case 2:
                color = Color.rgb(255,255,133);
                break;
            case 3:
                color = Color.rgb(241,192,122);
                break;
            case 4:
                color = Color.rgb(222,146,131);
                break;
            case 5:
                color = Color.rgb(199, 146, 217);
                break;
        }

        return color;
    }

    public static int getTextColorByPriority(int priority){
        int color;
        if (priority == 2)
        {
            color = Color.rgb(190,180,220);
        }
        else{
            color = Color.rgb(255,255,255);
        }
        return color;
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return this.childs.get(this.headers.get(groupPosition))
                .size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return this.headers.get(groupPosition);
    }

    @Override
    public int getGroupCount() {
        return this.headers.size();
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded,
                             View convertView, ViewGroup parent) {
        Team headerTitle = (Team) getGroup(groupPosition);
        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) this.context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.list_group, null);
        }

        TextView lblListHeader = (TextView) convertView
                .findViewById(R.id.lblListHeader);
        lblListHeader.setTypeface(null, Typeface.BOLD);
        lblListHeader.setText(headerTitle.getName());

        return convertView;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

}
