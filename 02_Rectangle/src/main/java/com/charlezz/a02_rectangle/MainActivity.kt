package com.charlezz.a02_rectangle

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {


    var viewMatrix: FloatArray = FloatArray(16)
    var projectionMatrix: FloatArray = FloatArray(16)
    var modelMatrix=FloatArray(16){
        1f
    }
    var finalMatrix = FloatArray(16)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        gl_surface_view.setEGLContextClientVersion(2)
//        gl_surface_view.setRenderer(QuadRenderer())
//        gl_surface_view.setRenderer(QuadRenderer2())
//        gl_surface_view.setRenderer(CubeRenderer2())
        gl_surface_view.setRenderer(GameRenderer())

//        gl_surface_view.setRenderer(QuadRenderer3().also {
//            gl_surface_view.setOnTouchListener(it)
//        })

//        Matrix.setIdentityM(modelMatrix, 0)
//        Matrix.setLookAtM(viewMatrix, 0,
//                0.0f, 0.0f, -4.0f,
//                0.0f, 0.0f, 0.0f,
//                0.0f, 1f, 0.0f)
//
//
//        Matrix.perspectiveM(projectionMatrix, 0, 60f, 1.7f, 3f,7f)
//
//        Matrix.multiplyMM(finalMatrix, 0, viewMatrix, 0, modelMatrix, 0)
//
//        Matrix.frustumM(projectionMatrix, 0, 30f)
//
////        print("")

    }
}
