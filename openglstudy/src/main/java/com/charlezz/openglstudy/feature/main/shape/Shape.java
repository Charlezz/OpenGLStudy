package com.charlezz.openglstudy.feature.main.shape;

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

    protected abstract String getColorHandleName();

    protected abstract String getMVPMatHandleName();

    protected int program;
    protected static final int SIZE_OF_FLOAT = 4;
    protected static final int VALUES_PER_COLOR = 4;
    protected int vertexStride = getCoordsPerVertex() * SIZE_OF_FLOAT;
    protected int colorStride = VALUES_PER_COLOR * SIZE_OF_FLOAT;
    protected final FloatBuffer vertexBuffer;
    protected final ByteBuffer indexBuffer;
    protected final int positionHandle;
    protected final int colorHandle;
    protected final int mvpMatrixHandle;

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

        program = GLES20.glCreateProgram();
        GLES20.glAttachShader(program, GlUtil.loadShader(GLES20.GL_VERTEX_SHADER, getVertexShader()));
        GLES20.glAttachShader(program, GlUtil.loadShader(GLES20.GL_FRAGMENT_SHADER, getFragmentShader()));
        GLES20.glLinkProgram(program);

        positionHandle = GLES20.glGetAttribLocation(program, getPositionHandleName());
        colorHandle = GLES20.glGetAttribLocation(program, getColorHandleName());
        mvpMatrixHandle = GLES20.glGetUniformLocation(program, getMVPMatHandleName());

        GLES20.glUniformMatrix4fv(mvpMatrixHandle, 1, false, mvpMatrix, 0);
        Matrix.setIdentityM(modelMatrix, 0);
        Matrix.setIdentityM(mvpMatrix, 0);
    }

    public abstract  void draw(float[] matrix);

}
