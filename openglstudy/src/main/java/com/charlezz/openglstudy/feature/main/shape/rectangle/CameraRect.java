package com.charlezz.openglstudy.feature.main.shape.rectangle;

import java.util.Arrays;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

import android.Manifest;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.SurfaceTexture;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraCaptureSession;
import android.hardware.camera2.CameraDevice;
import android.hardware.camera2.CameraManager;
import android.hardware.camera2.CaptureRequest;
import android.opengl.GLES11Ext;
import android.opengl.GLES20;
import android.opengl.Matrix;
import android.os.Build;
import android.os.Handler;
import android.os.HandlerThread;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.Surface;

import com.charlezz.openglstudy.App;
import com.charlezz.openglstudy.feature.main.shape.base.TextureShape;
import com.charlezz.openglstudy.utils.PermissionRequestBus;
import com.charlezz.openglstudy.utils.PermissionRequestMsg;
import com.charlezz.openglstudy.utils.PermissionResultBus;

import io.reactivex.disposables.CompositeDisposable;

@TargetApi(Build.VERSION_CODES.LOLLIPOP)
public class CameraRect extends TextureShape {

    public static final String TAG = CameraRect.class.getSimpleName();

    private static final int REQ_CODE_CAMERA = 0;

    private HandlerThread mBackgroundThread;
    private Handler mBackgroundHandler;
    private CaptureRequest mPreviewRequest;

    private SurfaceTexture texture;
    private int textureId;
    private String mCameraId;
    private CameraCaptureSession mCaptureSession;
    private CameraDevice mCameraDevice;

    private Semaphore mCameraOpenCloseLock = new Semaphore(1);

    private CompositeDisposable disposables = new CompositeDisposable();

