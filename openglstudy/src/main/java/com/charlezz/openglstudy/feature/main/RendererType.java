package com.charlezz.openglstudy.feature.main;

import android.graphics.BitmapFactory;
import android.support.annotation.StringRes;

import com.charlezz.openglstudy.App;
import com.charlezz.openglstudy.R;
import com.charlezz.openglstudy.feature.main.shape.base.ShapeRenderer;
import com.charlezz.openglstudy.feature.main.shape.cube.Cube;
import com.charlezz.openglstudy.feature.main.shape.rectangle.CameraRect;
import com.charlezz.openglstudy.feature.main.shape.rectangle.Rect;
import com.charlezz.openglstudy.feature.main.shape.rectangle.Texture2DRect;
import com.charlezz.openglstudy.feature.main.shape.rectangle.VideoRect;
import com.charlezz.openglstudy.feature.main.shape.triangle.Triangle;

public enum RendererType {

    Triangle(R.string.triangle){
        @Override
        public ShapeRenderer<Triangle> getRenderer() {
            return new ShapeRenderer<>(new Triangle());
        }
    },
    Rectangle(R.string.rectangle) {
        @Override
        public ShapeRenderer<Rect> getRenderer() {
            return new ShapeRenderer<>(new Rect());
        }
    },
    TextureRectangle(R.string.rectangle_with_texture){
        @Override
        public ShapeRenderer<Texture2DRect> getRenderer() {
            return new ShapeRenderer<>(new Texture2DRect(
                    BitmapFactory.decodeResource(App.getContext().getResources(), R.drawable.runa)
            ));
        }
    },
    Cube(R.string.cube){
        @Override
        public ShapeRenderer<Cube> getRenderer() {
            return new ShapeRenderer<>(new Cube());
        }
    },
    Video(R.string.video){
        @Override
        public ShapeRenderer<VideoRect> getRenderer() {
            return new ShapeRenderer<>(new VideoRect("https://sample-videos.com/video123/mp4/720/big_buck_bunny_720p_1mb.mp4"));
        }
    },
    Camera(R.string.camera){
        @Override
        public ShapeRenderer<CameraRect> getRenderer() {
            return new ShapeRenderer<>(new CameraRect());
        }
    }
    ;

    @StringRes
    private int name;

    public abstract ShapeRenderer<?> getRenderer();

    RendererType(@StringRes int name){
        this.name = name;
    }

    @StringRes
    public int getName(){
        return name;
    }

}
