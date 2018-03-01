package com.wafel.skald.plugins.file

import com.wafel.skald.api.Saga
import java.io.File

/**
 * File appender configuration
 */
interface FileAppenderConfig {
    /**
     * Overrides the file if it exists already.
     */
    fun withOverride()
}

/**
 * Function adds File appender to this Saga instance.
 * @param file - file to append with data.
 * @param init - function type with FileAppender instance as a receiver. Inside this function
 * you can refer to all appender's public method without 'this.' prefix.
 */
fun Saga.toFile(file: File, init: FileAppenderConfig.()->Unit) {
    this.to(FileAppender(), init)
}