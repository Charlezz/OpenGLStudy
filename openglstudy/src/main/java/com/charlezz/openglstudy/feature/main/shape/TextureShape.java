package com.charlezz.openglstudy.feature.main.shape;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import android.graphics.Bitmap;
import android.opengl.GLES20;
import android.opengl.GLUtils;
import android.opengl.Matrix;

public abstract class TextureShape extends Shape {

    private int[] textures = new int[1];

    protected abstract float[] getUVs();
    private FloatBuffer uvBuffer;
    protected TextureShape(Bitmap bitmap){
        super();

        GLES20.glGenTextures(1, textures, 0);
        GLES20.glActiveTexture(GLES20.GL_TEXTURE0);
        GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, textures[0]);
        GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_MAG_FILTER, GLES20.GL_LINEAR);
        GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_MIN_FILTER, GLES20.GL_LINEAR);
        GLUtils.texImage2D(GLES20.GL_TEXTURE_2D, 0, bitmap, 0);

        ByteBuffer bb = ByteBuffer.allocateDirect(getUVs().length*SIZE_OF_FLOAT);
        bb.order(ByteOrder.nativeOrder());
        uvBuffer= bb.asFloatBuffer();
        uvBuffer.put(getUVs());
        uvBuffer.position(0);

        if(!bitmap.isRecycled()){
            bitmap.recycle();
        }
    }
    @Override
    public void draw(float[] matrix) {
        GLES20.glUseProgram(program);
        GLES20.glEnableVertexAttribArray(positionHandle);
        GLES20.glVertexAttribPointer(positionHandle, getCoordsPerVertex(), GLES20.GL_FLOAT, false, vertexStride, vertexBuffer);

        GLES20.glEnableVertexAttribArray(colorHandle);
        GLES20.glVertexAttribPointer(colorHandle, 2, GLES20.GL_FLOAT, false, 0, uvBuffer);

        Matrix.multiplyMM(mvpMatrix, 0, matrix, 0, modelMatrix,0);
        GLES20.glUniformMatrix4fv(mvpMatrixHandle, 1, false, mvpMatrix, 0);

        GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, textures[0]);
        GLES20.glDrawElements(GLES20.GL_TRIANGLES, getIndices().length, GLES20.GL_UNSIGNED_BYTE, indexBuffer);
    }
}
