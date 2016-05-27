package com.m13.dam.dam_m13_finalproject_android.controller.adapters;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.m13.dam.dam_m13_finalproject_android.R;
import com.m13.dam.dam_m13_finalproject_android.model.pojo.Task;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by Jesús Atalaya on 27/05/2016.
 */
public class UserTaskAdapter extends BaseAdapter {

    ArrayList<Task> tasks;
    Context context;

    public UserTaskAdapter(Context context, ArrayList<Task> tasks) {
        this.tasks = tasks;
        this.context = context;
    }

    @Override
    public int getCount() {
        return tasks.size();
    }

    @Override
    public Object getItem(int position) {
        return tasks.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        final Task task = (Task) getItem(position);
        final SimpleDateFormat formatter =  new SimpleDateFormat("yyyy-MM-dd HH:mm");

        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.user_task_adapter, null);
        }

        TextView taskText = (TextView) convertView.findViewById(R.id.lblListItem);
        TextView taskDateText = (TextView) convertView.findViewById(R.id.lblListItemDate);

        LinearLayout linearLayout = (LinearLayout) convertView.findViewById((R.id.layout));

        taskText.setText(task.getName());
        taskText.setTextColor(TeamTaskAdapter.getTextColorByPriority(task.getPriority()));

        taskDateText.setText(context.getResources().getString(R.string.PRIORITY_DATE) + " " +
                formatter.format(task.getPriorityDate()));
        taskDateText.setTextColor(TeamTaskAdapter.getTextColorByPriority(task.getPriority()));
        linearLayout.setBackgroundColor(TeamTaskAdapter.getColorByPriority(task.getPriority()));

        return convertView;
    }

}
