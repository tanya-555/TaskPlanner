package com.example.to_doapp.contract;

import com.example.to_doapp.db.TaskDatabase;
import com.example.to_doapp.model.TaskModel;
import com.hannesdorfmann.mosby3.mvp.MvpPresenter;
import com.hannesdorfmann.mosby3.mvp.MvpView;

import java.util.List;

public interface AppContract {

    interface View extends MvpView {
        void showData(List<TaskModel> taskModelList);
    }

    abstract class Presenter implements MvpPresenter<View> {
        public abstract void loadData(TaskDatabase taskDatabase);

        public abstract void deleteTask(String taskName);

        public abstract void updateStatus(TaskModel taskModel);

        public abstract void setTaskModelList(List<TaskModel> taskModelList);
    }
}
