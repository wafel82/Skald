package com.wafel.skald.plugins.logcat

import com.wafel.skald.api.Saga

/**
 * Logcat appender configuration
 */
interface LogcatAppenderConfig {
    /**
     * Function configures tag to be used when logging to logcat.
     * @param tag - lambda returning tag string to be used.
     */
    fun withTag(tag: ()->String)
}

/**
 * Function adds Logcat appender to this Saga instance.
 * @param init - function type with LogcatAppender instance as a receiver. Inside this function
 * you can refer to all LogcatAppender's public method without 'this.' prefix.
 */
fun Saga.toLogcat(init: LogcatAppenderConfig.()->Unit) {
    this.to(LogcatAppender(), init)
}