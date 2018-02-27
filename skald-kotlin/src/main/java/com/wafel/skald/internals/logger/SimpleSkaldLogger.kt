package com.wafel.skald.internals.logger

import com.wafel.skald.api.LogLevel
import com.wafel.skald.api.Saga
import com.wafel.skald.api.SkaldAppender
import com.wafel.skald.api.SkaldLogger
import com.wafel.skald.internals.config.SimpleSkald
import com.wafel.skald.internals.patterns.patternHandlers

internal class SimpleSkaldLogger(private val loggerPath: String) : SkaldLogger {
    private val sagas: List<Saga> = SimpleSkald.getConfiguredSagas().filterApplicableSagas(loggerPath)
    private val wtfLogEntryConfigs      = sagas.toAppendersByLogLevel(LogLevel.WTF)
    private val errorLogEntryConfigs    = sagas.toAppendersByLogLevel(LogLevel.ERROR)
    private val warnLogEntryConfigs     = sagas.toAppendersByLogLevel(LogLevel.WARN)
    private val infoLogEntryConfigs     = sagas.toAppendersByLogLevel(LogLevel.INFO)
    private val debugLogEntryConfigs    = sagas.toAppendersByLogLevel(LogLevel.DEBUG)
    private val traceLogEntryConfigs    = sagas.toAppendersByLogLevel(LogLevel.TRACE)

    override fun wtf(message: String)   = wtfLogEntryConfigs.forEach { (sagaPattern, appenders) -> evaluateLogEntry(sagaPattern, message).let { entry -> appenders.forEach { it.wtf(entry) } } }

    override fun error(message: String) = errorLogEntryConfigs.forEach { (sagaPattern, appenders) -> evaluateLogEntry(sagaPattern, message).let { entry -> appenders.forEach { it.error(entry) } } }

    override fun warn(message: String)  = warnLogEntryConfigs.forEach { (sagaPattern, appenders) -> evaluateLogEntry(sagaPattern, message).let { entry -> appenders.forEach { it.warn(entry) } } }

    override fun info(message: String)  = infoLogEntryConfigs.forEach { (sagaPattern, appenders) -> evaluateLogEntry(sagaPattern, message).let { entry -> appenders.forEach { it.info(entry) } } }

    override fun debug(message: String) = debugLogEntryConfigs.forEach { (sagaPattern, appenders) -> evaluateLogEntry(sagaPattern, message).let { entry -> appenders.forEach { it.debug(entry) } } }

    override fun trace(message: String) = traceLogEntryConfigs.forEach { (sagaPattern, appenders) -> evaluateLogEntry(sagaPattern, message).let { entry -> appenders.forEach { it.trace(entry) } } }

    private fun List<Saga>.filterApplicableSagas(path: String) = this
            .filter { path.startsWith(it.getPath()) }

    private fun List<Saga>.toAppendersByLogLevel(logLevel: LogLevel) = this
            .filter { it.getLevel() >= logLevel }
            .map { saga -> LogEntryConfig(saga.getPattern(), saga.getAppenders())  }


    private fun evaluateLogEntry(sagaPattern: String,  message: String): String {
        var logEntry: String = sagaPattern
        patternHandlers.forEach { logEntry = it.handle(logEntry, loggerPath, message) }
        return logEntry
    }

    private data class LogEntryConfig(val sagaPattern: String, val appenders: List<SkaldAppender>)

}