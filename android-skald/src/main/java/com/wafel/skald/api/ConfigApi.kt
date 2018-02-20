package com.wafel.skald.api

import com.wafel.skald.internals.config.SimpleSkald
import com.wafel.skald.internals.config.ScaldAppender

/**
 * Logging levels to be used when configuring skald
 */
enum class LogLevel { NONE, WTF, ERROR, WARN, INFO, DEBUG, TRACE }


/**
 * Main function you begin Skald configuration with
 * @param init - function type with Skald instance as a receiver. Inside this function you can
 * refer to all Skald's public method without 'this.' prefix. Use this function to add sagas
 * to Skald configuration.
 * @return - configured skald instance
 */
fun skald(init: Skald.()->Unit): Skald {
    val scaldConfiguration = SimpleSkald()
    scaldConfiguration.init()
    return scaldConfiguration
}

/**
 * Main framework class holding its configuration
 */
interface Skald {
    /**
     * Method to be used to add new Saga configuration.
     * @param init - function type with Saga instance as a receiver. Inside this function you can
     * refer to all Saga's public method without 'this.' prefix. Use this function to configure
     * newly added saga instance.
     */
    fun writeSaga(init: Saga.()->Unit)
}

/**
 * Class defining saga configuration capabilities
 */
abstract class Saga {
    /**
     * Function adds Logcat appender to this Saga instance.
     * @param init - function type with LogcatAppender instance as a receiver. Inside this function
     * you can refer to all LogcatAppender's public method without 'this.' prefix.
     */
    abstract fun toLogcat(init: LogcatAppender.() -> Unit)

    /**
     * Function configures log level to be used with this Saga instance.
     * @param level - lambda returning one of the @LogLevel enum instance
     */
    abstract fun withLevel(level: ()-> LogLevel)

    /**
     * Function configures path to be used with this Saga instance. Provided path will be compared
     * with the one defined when creating logger (@see createLogger methods). When logger path starts
     * with Saga path - then saga will be used by this logger.
     *@param path - lambda returning path to be configured.
     */
    abstract fun withPath(path: ()->String)
    internal abstract fun getLevel(): LogLevel
    internal abstract fun getPath(): String
    internal abstract fun getAppenders(): List<ScaldAppender>
}

/**
 * Logcat appender configuration
 */
interface LogcatAppender {
    /**
     * Function configures tag to be used when logging to logcat.
     * @param tag - lambda returning tag string to be used.
     */
    fun withTag(tag: ()->String)
}

