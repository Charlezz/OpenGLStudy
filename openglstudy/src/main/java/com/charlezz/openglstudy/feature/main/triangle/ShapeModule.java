package com.charlezz.openglstudy.feature.main.triangle;

import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingUtil;

import com.charlezz.openglstudy.R;
import com.charlezz.openglstudy.databinding.ActivityShapeBinding;
import com.charlezz.openglstudy.di.ActivityScope;

import dagger.Module;
import dagger.Provides;

@Module
public abstract class ShapeModule {
    @Provides
    @ActivityScope
    ActivityShapeBinding provideBinding(ShapeActivity activity){
        return DataBindingUtil.setContentView(activity, R.layout.activity_shape);
    }

    @Provides
    @ActivityScope
    ShapeViewModel provideViewModel(ShapeActivity activity){
        return ViewModelProviders.of(activity).get(ShapeViewModel.class);
    }
}
