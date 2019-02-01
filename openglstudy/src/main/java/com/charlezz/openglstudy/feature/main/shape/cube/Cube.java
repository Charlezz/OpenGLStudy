package com.charlezz.openglstudy.feature.main.shape.cube;

import java.util.concurrent.TimeUnit;

import android.opengl.Matrix;
import android.os.SystemClock;

import com.charlezz.openglstudy.feature.main.shape.SolidColorShape;

public class Cube extends SolidColorShape {
    @Override
    protected String getVertexShader() {
        return "uniform mat4 uMVPMatrix;" +
                "attribute vec4 vPosition;" +
                "attribute vec4 vColor;" +
                "varying vec4 _vColor;" +
                "void main() {" +
                "  _vColor = vColor;" +
                "  gl_Position = uMVPMatrix * vPosition;" +
                "}";
    }

    @Override
    protected String getFragmentShader() {
        return "precision mediump float;" +
                "varying vec4 _vColor;" +
                "void main() {" +
                "  gl_FragColor = _vColor;" +
                "}";
    }

    @Override
    protected float[] getVertices() {
        return new float[]{
                -0.5f, -0.5f, -0.5f,
                0.5f, -0.5f, -0.5f,
                0.5f, 0.5f, -0.5f,
                -0.5f, 0.5f, -0.5f,
                -0.5f, -0.5f, 0.5f,
                0.5f, -0.5f, 0.5f,
                0.5f, 0.5f, 0.5f,
                -0.5f, 0.5f, 0.5f
        };
    }

    @Override
    protected int getCoordsPerVertex() {
        return 3;
    }

    @Override
    protected byte[] getIndices() {
        return new byte[]{
                0, 1, 3, 3, 1, 2, // Front face.
                0, 1, 4, 4, 5, 1, // Bottom face.
                1, 2, 5, 5, 6, 2, // Right face.
                2, 3, 6, 6, 7, 3, // Top face.
                3, 7, 4, 4, 3, 0, // Left face.
                4, 5, 7, 7, 6, 5, // Rear face.
        };
    }

    @Override
    protected float[] getColors() {
        return new float[]{
                0.0f, 1.0f, 1.0f, 1.0f,
                1.0f, 0.0f, 0.0f, 1.0f,
                1.0f, 1.0f, 0.0f, 1.0f,
                0.0f, 1.0f, 0.0f, 1.0f,
                0.0f, 0.0f, 1.0f, 1.0f,
                1.0f, 0.0f, 1.0f, 1.0f,
                1.0f, 1.0f, 1.0f, 1.0f,
                0.0f, 1.0f, 1.0f, 1.0f,
        };
    }

    @Override
    protected String getPositionHandleName() {
        return "vPosition";
    }

    @Override
    protected String getColorHandleName() {
        return "vColor";
    }

    @Override
    protected String getMVPMatHandleName() {
        return "uMVPMatrix";
    }

    @Override
    public void draw(float[] matrix) {
        super.draw(matrix);
        updateRotation();
        Matrix.setRotateM(modelMatrix,0, rotation, 1.0f, 1.0f, 1.0f);
    }

    private long lastUpdateMillis = 0;
    private int REFRESH_RATE_FPS = 60;
    private long FRAME_TIME_MILLIS = TimeUnit.SECONDS.toMillis(1) / REFRESH_RATE_FPS;
    private float rotation = 0f;
    private float ROTATION_INCREMENT = 1f;

    private void updateRotation() {
        long currentTime = SystemClock.elapsedRealtime();
        if (lastUpdateMillis != 0L) {
            long factor = (lastUpdateMillis-currentTime) / FRAME_TIME_MILLIS;
            rotation += ROTATION_INCREMENT * factor;
            rotation = rotation%360;
        }
        lastUpdateMillis = currentTime;
    }
}
