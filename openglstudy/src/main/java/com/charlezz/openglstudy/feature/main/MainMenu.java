package com.charlezz.openglstudy.feature.main;

import android.support.annotation.StringRes;

public class MainMenu {
    @StringRes
    private int name;
    private Class activityClass;
    private Navigator navigator;

    public MainMenu(@StringRes int nameResId, Class activityClass, Navigator navigator) {
        this.name = nameResId;
        this.activityClass = activityClass;
        this.navigator = navigator;
    }

    public void onMenuClicked(){
        navigator.onMenuClicked(activityClass);
    }

    public int getName() {
        return name;
    }

    interface Navigator{
        void onMenuClicked(Class activityClass);
    }
}
