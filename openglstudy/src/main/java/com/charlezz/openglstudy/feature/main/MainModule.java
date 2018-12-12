package com.charlezz.openglstudy.feature.main;

import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingUtil;

import com.charlezz.openglstudy.R;
import com.charlezz.openglstudy.databinding.ActivityMainBinding;
import com.charlezz.openglstudy.di.ActivityScope;

import dagger.Module;
import dagger.Provides;

@Module
public abstract class MainModule {
    @Provides
    @ActivityScope
    static MainViewModel provideViewModel(MainActivity activity){
        return ViewModelProviders.of(activity).get(MainViewModel.class);
    }

    @Provides
    @ActivityScope
    static ActivityMainBinding provideBinding(MainActivity activity){
        return DataBindingUtil.setContentView(activity, R.layout.activity_main);
    }

    @Provides
    @ActivityScope
    static MainAdapter provideAdapter(MainViewModel viewModel){
        return new MainAdapter(viewModel.getMenus());
    }

}
