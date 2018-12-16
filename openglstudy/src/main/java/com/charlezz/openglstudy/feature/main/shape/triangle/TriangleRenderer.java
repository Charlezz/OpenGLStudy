package com.charlezz.openglstudy.feature.main.shape.triangle;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import android.opengl.Matrix;

public class TriangleRenderer implements GLSurfaceView.Renderer {
    private Triangle triangle;

    private final float[] projectionMatrix;
    private final float[] viewMatrix;
    private final float[] vpMatrix;

    public TriangleRenderer(){
        projectionMatrix = new float[16];
        viewMatrix = new float[16];
        vpMatrix = new float[16];

        Matrix.setIdentityM(projectionMatrix, 0);
        Matrix.setIdentityM(viewMatrix, 0);
        Matrix.setIdentityM(vpMatrix, 0);
    }

    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {
        GLES20.glClearColor(1.0f, 0.0f, 0.0f, 1.0f);
        GLES20.glClearDepthf(1.0f);
        GLES20.glEnable(GLES20.GL_DEPTH_TEST);
        GLES20.glDepthFunc(GLES20.GL_LEQUAL);

        triangle = new Triangle();
    }

    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height) {
        GLES20.glViewport(0,0,width, height);

        float aspectRatio = (float) width/ height;
        Matrix.frustumM(projectionMatrix, 0, -aspectRatio, aspectRatio, -1.0f, 1.0f, 3.0f, 7.0f);
        Matrix.setLookAtM(viewMatrix, 0,
                0.0f, 1.0f, -4.0f,
                0.0f, 0.0f, 0.0f,
                0.0f, 1.0f, 0.0f);

        // mvp = p * v * m (곱하는 순서 중요함)

        Matrix.multiplyMM(vpMatrix, 0, projectionMatrix, 0, viewMatrix, 0);
    }

    @Override
    public void onDrawFrame(GL10 gl) {
        GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT | GLES20.GL_DEPTH_BUFFER_BIT);
        triangle.draw(vpMatrix);
    }
}
