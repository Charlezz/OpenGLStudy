package com.charlezz.a02_rectangle

import android.opengl.GLES20
import android.opengl.Matrix
import java.nio.Buffer

class MyTerrain{

    val vertexShader = "" +
            "uniform mat4 mvpMatrix;" +
            "attribute vec4 vPosition;" +
            "void main() {" +
            "  gl_Position = mvpMatrix * vPosition;" +
            "}"

    val fragmentShader = "" +
            "precision mediump float;" +
            "void main() {" +
            "  gl_FragColor = vec4(1.0, 0.0, 0.0, 1.0);" +
            "}"

    val quadVertices: Buffer = GlUtil.createFloatBuffer(arrayOf(
            -0.5f, 0.5f,
            -0.5f, -0.5f,
            0.5f, -0.5f,

            -0.5f, 0.5f,
            0.5f, -0.5f,
            0.5f, 0.5f
    ).toFloatArray())

    var vPositionHandle: Int = 0
    var program: Int = 0
    var mvpMatrixHandle : Int = 0
    var modelMatrix =FloatArray(16)
    var mvpMatrix=FloatArray(16)


    init {
        program = GlUtil.createProgram(vertexShader, fragmentShader)
        vPositionHandle = GLES20.glGetAttribLocation(program, "vPosition")
        mvpMatrixHandle = GLES20.glGetUniformLocation(program, "mvpMatrix")
        Matrix.setRotateEulerM(modelMatrix, 0,-90f, 0f,0f)
        Matrix.scaleM(modelMatrix, 0,1.5f, 1.5f,0f)

    }
    fun draw(vpMatrix:FloatArray){
        GLES20.glUseProgram(program)
        GLES20.glVertexAttribPointer(vPositionHandle, 2, GLES20.GL_FLOAT, false, 0, quadVertices)
        GLES20.glEnableVertexAttribArray(vPositionHandle)

        Matrix.multiplyMM(mvpMatrix, 0, vpMatrix, 0, modelMatrix, 0)

        GLES20.glUniformMatrix4fv(mvpMatrixHandle, 1, false, mvpMatrix, 0)

        GLES20.glDrawArrays(GLES20.GL_TRIANGLES, 0, 6)

        GLES20.glDisableVertexAttribArray(vPositionHandle)
    }
}
