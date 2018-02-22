package com.wafel.skald.internals.config

import com.wafel.skald.api.LogLevel
import com.wafel.skald.api.LogcatAppender
import com.wafel.skald.api.Saga
import com.wafel.skald.internals.patterns.AvailablePatterns

internal class SimpleSaga : Saga() {
    private val appenders = mutableListOf<ScaldAppender>()
    private var logLevel = LogLevel.NONE
    private var path = ""
    private var pattern = "{message}"

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

    override fun withPattern(pattern: (Patterns) -> String) {
        this.pattern = pattern(AvailablePatterns)
    }

    override fun getLevel(): LogLevel {
        return logLevel
    }

    override fun getPath(): String {
        return path
    }

    override fun getPattern(): String {
        return pattern
    }
    override fun getAppenders(): List<ScaldAppender> {
        return appenders
    }
}