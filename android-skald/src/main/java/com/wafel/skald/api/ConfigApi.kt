package com.wafel.skald.api

import android.icu.lang.UCharacter.GraphemeClusterBreak.T
import android.icu.lang.UCharacter.GraphemeClusterBreak.V
import android.util.Patterns
import com.wafel.skald.internals.config.SimpleSkald


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
     * API method for plugins integration. It allows to initialize appender and
     * to add it to this Saga instance.
     * @param appender - instance of appender to be configured and added to Saga
     * @param init - function type with ScaldAppender instance as a receiver. Inside this function
     * you can refer to all appender's public method without 'this.' prefix.
     */
    abstract fun <T: SkaldAppender> to(appender: T, init: T.() -> Unit)



    /**
     * Function configures log level to be used with this Saga instance.
     * @param level - lambda returning one of the @LogLevel enum instance
     */
    abstract fun withLevel(level: () -> LogLevel)

    /**
     * Function configures path to be used with this Saga instance. Provided path will be compared
     * with the one defined when creating logger (@see createLogger methods). When logger path starts
     * with Saga path - then saga will be used by this logger.
     *@param path - lambda returning path to be configured.
     */
    abstract fun withPath(path: () -> String)

    /**
     * Function configures pattern to be used to construct log message
     */
    abstract fun withPattern(pattern: (Patterns) -> String)

    interface Patterns {
    val threadName: String
    val message: String
    val fullPath: String
    val simplePath: String
}

    // internal api methods:
    internal abstract fun getLevel(): LogLevel
    internal abstract fun getPath(): String
    internal abstract fun getPattern(): String
    internal abstract fun getAppenders(): List<SkaldAppender>
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

