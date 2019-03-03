package com.charlezz.openglstudy.feature.main.shape.base;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import android.opengl.GLES20;
import android.opengl.Matrix;

public abstract class SolidColorShape extends Shape {
    protected abstract String getColorHandleName();
    protected abstract float[] getColors();
    protected int colorHandle;

    protected FloatBuffer colorBuffer;

    @Override
    public void onDrawFrame(float[] matrix) {
        GLES20.glUseProgram(program);
        GLES20.glEnableVertexAttribArray(positionHandle);
        GLES20.glVertexAttribPointer(positionHandle, getCoordsPerVertex(), GLES20.GL_FLOAT, false, vertexStride, vertexBuffer);

        GLES20.glEnableVertexAttribArray(colorHandle);
        GLES20.glVertexAttribPointer(colorHandle, SIZE_OF_FLOAT, GLES20.GL_FLOAT, false, colorStride, colorBuffer);

        Matrix.multiplyMM(mvpMatrix, 0, matrix, 0, modelMatrix,0);
        GLES20.glUniformMatrix4fv(mvpMatrixHandle, 1, false, mvpMatrix, 0);

        GLES20.glDrawElements(GLES20.GL_TRIANGLES, getIndices().length, GLES20.GL_UNSIGNED_BYTE, indexBuffer);
    }

    @Override
    public void onSurfaceChanged(int width, int height) {
        super.onSurfaceChanged(width, height);
        ByteBuffer byteBuffer = ByteBuffer.allocateDirect(getColors().length * SIZE_OF_FLOAT);
        byteBuffer.order(ByteOrder.nativeOrder());
        colorHandle = GLES20.glGetAttribLocation(program, getColorHandleName());
        colorBuffer = byteBuffer.asFloatBuffer();
        colorBuffer.put(getColors());
        colorBuffer.position(0);
    }
}
