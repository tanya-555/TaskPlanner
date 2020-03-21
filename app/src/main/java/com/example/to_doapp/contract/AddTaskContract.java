package com.example.to_doapp.contract;

import com.hannesdorfmann.mosby3.mvp.MvpPresenter;
import com.hannesdorfmann.mosby3.mvp.MvpView;

public interface AddTaskContract {

    interface View extends MvpView {

    }

    public abstract class Presenter implements MvpPresenter<View> {

    }
}
