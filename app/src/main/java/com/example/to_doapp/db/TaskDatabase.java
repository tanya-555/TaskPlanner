package com.example.to_doapp.db;
import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import com.example.to_doapp.model.TaskModel;

@Database(entities = TaskModel.class , exportSchema = false , version =1)
abstract public class TaskDatabase extends RoomDatabase {

    private static final String DB_NAME = "myDatabase";
    private static TaskDatabase instance;

    public static synchronized TaskDatabase getInstance(Context context) {

        if(instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(), TaskDatabase.class, DB_NAME)
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return instance;
    }
    public abstract TaskOperationsDao taskOperationsDao();
}
