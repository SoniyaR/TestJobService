package com.soniya.servicedemo;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;

import java.util.List;

public class CustomAdapter extends ArrayAdapter {

    Context context;
    int resource;
    List<String> dataList;

    public CustomAdapter(Context context, int resource, List<String> objects) {
        super(context, resource, objects);
        this.context = context;
        this.resource = resource;
        this.dataList = objects;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(resource, null, true);
        }
        if(dataList.get(position) != null && !dataList.get(position).isEmpty()){
            TextView textView = (TextView) convertView.findViewById(R.id.datatext);
            textView.setText(dataList.get(position));
        }

        return convertView;
    }

    public void setData(List<String> data){
        dataList = data;
        notifyDataSetChanged();
    }
}
