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

    protected abstract float[] getColors();

    protected abstract String getPositionHandleName();

    protected abstract String getColorHandleName();

    protected abstract String getMVPMatHandleName();

    private int program;
    private static final int SIZE_OF_FLOAT = 4;
    private static final int VALUES_PER_COLOR = 4;
    private int vertexStride = getCoordsPerVertex() * SIZE_OF_FLOAT;
    private int colorStride = VALUES_PER_COLOR * SIZE_OF_FLOAT;
    private final FloatBuffer vertexBuffer;
    private final ByteBuffer indexBuffer;
    private final FloatBuffer colorBuffer;
    private final int positionHandle;
    private final int colorHandle;
    private final int mvpMatrixHandle;

    protected final float[] modelMatrix = new float[16];
    protected final float[] mvpMatrix = new float[16];

    public Shape(){
        ByteBuffer byteBuffer = ByteBuffer.allocateDirect(getVertices().length * SIZE_OF_FLOAT);
        byteBuffer.order(ByteOrder.nativeOrder());
        vertexBuffer = byteBuffer.asFloatBuffer();
        vertexBuffer.put(getVertices());
        vertexBuffer.position(0);

        byteBuffer = ByteBuffer.allocateDirect(getColors().length* SIZE_OF_FLOAT);
        byteBuffer.order(ByteOrder.nativeOrder());
        colorBuffer = byteBuffer.asFloatBuffer();
        colorBuffer.put(getColors());
        colorBuffer.position(0);

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

    public void draw(float[] matrix){
        GLES20.glUseProgram(program);
        GLES20.glEnableVertexAttribArray(positionHandle);
        GLES20.glVertexAttribPointer(positionHandle, getCoordsPerVertex(), GLES20.GL_FLOAT, false, vertexStride, vertexBuffer);

        GLES20.glEnableVertexAttribArray(colorHandle);
        GLES20.glVertexAttribPointer(colorHandle, SIZE_OF_FLOAT, GLES20.GL_FLOAT, false, colorStride, colorBuffer);

        Matrix.multiplyMM(mvpMatrix, 0, matrix, 0, modelMatrix,0);
        GLES20.glUniformMatrix4fv(mvpMatrixHandle, 1, false, mvpMatrix, 0);

        GLES20.glDrawElements(GLES20.GL_TRIANGLES, getIndices().length, GLES20.GL_UNSIGNED_BYTE, indexBuffer);

//        GLES20.glDrawArrays(GLES20.GL_TRIANGLES, 0, 3);
//        GLES20.glDisableVertexAttribArray(positionHandle);
//        GLES20.glDisableVertexAttribArray(mvpMatrixHandle);
    }





}
