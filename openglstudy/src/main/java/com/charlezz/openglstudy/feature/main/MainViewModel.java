package com.charlezz.openglstudy.feature.main;

import java.util.ArrayList;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.content.Context;
import android.content.Intent;

import com.charlezz.openglstudy.R;
import com.charlezz.openglstudy.feature.main.triangle.ShapeActivity;

public class MainViewModel extends AndroidViewModel implements MainMenu.Navigator {

    private ArrayList<MainMenu> menus = new ArrayList<>();
    private Context context;

    public MainViewModel(Application app){
        super(app);
        this.context = app;
        init();
    }

    private void init(){
        menus.add(new MainMenu(R.string.triangle, ShapeActivity.class, this));
    }

    public ArrayList<MainMenu> getMenus() {
        return menus;
    }

    @Override
    public void onMenuClicked(Class activityClass) {
        context.startActivity(new Intent(context, activityClass));
    }
}
