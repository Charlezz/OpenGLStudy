package com.charlezz.a01_triangle

import android.opengl.GLES20.*
import android.opengl.GLSurfaceView
import com.charlezz.a01_triangle.GlUtil.checkGlError
import com.charlezz.a01_triangle.GlUtil.createProgram
import java.nio.Buffer
import javax.microedition.khronos.egl.EGLConfig
import javax.microedition.khronos.opengles.GL10

class QuadRenderer : GLSurfaceView.Renderer {

    val vertexShader = "" +
            "attribute vec4 vPosition;" +
            "void main() {" +
            "  gl_Position = vPosition;" +
            "}"

    val fragmentShader = "" +
            "precision mediump float;" +
            "void main() {" +
            "  gl_FragColor = vec4(1.0, 0.0, 0.0, 1.0);" +
            "}"

    val triangleVertices: Buffer = GlUtil.createFloatBuffer(arrayOf(
            -0.5f, 0.5f,
            -0.5f, -0.5f,
            0.5f, -0.5f,

            -0.5f, 0.5f,
            0.5f, -0.5f,
            0.5f, 0.5f
    ).toFloatArray())

//    val drawOrder = arrayOf(0,1,2,0,2,3)
    var vPositionHandle: Int = 0
    var program: Int = 0
    var flag = false
    override fun onDrawFrame(p0: GL10?) {
        glClearColor(1.0f,1.0f,1.0f, 1.0f)
        checkGlError("glClearColor")

        glClear(GL_DEPTH_BUFFER_BIT or GL_COLOR_BUFFER_BIT)
        checkGlError("glClear")

        glUseProgram(program)
        checkGlError("glUseProgram")

        glVertexAttribPointer(vPositionHandle,2,GL_FLOAT, false, 0, triangleVertices)
        checkGlError("glVertexAttribPointer")

        glEnableVertexAttribArray(vPositionHandle)
        checkGlError("glEnableVertexAttribArray")

        glDrawArrays(GL_TRIANGLES, 0, 3)
        glDrawArrays(GL_TRIANGLES, 3, 3)
        checkGlError("glDrawArrays")
    }

    override fun onSurfaceChanged(gl: GL10, width: Int, height: Int) {
        program = createProgram(vertexShader, fragmentShader)

        vPositionHandle = glGetAttribLocation(program, "vPosition")
        checkGlError("glGetAttribLocation")

        glViewport(0, 0, width, height)
        checkGlError("glViewport")
    }

    override fun onSurfaceCreated(gl: GL10, p1: EGLConfig) {
        //nothing to do
    }
}