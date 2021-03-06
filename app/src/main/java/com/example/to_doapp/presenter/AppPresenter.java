package com.example.to_doapp.presenter;

import android.os.AsyncTask;
import com.example.to_doapp.contract.AppContract;
import com.example.to_doapp.db.TaskDatabase;
import com.example.to_doapp.model.TaskModel;
import com.hannesdorfmann.mosby3.mvp.MvpPresenter;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class AppPresenter extends AppContract.Presenter implements MvpPresenter<AppContract.View> {

    public AppContract.View view;
    private List<TaskModel> taskModelList = new ArrayList<>();
    private String deletedTaskName;
    private String updateTaskName;
    private TaskDatabase taskDatabase;


    @Override
    public void loadData(TaskDatabase taskDatabase) {
        this.taskDatabase = taskDatabase;
        FetchTasks fetchTasks = new FetchTasks();
        fetchTasks.execute();
    }

    @Override
    public void deleteTask(String taskName) {
        deletedTaskName = taskName;
        DeleteTasks deleteTasks = new DeleteTasks();
        deleteTasks.execute();
    }

    @Override
    public void updateStatus(TaskModel taskModel) {
        updateTaskName = taskModel.taskName;
        UpdateTasks updateTasks = new UpdateTasks();
        updateTasks.execute();
    }

    @Override
    public void setTaskModelList(List<TaskModel> taskModelList) {
        this.taskModelList = taskModelList;
    }


    @Override
    public void attachView(@NotNull AppContract.View view) {
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

    class FetchTasks extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... voids) {
            taskModelList = taskDatabase.taskOperationsDao().getTask();
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            if (view != null) {
                view.showData(taskModelList);
            }
        }
    }

    class DeleteTasks extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... voids) {
            taskDatabase.taskOperationsDao().delete(deletedTaskName);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
        }
    }

    class UpdateTasks extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... voids) {
            taskDatabase.taskOperationsDao().updateStatus(updateTaskName, "completed");
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
        }
    }
}
