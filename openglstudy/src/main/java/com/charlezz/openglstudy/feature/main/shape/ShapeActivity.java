package com.charlezz.openglstudy.feature.main.shape;

import javax.inject.Inject;

import android.os.Bundle;

import com.charlezz.openglstudy.databinding.ActivityShapeBinding;
import com.charlezz.openglstudy.feature.main.RendererType;

import dagger.android.support.DaggerAppCompatActivity;

public class ShapeActivity extends DaggerAppCompatActivity {

    public static final String TAG = ShapeActivity.class.getSimpleName();
    public static final String EXTRA_RENDERER_TYPE ="renderer type";

    @Inject
    ActivityShapeBinding binding;

    @Inject
    ShapeViewModel viewModel;

    @Inject
    RendererType rendererType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle(rendererType.getName());
        binding.setLifecycleOwner(this);
        binding.setViewModel(viewModel);

    }
}
