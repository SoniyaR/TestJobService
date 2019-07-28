package com.soniya.servicedemo;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface DataDao {

    @Query("select * from dateData")
     List<DataEntity> getAllData();

    @Insert
     void insertData(DataEntity dataobj);

    @Delete
     void deleteAll(DataEntity data);

}
