package com.example.myto_do_list.database;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.example.myto_do_list.dao.TaskDao;
import com.example.myto_do_list.entidade.Task;

@Database(entities = {Task.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public abstract TaskDao taskDao();
}