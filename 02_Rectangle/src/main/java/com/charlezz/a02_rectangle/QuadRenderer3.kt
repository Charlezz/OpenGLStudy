package com.charlezz.a02_rectangle

import android.opengl.GLES20.*
import android.opengl.GLSurfaceView
import android.opengl.Matrix
import android.view.MotionEvent
import android.view.View
import java.nio.Buffer
import java.nio.ByteBuffer
import java.nio.ByteOrder
import javax.microedition.khronos.egl.EGLConfig
import javax.microedition.khronos.opengles.GL10

class QuadRenderer3 : GLSurfaceView.Renderer, View.OnTouchListener{


    val vertexShader = "" +
            "uniform mat4 mvpMatrix;"+
            "attribute vec4 vPosition;" +
            "void main() {" +
            "  gl_Position = mvpMatrix*vPosition;" +
            "}"

    val fragmentShader = "" +
            "precision mediump float;" +
            "void main() {" +
            "  gl_FragColor = vec4(0.0, 0.0, 1.0, 1.0);" +
            "}"

    val quadVertices: Buffer = GlUtil.createFloatBuffer(arrayOf(
            -0.5f,  0.5f,
            -0.5f, -0.5f,
            0.5f, -0.5f,
            0.5f,  0.5f
    ).toFloatArray())

    val order = arrayOf(0,1,2,0,2,3).toIntArray()
    val orderBuffer = ByteBuffer.allocateDirect(order.size * 4).let {
        it.order(ByteOrder.nativeOrder())
        it.asIntBuffer()
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
    var finalMVPMatrix = FloatArray(16)
    var rotationMatrix = FloatArray(16)


    init {
        Matrix.setLookAtM(viewMatrix, 0,
                0.0f, 0.0f, -8.0f,
                0.0f, 0.0f, 0.0f,
                0.0f, 1f, 0.0f)
    }


    override fun onSurfaceCreated(gl: GL10, p1: EGLConfig) {
        //nothing to do
        program = GlUtil.createProgram(vertexShader, fragmentShader)
        vPositionHandle = glGetAttribLocation(program, "vPosition")
        mvpMatrixHandle = glGetUniformLocation(program, "mvpMatrix")
    }
    override fun onSurfaceChanged(gl: GL10, width: Int, height: Int) {
        glViewport(0, 0, width, height)

        val ratio = width.toFloat()/height
        Matrix.perspectiveM(projectionMatrix, 0, 60f, ratio, 0.0f,10.0f)
        Matrix.multiplyMM(mvpMatrix, 0, projectionMatrix, 0, viewMatrix, 0)
    }

    override fun onDrawFrame(p0: GL10?) {
        glClearColor(1.0f, 1.0f, 1.0f, 1.0f)
        glClear(GL_DEPTH_BUFFER_BIT or GL_COLOR_BUFFER_BIT)
        glUseProgram(program)
        glVertexAttribPointer(vPositionHandle, 2, GL_FLOAT, false, 0, quadVertices)
        glEnableVertexAttribArray(vPositionHandle)


        Matrix.setRotateM(rotationMatrix, 0, 45f, 0f,1f,0f)
        Matrix.multiplyMM(finalMVPMatrix, 0, mvpMatrix, 0, rotationMatrix, 0)

        glUniformMatrix4fv(mvpMatrixHandle, 1, false, finalMVPMatrix, 0)

        glDrawElements(GL_TRIANGLES, order.size, GL_UNSIGNED_INT, orderBuffer)
    }
    override fun onTouch(p0: View?, p1: MotionEvent?): Boolean {
        return false
    }


}