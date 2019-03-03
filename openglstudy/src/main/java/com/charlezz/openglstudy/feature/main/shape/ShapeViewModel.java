package com.charlezz.openglstudy.feature.main.shape;

import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.LifecycleObserver;
import android.arch.lifecycle.OnLifecycleEvent;
import android.databinding.BaseObservable;

import com.charlezz.openglstudy.feature.main.RendererType;
import com.charlezz.openglstudy.feature.main.shape.base.ShapeRenderer;

public class ShapeViewModel extends BaseObservable implements LifecycleObserver {
    private ShapeRenderer<?> renderer;

    public ShapeViewModel(RendererType type) {
        renderer = type.getRenderer();
    }

    public ShapeRenderer<?> getRenderer(){
        return renderer;
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    public void onResume(){
        renderer.onResume();
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    public void onPause(){
        renderer.onPause();
    }

}
