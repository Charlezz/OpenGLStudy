package com.charlezz.a02_rectangle

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        gl_surface_view.setEGLContextClientVersion(2)
//        gl_surface_view.setRenderer(QuadRenderer())
        gl_surface_view.setRenderer(QuadRenderer2())
    }
}
