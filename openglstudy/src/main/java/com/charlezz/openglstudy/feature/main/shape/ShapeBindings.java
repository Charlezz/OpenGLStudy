package com.charlezz.openglstudy.feature.main.shape;

import android.databinding.BindingAdapter;
import android.opengl.GLSurfaceView;

public class ShapeBindings {
    @BindingAdapter("renderer")
    public static void setRenderer(GLSurfaceView view, GLSurfaceView.Renderer renderer){
        view.setEGLContextClientVersion(2);
        view.setRenderer(renderer);
    }
}
