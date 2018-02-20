package com.wafel.skald.internals.config

import com.wafel.skald.api.LogLevel
import com.wafel.skald.api.LogcatAppender
import com.wafel.skald.api.Saga

internal class SimpleSaga : Saga() {
    private val appenders = mutableListOf<ScaldAppender>()
    private var logLevel = LogLevel.NONE
    private var path = ""

    override fun toLogcat(init: LogcatAppender.() -> Unit) {
        val appender = SimpleLogcatAppender()
        appender.init()
        appenders.add(appender)
    }

    override fun withLevel(level: ()-> LogLevel) {
        this.logLevel = level()
    }

    override fun withPath(path: ()->String) {
        this.path = path()
    }

    override fun getLevel(): LogLevel {
        return logLevel
    }

    override fun getPath(): String {
        return path
    }

    override fun getAppenders(): List<ScaldAppender> {
        return appenders
    }
}