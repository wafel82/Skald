package com.wafel.skald.internals.config

internal interface ScaldAppender {
    fun wtf(message: String)
    fun error(message: String)
    fun warn(message: String)
    fun info(message: String)
    fun debug(message: String)
    fun trace(message: String)
}