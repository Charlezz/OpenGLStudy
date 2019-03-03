package com.charlezz.openglstudy.feature.main.shape;

import static com.charlezz.openglstudy.feature.main.shape.ShapeActivity.EXTRA_RENDERER_TYPE;

import android.databinding.DataBindingUtil;

import com.charlezz.openglstudy.R;
import com.charlezz.openglstudy.databinding.ActivityShapeBinding;
import com.charlezz.openglstudy.di.ActivityScope;
import com.charlezz.openglstudy.feature.main.RendererType;

import dagger.Module;
import dagger.Provides;

@Module
public abstract class ShapeModule {
    @Provides
    @ActivityScope
    static ActivityShapeBinding provideBinding(ShapeActivity activity){
        return DataBindingUtil.setContentView(activity, R.layout.activity_shape);
    }

    @Provides
    @ActivityScope
    static ShapeViewModel provideViewModel(RendererType rendererType){
        return new ShapeViewModel(rendererType);
    }

    @Provides
    @ActivityScope
    static RendererType provideRendererType(ShapeActivity activity){
        if (!activity.getIntent().hasExtra(EXTRA_RENDERER_TYPE)) {
            throw new IllegalArgumentException("No RendererType");
        }
        return (RendererType) activity.getIntent().getSerializableExtra(EXTRA_RENDERER_TYPE);
    }

}
