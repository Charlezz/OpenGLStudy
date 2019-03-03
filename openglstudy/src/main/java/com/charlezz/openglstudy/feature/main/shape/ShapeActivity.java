package com.charlezz.openglstudy.feature.main.shape;

import javax.inject.Inject;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;

import com.charlezz.openglstudy.databinding.ActivityShapeBinding;
import com.charlezz.openglstudy.feature.main.RendererType;
import com.charlezz.openglstudy.utils.PermissionRequestBus;
import com.charlezz.openglstudy.utils.PermissionResultBus;
import com.charlezz.openglstudy.utils.PermissionResultMsg;

import dagger.android.support.DaggerAppCompatActivity;
import io.reactivex.disposables.CompositeDisposable;

public class ShapeActivity extends DaggerAppCompatActivity {

    public static final String TAG = ShapeActivity.class.getSimpleName();
    public static final String EXTRA_RENDERER_TYPE ="renderer type";

    @Inject
    ActivityShapeBinding binding;

    @Inject
    ShapeViewModel viewModel;

    @Inject
    RendererType rendererType;

    private CompositeDisposable disposables = new CompositeDisposable();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle(rendererType.getName());
        binding.setLifecycleOwner(this);
        binding.setViewModel(viewModel);
        getLifecycle().addObserver(viewModel);

        disposables.add(PermissionRequestBus.getInstance().toObserverable().subscribe(msg -> {
                    ActivityCompat.requestPermissions(ShapeActivity.this, msg.getPermissions(), msg.getRequestCode() );
                }, throwable -> { }));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(disposables.isDisposed()){
            disposables.dispose();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        PermissionResultBus.getInstance().send(PermissionResultMsg.with(requestCode, permissions, grantResults));

    }
}
