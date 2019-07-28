package com.soniya.servicedemo;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {DataEntity.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {

    public abstract DataDao dataDao();
}
