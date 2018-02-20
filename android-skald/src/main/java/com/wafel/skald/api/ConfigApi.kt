package com.wafel.skald.api

import com.wafel.skald.internals.config.SimpleSkald
import com.wafel.skald.internals.config.ScaldAppender


enum class LogLevel { NONE, WTF, ERROR, WARN, INFO, DEBUG, TRACE }

fun skald(init: Skald.()->Unit): Skald {
    val scaldConfiguration = SimpleSkald()
    scaldConfiguration.init()
    return scaldConfiguration
}

interface Skald {
    fun writeSaga(init: Saga.()->Unit)
}

abstract class Saga {
    abstract fun toLogcat(init: LogcatAppender.() -> Unit)
    abstract fun withLevel(level: ()-> LogLevel)
    abstract fun withPath(path: ()->String)
    internal abstract fun getLevel(): LogLevel
    internal abstract fun getPath(): String
    internal abstract fun getAppenders(): List<ScaldAppender>
}

interface LogcatAppender {
    fun withTag(tag: ()->String)
}

