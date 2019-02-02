package com.charlezz.openglstudy.feature.main.shape.rectangle;

import android.graphics.Bitmap;
import android.opengl.GLES20;
import android.opengl.GLUtils;

import com.charlezz.openglstudy.feature.main.shape.base.TextureShape;

public class Texture2DRect extends TextureShape {

    private Bitmap bitmap;

    public Texture2DRect(Bitmap bitmap) {
        super();
        this.bitmap = bitmap;
    }

    @Override
    protected void bindTexture(int textureId) {
        GLES20.glActiveTexture(GLES20.GL_TEXTURE0);
        GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, textureId);
        GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_MAG_FILTER, GLES20.GL_LINEAR);
        GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_MIN_FILTER, GLES20.GL_LINEAR);
        GLUtils.texImage2D(GLES20.GL_TEXTURE_2D, 0, bitmap, 0);
        if(!bitmap.isRecycled()){
            bitmap.recycle();
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
        return "precision mediump float;" +
                "varying vec2 v_texCoord;"+
                "uniform sampler2D s_texture;"+
                "void main() {" +
                "  gl_FragColor = texture2D(s_texture, v_texCoord);" +
                "}";
    }

    @Override
    protected float[] getVertices() {
        return new float[]{
                -0.5f, -0.5f,  0f,  //bottom left
                0.5f, -0.5f,  0f,  //bottom right
                0.5f,  0.5f,  0f,  // top right
                -0.5f,  0.5f,  0f, // top left
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




    /**
     * VERTEX
     * (3)------(2)
     *  ㅣ     / ㅣ
     *  ㅣ   /   ㅣ
     *  ㅣ /     ㅣ
     * (0)------(1)
     *
     * UV
     * (0,0)---(1,0)
     *  ㅣ     / ㅣ
     *  ㅣ   /   ㅣ
     *  ㅣ /     ㅣ
     * (0,1)---(1,1)
     */



}
