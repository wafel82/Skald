package com.wafel.skald.demo

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.wafel.skald.api.createLogger

class MainActivity : AppCompatActivity() {
    private val logger = createLogger(this.javaClass)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        logger.debug("Hello Skald!")
    }
}
