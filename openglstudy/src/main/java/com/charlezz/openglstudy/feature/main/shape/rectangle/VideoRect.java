package com.charlezz.openglstudy.feature.main.shape.rectangle;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

import android.graphics.SurfaceTexture;
import android.media.MediaPlayer;
import android.opengl.GLES11Ext;
import android.opengl.GLES20;
import android.opengl.Matrix;
import android.view.Surface;

import com.charlezz.openglstudy.feature.main.shape.base.TextureShape;

public class VideoRect
        extends TextureShape
        implements SurfaceTexture.OnFrameAvailableListener {

    private FloatBuffer uvBuffer;
    private MediaPlayer mediaPlayer = new MediaPlayer();
    private SurfaceTexture surfaceTexture;


    public VideoRect(String path){
        super();

        mediaPlayer = new MediaPlayer();

        try {
            mediaPlayer.setDataSource(path);
            mediaPlayer.prepare();
            mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mp) {
                    mp.start();
                }
            });
            mediaPlayer.start();


        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void onPrepare(int width, int height) {
        super.onPrepare(width, height);

        ByteBuffer bb = ByteBuffer.allocateDirect(getUVs().length*SIZE_OF_FLOAT);
        bb.order(ByteOrder.nativeOrder());
        uvBuffer= bb.asFloatBuffer();
        uvBuffer.put(getUVs());
        uvBuffer.position(0);

    }


    @Override
    public void onDraw(float[] matrix) {
        super.onDraw(matrix);

        try {
            semaphore.tryAcquire(16, TimeUnit.MILLISECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        surfaceTexture.updateTexImage();
    }

    private Semaphore semaphore = new Semaphore(1);
    @Override
    public void onFrameAvailable(SurfaceTexture surface) {
        semaphore.release();
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
    protected void bindTexture(int textureId) {
        GLES20.glActiveTexture(GLES20.GL_TEXTURE0);
        GLES20.glBindTexture(GLES11Ext.GL_TEXTURE_EXTERNAL_OES, textureId);
        GLES20.glTexParameterf(GLES11Ext.GL_TEXTURE_EXTERNAL_OES, GLES20.GL_TEXTURE_MIN_FILTER, GLES20.GL_NEAREST);
        GLES20.glTexParameterf(GLES11Ext.GL_TEXTURE_EXTERNAL_OES, GLES20.GL_TEXTURE_MAG_FILTER, GLES20.GL_LINEAR);

        surfaceTexture = new SurfaceTexture(textureId);
        surfaceTexture.setOnFrameAvailableListener(this);
        final Surface surface = new Surface(surfaceTexture);
        mediaPlayer.setSurface(surface);
        surface.release();

        Matrix.setIdentityM(modelMatrix, 0);
        Matrix.scaleM(modelMatrix, 0, 1, (float)mediaPlayer.getVideoHeight()/(float)mediaPlayer.getVideoWidth(),0);



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
        return "#extension GL_OES_EGL_image_external : require\n" +
                "precision mediump float;" +
                "varying vec2 v_texCoord;"+
                "uniform samplerExternalOES s_texture;"+
                "void main() {" +
                "  gl_FragColor = texture2D(s_texture, v_texCoord);" +
                "}";
    }

    @Override
    protected float[] getVertices() {
        return new float[]{
                -1f, -1f,  0f,  //bottom left
                1f, -1f,  0f,  //bottom right
                1f,  1f,  0f,  // top right
                -1f,  1f,  0f, // top left
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
}
