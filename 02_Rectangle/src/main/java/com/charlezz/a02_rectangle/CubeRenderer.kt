package com.charlezz.a02_rectangle

import android.opengl.GLES20
import android.opengl.GLES20.*
import android.opengl.GLSurfaceView
import android.opengl.Matrix
import android.os.SystemClock
import android.util.Log
import com.charlezz.a02_rectangle.GlUtil.createProgram
import java.nio.Buffer
import java.nio.ByteBuffer
import java.nio.ByteOrder
import java.util.concurrent.TimeUnit
import javax.microedition.khronos.egl.EGLConfig
import javax.microedition.khronos.opengles.GL10







class CubeRenderer : GLSurfaceView.Renderer {

val TAG = CubeRenderer::class.java.simpleName
    val vertexShader = "" +
            "uniform mat4 uMVPMatrix;" +
            "attribute vec4 vPosition;" +
            "void main() {" +
            "  gl_Position = uMVPMatrix*vPosition;" +
            "}"

    val fragmentShader = "" +
            "precision mediump float;" +
            "void main() {" +
            "  gl_FragColor = vec4(0.0, 0.0, 1.0, 1.0);" +
            "}"

    val cubeVertices: Buffer = GlUtil.createFloatBuffer(arrayOf(
            -0.5f, -0.5f, -0.5f,
            0.5f, -0.5f, -0.5f,
            0.5f, 0.5f, -0.5f,
            -0.5f, 0.5f, -0.5f,
            -0.5f, -0.5f, 0.5f,
            0.5f, -0.5f, 0.5f,
            0.5f, 0.5f, 0.5f,
            -0.5f, 0.5f, 0.5f
    ).toFloatArray())

    val order = byteArrayOf(
            0, 1, 3, 3, 1, 2, // Front face.
            0, 1, 4, 4, 5, 1, // Bottom face.
            1, 2, 5, 5, 6, 2, // Right face.
            2, 3, 6, 6, 7, 3, // Top face.
            3, 7, 4, 4, 3, 0, // Left face.
            4, 5, 7, 7, 6, 5 // Rear face.
    )
    val orderBuffer = ByteBuffer.allocateDirect(order.size * 4).let {
        it.order(ByteOrder.nativeOrder())
    }.apply {
        put(order)
        position(0)
    }

    var vPositionHandle: Int = 0
    var mvpMatrixHandle:Int = 0
    var program: Int = 0
    var flag = false

    var mvpMatrix: FloatArray = FloatArray(16)
    var projectionMatrix: FloatArray = FloatArray(16)
    var viewMatrix: FloatArray = FloatArray(16)
    var rotationMatrix = FloatArray(16)
    var finalMVPMatrix = FloatArray(16)

    private var mCubeRotation: Float = 0f
    private var mLastUpdateMillis: Long = 0


    val CUBE_ROTATION_INCREMENT = 0.6f
    val REFRESH_RATE_FPS = 60
    val FRAME_TIME_MILLIS = TimeUnit.SECONDS.toMillis(1) / REFRESH_RATE_FPS

    init {
        Matrix.setLookAtM(viewMatrix, 0,
                0.0f, 0.0f, -4.0f,
                0.0f, 0.0f, 0.0f,
                0.0f, 1f, 0.0f)
    }

    override fun onSurfaceCreated(gl: GL10, p1: EGLConfig) {
        GLES20.glClearColor(0.0f, 0.0f, 0.0f, 1.0f)
        GLES20.glClearDepthf(1.0f)
        GLES20.glEnable(GLES20.GL_DEPTH_TEST)
        GLES20.glDepthFunc(GLES20.GL_LEQUAL)


        program = createProgram(vertexShader, fragmentShader)
        mvpMatrixHandle = glGetUniformLocation(program, "uMVPMatrix")
        vPositionHandle = glGetAttribLocation(program, "vPosition")
    }


    override fun onSurfaceChanged(gl: GL10, width: Int, height: Int) {

        val ratio = width.toFloat()/height
        glViewport(0, 0, width, height)
//        Matrix.frustumM(projectionMatrix, 0, -ratio, ratio, -1.0f, 1.0f, 3.0f, 7.0f)
        Matrix.perspectiveM(projectionMatrix, 0, 60f, ratio, 3f,7f)
        // modelView = projection x view
        Matrix.multiplyMM(mvpMatrix, 0, projectionMatrix, 0, viewMatrix, 0)

    }

    override fun onDrawFrame(gl: GL10?) {
        glClearColor(1.0f, 1.0f, 1.0f, 1.0f)
        glClear(GL_DEPTH_BUFFER_BIT or GL_COLOR_BUFFER_BIT)


        Matrix.setRotateM(rotationMatrix, 0, mCubeRotation, 1.0f,1.0f,1.0f)
        Matrix.multiplyMM(finalMVPMatrix, 0, mvpMatrix, 0, rotationMatrix, 0)

        glUseProgram(program)
        glEnableVertexAttribArray(vPositionHandle)
        glVertexAttribPointer(vPositionHandle, 3, GL_FLOAT, false, 0, cubeVertices)

//        glUniformMatrix4fv(mvpMatrixHandle, 1, false, mvpMatrix, 0)
        glUniformMatrix4fv(mvpMatrixHandle, 1, false, finalMVPMatrix, 0)

        glDrawElements(GL_TRIANGLES, order.size, GL_UNSIGNED_BYTE, orderBuffer)

        GLES20.glDisableVertexAttribArray(vPositionHandle)
        updateCubeRotation()

        Log.e(TAG,"mCubeRotation:"+mCubeRotation)
    }

    private fun updateCubeRotation() {
        if (mLastUpdateMillis != 0L) {
            val factor = (SystemClock.elapsedRealtime() - mLastUpdateMillis) / FRAME_TIME_MILLIS
            mCubeRotation += CUBE_ROTATION_INCREMENT * factor
        }
        mLastUpdateMillis = SystemClock.elapsedRealtime()
    }
}