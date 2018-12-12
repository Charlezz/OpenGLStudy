package com.charlezz.a01_triangle

import android.opengl.GLES20.*
import android.opengl.GLSurfaceView
import com.charlezz.a01_triangle.GlUtil.checkGlError
import com.charlezz.a01_triangle.GlUtil.createProgram
import java.nio.Buffer
import javax.microedition.khronos.egl.EGLConfig
import javax.microedition.khronos.opengles.GL10

class MyRenderer : GLSurfaceView.Renderer {

    val vertexShader = "" +
            "attribute vec4 vPosition;" +
            "void main() {" +
            "  gl_Position = vPosition;" +
            "}"

    val fragmentShader = "" +
            "precision mediump float;" +
            "void main() {" +
            "  gl_FragColor = vec4(0.0, 1.0, 0.0, 1.0);" +
            "}"

    val triangleVertices: Buffer = GlUtil.createFloatBuffer(arrayOf(
            0.0f, 0.5f,
            -0.5f, -0.5f,
            0.5f, -0.5f
    ).toFloatArray())

    var vPositionHandle: Int = 0
    var program: Int = 0
    var grey = 0f
    var flag = false
    override fun onDrawFrame(p0: GL10?) {
        if( grey > 1.0f || grey < 0.0f){
            flag = !flag
        }
        if(flag){
            grey += 0.01f
        }else{
            grey -= 0.01f
        }

        glClearColor(grey,grey,grey, 1.0f)
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