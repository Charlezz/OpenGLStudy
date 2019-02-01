package com.charlezz.openglstudy.feature.main.shape;

import android.opengl.GLSurfaceView;
import android.support.annotation.StringRes;

import com.charlezz.openglstudy.R;
import com.charlezz.openglstudy.feature.main.shape.cube.CubeRenderer;
import com.charlezz.openglstudy.feature.main.shape.rectangle.RectangleRenderer;
import com.charlezz.openglstudy.feature.main.shape.rectangle.TextureRectRenderer;
import com.charlezz.openglstudy.feature.main.shape.triangle.TriangleRenderer;

public enum RendererType {

    Triangle(R.string.triangle){
        @Override
        GLSurfaceView.Renderer getRenderer() {
            return new TriangleRenderer();
        }
    },
    Rectangle(R.string.rectangle) {
        @Override
        GLSurfaceView.Renderer getRenderer() {
            return new RectangleRenderer();
        }
    },
    TextureRectangle(R.string.rectangle_with_texture){
        @Override
        GLSurfaceView.Renderer getRenderer() {
            return new TextureRectRenderer();
        }
    },
    Cube(R.string.cube){
        @Override
        GLSurfaceView.Renderer getRenderer() {
            return new CubeRenderer();
        }
    };



    @StringRes
    private int name;

    abstract GLSurfaceView.Renderer getRenderer();

    RendererType(@StringRes int name){
        this.name = name;
    }

    @StringRes
    public int getName(){
        return name;
    }

}
