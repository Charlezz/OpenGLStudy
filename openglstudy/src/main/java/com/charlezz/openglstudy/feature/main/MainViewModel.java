package com.charlezz.openglstudy.feature.main;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.content.Intent;

import com.charlezz.openglstudy.feature.main.shape.RendererType;
import com.charlezz.openglstudy.feature.main.shape.ShapeActivity;

import java.util.ArrayList;

public class MainViewModel extends AndroidViewModel implements MainMenu.Navigator {

    private ArrayList<MainMenu> menus = new ArrayList<>();

    public MainViewModel(Application app){
        super(app);
        init();
    }

    private void init(){
        for(RendererType type : RendererType.values()){
            menus.add(new MainMenu(type, this));
        }
    }

    public ArrayList<MainMenu> getMenus() {
        return menus;
    }

    @Override
    public void onMenuClicked(RendererType rendererType) {
        Intent intent = new Intent(getApplication(), ShapeActivity.class);
        intent.putExtra(ShapeActivity.EXTRA_RENDERER_TYPE, rendererType);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        getApplication().startActivity(intent);
    }
}
