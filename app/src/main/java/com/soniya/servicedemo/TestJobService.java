package com.soniya.servicedemo;

import android.app.job.JobParameters;
import android.app.job.JobService;
import android.os.Build;
import android.util.Log;

import androidx.annotation.RequiresApi;
import androidx.room.Room;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
public class TestJobService extends JobService {

    private static final String TAG = "TestJobService";
    private boolean jobCancelled = false;
    private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
    AppDatabase database;

    @Override
    public boolean onStartJob(JobParameters params) {
        Log.d(TAG, "job started...");
        database = Room.databaseBuilder(this, AppDatabase.class, "dateData").allowMainThreadQueries().build();
        doBackgroundTask(params);
        return true;
    }

    private void doBackgroundTask(final JobParameters params){
        new Thread(new Runnable() {
            @Override
            public void run() {
                for(int i = 0 ; i < 10 ; i ++) {

                    if(jobCancelled){
                        return;
                    }

                    Date date = new Date();
                    String currDate = dateFormat.format(date);
                    DataEntity data = new DataEntity();
                    data.setDataString(currDate);
                    database.dataDao().insertData(data);

                    try {
                        Thread.sleep(30*1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

               /* List<DataEntity> newdata = database.dataDao().getAllData();
                if(data != null && data.size()>0) {
                    dataList.clear();
                    for (DataEntity d : data) {
                        dataList.add(d.getDataString());
                    }
                }*/
                /*for(int i = 0 ; i < 10 ; i ++)  {
                    Log.d(TAG, "run: " + i);
                    if(jobCancelled){
                        return;
                    }
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }*/
                Log.d(TAG, "Job finished...");
                jobFinished(params,false);
            }
        }).start();
    }

    @Override
    public boolean onStopJob(JobParameters params) {
        Log.d(TAG, "job stopped...");
        jobCancelled = true;
        return true;
    }
}
