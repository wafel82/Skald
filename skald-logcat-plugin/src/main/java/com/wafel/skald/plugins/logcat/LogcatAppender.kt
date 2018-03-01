package com.wafel.skald.plugins.logcat

import android.util.Log
import com.wafel.skald.api.SkaldAppender

/**
 * Main Logcat Appender implementation class. It writes all log messages to logcat
 */
class LogcatAppender : LogcatAppenderConfig, SkaldAppender {
    private var tag = "SKALD"
    override fun withTag(tag: ()->String) {
        this.tag = tag()
    }

    override fun wtf(message: String) {
        Log.wtf(tag, message)
    }

    override fun error(message: String) {
        Log.e(tag, message)
    }

    override fun warn(message: String) {
        Log.w(tag, message)
    }

    override fun info(message: String) {
        Log.i(tag, message)
    }

    override fun debug(message: String) {
        Log.d(tag, message)
    }

    override fun trace(message: String) {
        Log.v(tag, message)
    }
}
