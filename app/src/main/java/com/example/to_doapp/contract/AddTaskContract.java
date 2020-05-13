package com.example.to_doapp.contract;

import com.example.to_doapp.db.TaskDatabase;
import com.example.to_doapp.model.TaskModel;
import com.hannesdorfmann.mosby3.mvp.MvpPresenter;
import com.hannesdorfmann.mosby3.mvp.MvpView;

public interface AddTaskContract {

    interface View extends MvpView {
        void onTaskAdded();
    }

    abstract class Presenter implements MvpPresenter<View> {
        public abstract void addData(TaskDatabase taskDatabase);

        public abstract void setTaskModel(TaskModel taskModel);
    }
}
