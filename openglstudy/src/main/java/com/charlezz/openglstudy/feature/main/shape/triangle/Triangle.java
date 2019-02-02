package com.charlezz.openglstudy.feature.main.shape.triangle;

import com.charlezz.openglstudy.feature.main.shape.base.SolidColorShape;

public class Triangle extends SolidColorShape {
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
                -0.5f, -0.5f, 0.0f,
                0.5f, -0.5f, 0.0f,
                0.0f, 0.5f, 0.0f
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
    protected String getColorHandleName() {
        return "vColor";
    }

    @Override
    protected String getMVPMatHandleName() {
        return "uMVPMatrix";
    }

    @Override
    protected byte[] getIndices() {
        return new byte[]{0,1,2};
    }

    @Override
    protected float[] getColors() {
        return new float[]{
                0.0f,1.0f,0.0f,1.0f,
                0.0f,1.0f,0.0f,1.0f,
                0.0f,1.0f,0.0f,1.0f
        };
    }
}
