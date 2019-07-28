package com.soniya.servicedemo;

import androidx.appcompat.app.AppCompatActivity;

import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.ComponentName;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;

import androidx.room.Room;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    ListView listView;
    List<String> dataList = new ArrayList<>();

    AppDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = findViewById(R.id.listview);

        database = Room.databaseBuilder(this, AppDatabase.class, "dateData").allowMainThreadQueries().build();
        List<DataEntity> data = database.dataDao().getAllData();

        if(data != null && data.size()>0){
            dataList.clear();
            for(DataEntity d:data){
                dataList.add(d.getDataString());
            }

        }else{
            dataList.clear();
            dataList.add("No data found!");
        }
        CustomAdapter adapter = new CustomAdapter(this, R.layout.data_row_layout, dataList);
        listView.setAdapter(adapter);

        scheduleJob();

    }

    public void scheduleJob()   {
        ComponentName componentName = new ComponentName(this, TestJobService.class);
        JobInfo jobInfo = new JobInfo.Builder(123, componentName)
                .setPersisted(true)
//                .setPeriodic(2*60*60*1000)
                .setPeriodic(60*1000)
                .build();

        JobScheduler scheduler = (JobScheduler) getSystemService(JOB_SCHEDULER_SERVICE);
        int result = scheduler.schedule(jobInfo);

        if(result == JobScheduler.RESULT_SUCCESS){
            Log.d("MainActivity", "JobScheduled success");
        }else{
            Log.d("MainActivity", "JobScheduled failed");
        }

    }

    public void cancelJob(){
        JobScheduler scheduler = (JobScheduler) getSystemService(JOB_SCHEDULER_SERVICE);
        scheduler.cancel(123);
        Log.d("MainActivity", "Job cancelled");
    }

}
