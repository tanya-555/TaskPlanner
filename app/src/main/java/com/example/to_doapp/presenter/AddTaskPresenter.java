package com.example.to_doapp.presenter;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import com.example.to_doapp.contract.AddTaskContract;
import com.example.to_doapp.db.TaskDatabase;
import com.example.to_doapp.model.TaskModel;
import com.hannesdorfmann.mosby3.mvp.MvpPresenter;

import org.jetbrains.annotations.NotNull;

public class AddTaskPresenter extends AddTaskContract.Presenter implements MvpPresenter<AddTaskContract.View> {

    public AddTaskContract.View view;
    private Context context;
    private TaskModel taskModel;

    @Override
    public void attachView(@NotNull AddTaskContract.View view) {
        this.view = view;
    }

    @Override
    public void detachView(boolean retainInstance) {

    }

    @Override
    public void detachView() {

    }

    @Override
    public void destroy() {

    }

    @Override
    public void loadData(Context context, TaskModel taskModel) {
        this.context = context;
        this.taskModel = taskModel;
        AddTask addTask = new AddTask();
        addTask.execute();
    }

    class AddTask extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... voids) {

            //adding to database
            TaskDatabase.getInstance(context).taskOperationsDao().insertNewTask(taskModel);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            Toast.makeText(context, "Added Task", Toast.LENGTH_LONG).show();
            view.onTaskAdded();
        }
    }
}
