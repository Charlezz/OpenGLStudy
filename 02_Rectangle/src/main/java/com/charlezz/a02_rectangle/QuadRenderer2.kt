package com.charlezz.a02_rectangle

import android.opengl.GLES20.*
import android.opengl.GLSurfaceView
import com.charlezz.a02_rectangle.GlUtil.createProgram
import java.nio.Buffer
import java.nio.ByteBuffer
import java.nio.ByteOrder
import javax.microedition.khronos.egl.EGLConfig
import javax.microedition.khronos.opengles.GL10

class QuadRenderer2 : GLSurfaceView.Renderer {

    val vertexShader = "" +
            "attribute vec4 vPosition;" +
            "void main() {" +
            "  gl_Position = vPosition;" +
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
    var program: Int = 0
    var flag = false
    override fun onDrawFrame(p0: GL10?) {
        glClearColor(1.0f, 1.0f, 1.0f, 1.0f)
        glClear(GL_DEPTH_BUFFER_BIT or GL_COLOR_BUFFER_BIT)
        glUseProgram(program)
        glVertexAttribPointer(vPositionHandle, 2, GL_FLOAT, false, 0, quadVertices)
        glEnableVertexAttribArray(vPositionHandle)
        glDrawElements(GL_TRIANGLES, order.size, GL_UNSIGNED_INT, orderBuffer)
    }
    override fun onSurfaceChanged(gl: GL10, width: Int, height: Int) {
        program = createProgram(vertexShader, fragmentShader)
        vPositionHandle = glGetAttribLocation(program, "vPosition")
        glViewport(0, 0, width, height)
    }

    override fun onSurfaceCreated(gl: GL10, p1: EGLConfig) {
        //nothing to do
    }
}