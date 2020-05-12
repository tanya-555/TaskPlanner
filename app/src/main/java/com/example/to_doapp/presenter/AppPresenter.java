package com.example.to_doapp.presenter;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import com.example.to_doapp.contract.AppContract;
import com.example.to_doapp.db.TaskDatabase;
import com.example.to_doapp.model.TaskModel;
import com.hannesdorfmann.mosby3.mvp.MvpPresenter;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class AppPresenter extends AppContract.Presenter implements MvpPresenter<AppContract.View> {

    public AppContract.View view;
    private Context context;
    private List<TaskModel> taskModelList;
    private String deletedTaskName;
    private String updateTaskName;


    @Override
    public void loadData(Context context) {
        this.context = context;
        taskModelList = new ArrayList<>();
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
    public void attachView(@NotNull AppContract.View view) {
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

    class FetchTasks extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... voids) {
            taskModelList = TaskDatabase.getInstance(context).taskOperationsDao().getTask();
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            if(view != null) {
                view.showData(taskModelList);
            }
        }
    }

    class DeleteTasks extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... voids) {
            TaskDatabase.getInstance(context).taskOperationsDao().delete(deletedTaskName);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            Toast.makeText(context, "Task deleted successfully!",Toast.LENGTH_LONG).show();
        }
    }

    class UpdateTasks extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... voids) {
            TaskDatabase.getInstance(context).taskOperationsDao().updateStatus(updateTaskName, "completed");
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            Toast.makeText(context, "Task updated successfully! Swipe to Refresh",Toast.LENGTH_LONG).show();
        }
    }
}
