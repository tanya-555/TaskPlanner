package com.example.to_doapp.di;

import com.example.to_doapp.controller.MainController;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {SharedPrefModule.class})
public interface SharedPrefComponent {

    void inject(MainController controller);
}
