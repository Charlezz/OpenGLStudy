package com.charlezz.openglstudy.feature.main.shape;

import android.arch.lifecycle.ViewModel;
import android.opengl.GLSurfaceView;

import com.charlezz.openglstudy.feature.main.RendererType;

public class ShapeViewModel extends ViewModel {

    private GLSurfaceView.Renderer renderer;

    public ShapeViewModel(RendererType type){
        renderer = type.getRenderer();
    }

    public GLSurfaceView.Renderer getRenderer(){
        return renderer;
    }
}
