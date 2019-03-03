package com.charlezz.openglstudy;

import android.app.Application;
import android.content.Context;

import com.charlezz.openglstudy.di.DaggerAppComponent;

import dagger.android.AndroidInjector;
import dagger.android.support.DaggerApplication;

public class App extends DaggerApplication {

    private static App context;


    public static Context getContext(){
        return context;
    }

    public static Application getApplication(){
        return context;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        context = this;
    }

    @Override
    protected AndroidInjector<? extends DaggerApplication> applicationInjector() {
        return DaggerAppComponent.builder().create(this);
    }
}
