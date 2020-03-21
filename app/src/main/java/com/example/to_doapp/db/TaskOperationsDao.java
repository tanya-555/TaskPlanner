package com.example.to_doapp.db;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.to_doapp.model.TaskModel;

import io.reactivex.Flowable;
import io.reactivex.Single;

@Dao
public interface TaskOperationsDao {

    //Query to get the number of tasks in the table
    @Query("SELECT COUNT(*) FROM task")
    Single<Integer> getNumberOfRows();

    //Query to insert a task in database
    @Insert
    void insertNewTask(TaskModel taskModel);

    //Delete all entries in database
    @Query("DELETE FROM task")
    void deleteAll();

    //Delete a specific task in database
    @Query("DELETE FROM task WHERE name = :name")
    void delete(String name);

    //Select from database
    @Query("SELECT * FROM task")
    Flowable<TaskModel> getTask();
}