    public CameraRect(){
        super();
        disposables.add(PermissionResultBus.getInstance().toObserverable().subscribe(permissionResultMsg -> {
            onRequestPermissionsResult(
                    permissionResultMsg.getRequestCode(),
                    permissionResultMsg.getPermissions(),
                    permissionResultMsg.getGrantResults());
        }, throwable -> {

        }));
    }

    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if(requestCode==REQ_CODE_CAMERA){
            for(int grant : grantResults){
                if(grant == PackageManager.PERMISSION_DENIED){
                    return;
                }
            }
            openCamera();
        }
    }

    @SuppressLint("MissingPermission")
    private void openCamera() {
        CameraManager manager = (CameraManager) App.getContext().getSystemService(Context.CAMERA_SERVICE);

        try {
            mCameraId = manager.getCameraIdList()[0];
        } catch (CameraAccessException e) {
            e.printStackTrace();
        }

        try {
            if (!mCameraOpenCloseLock.tryAcquire(2500, TimeUnit.MILLISECONDS)) {
                throw new RuntimeException("Time out waiting to lock camera opening.");
            }
            manager.openCamera(mCameraId, mStateCallback, mBackgroundHandler);
        } catch (CameraAccessException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            throw new RuntimeException("Interrupted while trying to lock camera opening.", e);
        }
    }

    @Override
    protected float[] getUVs() {
        return new float[]{
                0f,1f,
                1f,1f,
                1f,0f,
                0f,0f
        };
    }

    @Override
    protected void bindTexture(int textureId) {
        Log.e(TAG,"bindTexture");
        this.textureId = textureId;
        GLES20.glBindTexture(GLES11Ext.GL_TEXTURE_EXTERNAL_OES, textureId);
        GLES20.glTexParameteri(GLES11Ext.GL_TEXTURE_EXTERNAL_OES, GLES20.GL_TEXTURE_WRAP_S, GLES20.GL_CLAMP_TO_EDGE);
        GLES20.glTexParameteri(GLES11Ext.GL_TEXTURE_EXTERNAL_OES, GLES20.GL_TEXTURE_WRAP_T, GLES20.GL_CLAMP_TO_EDGE);
        GLES20.glTexParameteri(GLES11Ext.GL_TEXTURE_EXTERNAL_OES, GLES20.GL_TEXTURE_MIN_FILTER, GLES20.GL_LINEAR);
        GLES20.glTexParameteri(GLES11Ext.GL_TEXTURE_EXTERNAL_OES, GLES20.GL_TEXTURE_MAG_FILTER, GLES20.GL_LINEAR);
    }

    @Override
    protected String getVertexShader() {
        return "uniform mat4 uMVPMatrix;" +
                "attribute vec4 vPosition;" +
                "attribute vec2 a_texCoord;" +
                "varying vec2 v_texCoord;" +
                "void main() {" +
                "  gl_Position = uMVPMatrix * vPosition;" +
                "  v_texCoord = a_texCoord;"+
                "}";
    }

    @Override
    protected String getFragmentShader() {
        return "#extension GL_OES_EGL_image_external : require\n" +
                "precision mediump float;" +
                "varying vec2 v_texCoord;"+
                "uniform samplerExternalOES s_texture;"+
                "void main() {" +
                "  gl_FragColor = texture2D(s_texture, v_texCoord);" +
                "}";
    }

    @Override
    protected float[] getVertices() {
        return new float[]{
                -1f, -1f,  0f,  //bottom left
                1f, -1f,  0f,  //bottom right
                1f,  1f,  0f,  // top right
                -1f,  1f,  0f, // top left
        };
    }


    @Override
    protected int getCoordsPerVertex() {
        return 3;
    }

    @Override
    protected String getPositionHandleName() {
        return "vPosition";
    }

    @Override
    protected String getUVHandleName() {
        return "a_texCoord";
    }

    @Override
    protected String getMVPMatHandleName() {
        return "uMVPMatrix";
    }

    @Override
    protected byte[] getIndices() {
        return new byte[]{
                0,  1,  2,
                2,  3,  0,
        };
    }

    private void startBackgroundThread() {
        mBackgroundThread = new HandlerThread("CameraBackground");
        mBackgroundThread.start();
        mBackgroundHandler = new Handler(mBackgroundThread.getLooper());
    }

    private void stopBackgroundThread() {
        mBackgroundThread.quitSafely();
        try {
            mBackgroundThread.join();
            mBackgroundThread = null;
            mBackgroundHandler = null;
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void closeCamera() {
        try {
            mCameraOpenCloseLock.acquire();
            if (null != mCaptureSession) {
                mCaptureSession.close();
                mCaptureSession = null;
            }
            if (null != mCameraDevice) {
                mCameraDevice.close();
                mCameraDevice = null;
            }
        } catch (InterruptedException e) {
            throw new RuntimeException("Interrupted while trying to lock camera closing.", e);
        } finally {
            mCameraOpenCloseLock.release();
        }
    }

    @Override
    public void onResume() {
        startBackgroundThread();
        super.onResume();
    }

    @Override
    public void onPause() {
        closeCamera();
        stopBackgroundThread();
        super.onPause();
    }


    @Override
    public void onSurfaceChanged(int width, int height) {
        super.onSurfaceChanged(width, height);
        Matrix.setRotateM(modelMatrix, 0, 90, 0,0,-1);
        requestCameraPermission();
    }

    private final CameraDevice.StateCallback mStateCallback = new CameraDevice.StateCallback() {

        @Override
        public void onOpened(@NonNull CameraDevice cameraDevice) {
            Log.e(TAG,"onOpened");
            mCameraOpenCloseLock.release();
            mCameraDevice = cameraDevice;
            createCameraPreviewSession();
        }

        @Override
        public void onDisconnected(@NonNull CameraDevice cameraDevice) {
            Log.e(TAG,"onDisconnected");
            mCameraOpenCloseLock.release();
            cameraDevice.close();
            mCameraDevice = null;
        }

        @Override
        public void onError(@NonNull CameraDevice cameraDevice, int error) {
            Log.e(TAG,"onError");
            mCameraOpenCloseLock.release();
            cameraDevice.close();
            mCameraDevice = null;
        }

    };

    private void requestCameraPermission() {
        PermissionRequestBus.getInstance().send(PermissionRequestMsg.with(
                new String[]{Manifest.permission.CAMERA},
                REQ_CODE_CAMERA
        ));
    }

    private void createCameraPreviewSession() {
        Log.e(TAG,"createCameraPreviewSession");
        try {
            texture = new SurfaceTexture(textureId);
            texture.setDefaultBufferSize(1280,720);
            Surface surface = new Surface(texture);

            CaptureRequest.Builder mPreviewRequestBuilder = mCameraDevice.createCaptureRequest(CameraDevice.TEMPLATE_PREVIEW);
            mPreviewRequestBuilder.addTarget(surface);

            mCameraDevice.createCaptureSession(Arrays.asList(surface),
                    new CameraCaptureSession.StateCallback() {

                        @Override
                        public void onConfigured(@NonNull CameraCaptureSession cameraCaptureSession) {
                            Log.e(TAG,"onConfigured");
                            if (null == mCameraDevice) {
                                Log.e(TAG,"mCameraDevice is null");
                                return;
                            }

                            mCaptureSession = cameraCaptureSession;
                            try {
                                mPreviewRequestBuilder.set(CaptureRequest.CONTROL_AF_MODE, CaptureRequest.CONTROL_AF_MODE_CONTINUOUS_PICTURE);
                                mPreviewRequest = mPreviewRequestBuilder.build();
                                mCaptureSession.setRepeatingRequest(mPreviewRequest, null, mBackgroundHandler);
                            } catch (CameraAccessException e) {
                                e.printStackTrace();
                            }
                        }

                        @Override
                        public void onConfigureFailed(@NonNull CameraCaptureSession cameraCaptureSonFrameAvailableession) {
                            Log.e(TAG,"onConfigureFailed");
                        }
                    }, null
            );
        } catch (CameraAccessException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onDrawFrame(float[] matrix) {
        if(texture!=null){
            texture.updateTexImage();
            super.onDrawFrame(matrix);
            return;
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if(!disposables.isDisposed()){
            disposables.dispose();
        }
    }
}
