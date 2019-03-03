package com.charlezz.openglstudy.feature.main.shape.base;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import android.opengl.GLES20;
import android.opengl.Matrix;

public abstract class TextureShape extends Shape {

    private FloatBuffer uvBuffer;
    protected int[] textures = new int[1];

    protected abstract float[] getUVs();
    protected abstract String getUVHandleName();
    protected abstract void bindTexture(int textureId);
    protected int uvHandle;

    @Override
    public void onDrawFrame(float[] matrix) {
        GLES20.glUseProgram(program);
        GLES20.glEnableVertexAttribArray(positionHandle);
        GLES20.glVertexAttribPointer(positionHandle, getCoordsPerVertex(), GLES20.GL_FLOAT, false, vertexStride, vertexBuffer);

        GLES20.glEnableVertexAttribArray(uvHandle);
        GLES20.glVertexAttribPointer(uvHandle, 2, GLES20.GL_FLOAT, false, 0, uvBuffer);

        Matrix.multiplyMM(mvpMatrix, 0, matrix, 0, modelMatrix,0);
        GLES20.glUniformMatrix4fv(mvpMatrixHandle, 1, false, mvpMatrix, 0);

        GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, textures[0]);
        GLES20.glDrawElements(GLES20.GL_TRIANGLES, getIndices().length, GLES20.GL_UNSIGNED_BYTE, indexBuffer);
    }

    @Override
    public void onSurfaceChanged(int width, int height) {
        super.onSurfaceChanged(width, height);
        uvHandle = GLES20.glGetAttribLocation(program, getUVHandleName());
        ByteBuffer bb = ByteBuffer.allocateDirect(getUVs().length*SIZE_OF_FLOAT);
        bb.order(ByteOrder.nativeOrder());
        uvBuffer= bb.asFloatBuffer();
        uvBuffer.put(getUVs());
        uvBuffer.position(0);

        GLES20.glGenTextures(1, textures, 0);
        bindTexture(textures[0]);
    }
}
