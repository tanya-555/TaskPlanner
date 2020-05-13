package com.example.to_doapp.presenter;

import android.os.AsyncTask;

import com.example.to_doapp.contract.AddTaskContract;
import com.example.to_doapp.db.TaskDatabase;
import com.example.to_doapp.model.TaskModel;
import com.hannesdorfmann.mosby3.mvp.MvpPresenter;

import org.jetbrains.annotations.NotNull;

public class AddTaskPresenter extends AddTaskContract.Presenter implements MvpPresenter<AddTaskContract.View> {

    public AddTaskContract.View view;
    private TaskModel taskModel;
    private TaskDatabase taskDatabase;

    @Override
    public void attachView(@NotNull AddTaskContract.View view) {
        this.view = view;
    }

    @Override
    public void detachView(boolean retainInstance) {
        this.view = null;
    }

    @Override
    public void detachView() {
        this.view = null;
    }

    @Override
    public void destroy() {

    }

    @Override
    public void addData(TaskDatabase taskDatabase) {
        this.taskDatabase = taskDatabase;
        AddTask addTask = new AddTask();
        addTask.execute();
    }

    @Override
    public void setTaskModel(TaskModel taskModel) {
        this.taskModel = taskModel;
    }

    class AddTask extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... voids) {

            //adding to database
            taskDatabase.taskOperationsDao().insertNewTask(taskModel);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            view.onTaskAdded();
        }
    }
}
