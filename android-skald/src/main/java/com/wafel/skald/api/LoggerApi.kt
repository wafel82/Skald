package com.wafel.skald.api

import com.wafel.skald.internals.logger.SimpleSkaldLogger


fun createLogger(path: String): SkaldLogger = SimpleSkaldLogger(path)

fun createLogger(clazz: Class<*>): SkaldLogger = SimpleSkaldLogger(clazz.canonicalName)

interface SkaldLogger {
    fun wtf(message: String)
    fun error(message: String)
    fun warn(message: String)
    fun info(message: String)
    fun debug(message: String)
    fun trace(message: String)
}