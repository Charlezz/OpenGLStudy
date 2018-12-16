package com.charlezz.openglstudy.feature.main.shape;

import android.arch.lifecycle.ViewModel;
import android.opengl.GLSurfaceView;

public class ShapeViewModel extends ViewModel {

    private RendererType type;

    public ShapeViewModel(RendererType type){
        this.type = type;
    }

    public GLSurfaceView.Renderer getRenderer(){
        return type.getRenderer();
    }
}
