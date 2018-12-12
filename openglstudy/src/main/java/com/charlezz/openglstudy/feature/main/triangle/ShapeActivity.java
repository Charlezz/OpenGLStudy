package com.charlezz.openglstudy.feature.main.triangle;

import javax.inject.Inject;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.charlezz.openglstudy.R;
import com.charlezz.openglstudy.databinding.ActivityShapeBinding;

public class ShapeActivity extends AppCompatActivity {

    public static final String EXTRA_TRIANGLE = "triangle";
    public static final String EXTRA_RECTANGLE ="rectangle";

    @Inject
    ActivityShapeBinding binding;

    @Inject
    ShapeViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shape);
        viewModel.loadRenderer(getIntent().getAction());

    }
}
