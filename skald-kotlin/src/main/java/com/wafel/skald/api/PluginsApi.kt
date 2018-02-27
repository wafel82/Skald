package com.wafel.skald.api

interface SkaldAppender {
    fun wtf(message: String)
    fun error(message: String)
    fun warn(message: String)
    fun info(message: String)
    fun debug(message: String)
    fun trace(message: String)
}