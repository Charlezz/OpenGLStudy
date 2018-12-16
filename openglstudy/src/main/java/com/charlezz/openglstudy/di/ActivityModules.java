package com.charlezz.openglstudy.di;


import com.charlezz.openglstudy.feature.main.MainActivity;
import com.charlezz.openglstudy.feature.main.MainModule;
import com.charlezz.openglstudy.feature.main.shape.ShapeActivity;
import com.charlezz.openglstudy.feature.main.shape.ShapeModule;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class ActivityModules {
    @ActivityScope
    @ContributesAndroidInjector(modules = MainModule.class)
    abstract MainActivity mainActivity();

    @ActivityScope
    @ContributesAndroidInjector(modules = ShapeModule.class)
    abstract ShapeActivity shapeActivity();
}
