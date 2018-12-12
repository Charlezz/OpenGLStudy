package com.charlezz.openglstudy.feature.main;

import javax.inject.Inject;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;

import com.charlezz.openglstudy.databinding.ActivityMainBinding;

import dagger.android.support.DaggerAppCompatActivity;

public class MainActivity extends DaggerAppCompatActivity {

    @Inject
    ActivityMainBinding binding;
    @Inject
    MainViewModel viewModel;
    @Inject
    MainAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding.recycleView.setLayoutManager(new LinearLayoutManager(this));
        binding.recycleView.setAdapter(adapter);
    }
}
