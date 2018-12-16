package com.charlezz.openglstudy.feature.main.shape;

import static com.charlezz.openglstudy.feature.main.shape.ShapeActivity.EXTRA_RENDERER_TYPE;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;

import com.charlezz.openglstudy.R;
import com.charlezz.openglstudy.databinding.ActivityShapeBinding;
import com.charlezz.openglstudy.di.ActivityScope;

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
    static ShapeViewModel provideViewModel(final ShapeActivity activity, final RendererType rendererType){
        return ViewModelProviders.of(activity, new ViewModelProvider.Factory() {
            @NonNull
            @Override
            public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
                return (T) new ShapeViewModel(rendererType);
            }
        }).get(ShapeViewModel.class);
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
