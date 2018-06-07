package com.wafel.skald.api

import com.wafel.skald.internals.logger.SimpleSkaldLogger

/**
 * Logger factory method
 * @param path - path to be used by constructed logger. It will be compared with configured
 * Sagas' paths. When logger path starts with Saga path then such Saga will be used to log messages
 * with this logger.
 */
fun createLogger(path: String): SkaldLogger = SimpleSkaldLogger(path)

/**
* Logger factory method
* @param path - class to be used to construct logger path - class canonical name will be used.
* Constructed path will be compared with configured Sagas' paths. When logger path starts with
* Saga path then such Saga will be used to log messages with this logger.
*/
fun createLogger(clazz: Class<*>): SkaldLogger = SimpleSkaldLogger(clazz.canonicalName)

/**
 * Skald logger interface. Use its approproate methods in logging entry  points
 */
interface SkaldLogger {
    fun wtf(message: Any)
    fun error(message: Any)
    fun warn(message: Any)
    fun info(message: Any)
    fun debug(message: Any)
    fun trace(message: Any)
}