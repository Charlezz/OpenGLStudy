package com.charlezz.openglstudy.feature.main.shape.base;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import android.opengl.GLES20;
import android.opengl.Matrix;

import com.charlezz.openglstudy.utils.GlUtil;

public abstract class Shape {

    protected abstract String getVertexShader();

    protected abstract String getFragmentShader();

    protected abstract float[] getVertices();

    protected abstract int getCoordsPerVertex();

    protected abstract byte[] getIndices();

    protected abstract String getPositionHandleName();

    protected abstract String getMVPMatHandleName();

    protected abstract void onDrawFrame(float[] matrix);

    protected int program;
    protected static final int SIZE_OF_FLOAT = 4;
    protected static final int VALUES_PER_COLOR = 4;
    protected int vertexStride = getCoordsPerVertex() * SIZE_OF_FLOAT;
    protected int colorStride = VALUES_PER_COLOR * SIZE_OF_FLOAT;
    protected final FloatBuffer vertexBuffer;
    protected final ByteBuffer indexBuffer;
    protected int positionHandle;
    protected int mvpMatrixHandle;

    protected final float[] modelMatrix = new float[16];
    protected final float[] mvpMatrix = new float[16];

    public Shape(){
        ByteBuffer byteBuffer = ByteBuffer.allocateDirect(getVertices().length * SIZE_OF_FLOAT);
        byteBuffer.order(ByteOrder.nativeOrder());
        vertexBuffer = byteBuffer.asFloatBuffer();
        vertexBuffer.put(getVertices());
        vertexBuffer.position(0);

        indexBuffer = ByteBuffer.allocateDirect(getIndices().length);
        indexBuffer.put(getIndices());
        indexBuffer.position(0);
    }

    public void onSurfaceChanged(int width, int height){

        positionHandle = GLES20.glGetAttribLocation(program, getPositionHandleName());
        mvpMatrixHandle = GLES20.glGetUniformLocation(program, getMVPMatHandleName());

        GLES20.glUniformMatrix4fv(mvpMatrixHandle, 1, false, mvpMatrix, 0);
        Matrix.setIdentityM(modelMatrix, 0);
        Matrix.setIdentityM(mvpMatrix, 0);
    }

    public void onSurfaceCreated(){
        program = GlUtil.createProgram(getVertexShader(), getFragmentShader());
    }

    public void onResume(){

    }

    public void onPause(){

    }

    public void onDestroy(){

    }
}
