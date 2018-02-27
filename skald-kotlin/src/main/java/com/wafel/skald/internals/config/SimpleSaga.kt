package com.wafel.skald.internals.config

import com.wafel.skald.api.LogLevel
import com.wafel.skald.api.Saga
import com.wafel.skald.api.SkaldAppender
import com.wafel.skald.internals.patterns.AvailablePatterns

internal class SimpleSaga : Saga() {
    private val appenders = mutableListOf<SkaldAppender>()
    private var logLevel = LogLevel.NONE
    private var path = ""
    private var pattern = "{message}"


    override fun <T : SkaldAppender> to(appender: T, init: T.() -> Unit) {
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
    override fun getAppenders(): List<SkaldAppender> {
        return appenders
    }
}