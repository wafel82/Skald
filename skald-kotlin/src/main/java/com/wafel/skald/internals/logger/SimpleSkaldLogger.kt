package com.wafel.skald.internals.logger

import com.wafel.skald.api.*
import com.wafel.skald.api.LogLevel.*
import com.wafel.skald.internals.config.SimpleSkald
import com.wafel.skald.internals.patterns.PatternHandler

internal class SimpleSkaldLogger(private val loggerPath: String) : SkaldLogger {
    private val sagas: List<Saga> = SimpleSkald.getConfiguredSagas().filterApplicableSagas(loggerPath)
    private val wtfLogEntryConfigs      = sagas.toAppendersByLogLevel(WTF)
    private val errorLogEntryConfigs    = sagas.toAppendersByLogLevel(ERROR)
    private val warnLogEntryConfigs     = sagas.toAppendersByLogLevel(WARN)
    private val infoLogEntryConfigs     = sagas.toAppendersByLogLevel(INFO)
    private val debugLogEntryConfigs    = sagas.toAppendersByLogLevel(DEBUG)
    private val traceLogEntryConfigs    = sagas.toAppendersByLogLevel(TRACE)

    override fun wtf(message: Any) = wtfLogEntryConfigs.forEach { evaluateLogEntry(WTF, it.sagaPattern, it.patternHandlers, message, it.serializers, it.defaultSerializer).let { entry -> it.appenders.forEach { it.wtf(entry) } } }

    override fun error(message: Any) = errorLogEntryConfigs.forEach { evaluateLogEntry(ERROR, it.sagaPattern, it.patternHandlers, message, it.serializers, it.defaultSerializer).let { entry -> it.appenders.forEach { it.error(entry) } } }

    override fun warn(message: Any) = warnLogEntryConfigs.forEach { evaluateLogEntry(WARN, it.sagaPattern, it.patternHandlers, message, it.serializers, it.defaultSerializer).let { entry -> it.appenders.forEach { it.warn(entry) } } }

    override fun info(message: Any) = infoLogEntryConfigs.forEach { evaluateLogEntry(INFO, it.sagaPattern, it.patternHandlers, message, it.serializers, it.defaultSerializer).let { entry -> it.appenders.forEach { it.info(entry) } } }

    override fun debug(message: Any) = debugLogEntryConfigs.forEach { evaluateLogEntry(DEBUG, it.sagaPattern, it.patternHandlers, message, it.serializers, it.defaultSerializer).let { entry -> it.appenders.forEach { it.debug(entry) } } }

    override fun trace(message: Any) = traceLogEntryConfigs.forEach { evaluateLogEntry(TRACE, it.sagaPattern, it.patternHandlers, message, it.serializers, it.defaultSerializer).let { entry -> it.appenders.forEach { it.trace(entry) } } }

    private fun List<Saga>.filterApplicableSagas(path: String) = this
            .filter { path.startsWith(it.getPath()) }

    private fun List<Saga>.toAppendersByLogLevel(logLevel: LogLevel) = this
            .filter { it.getLevel() >= logLevel }
            .map { saga -> LogEntryConfig(saga.getPattern(), saga.getPatternHandlers(), saga.getAppenders(), saga.getSerializers(), saga.getDefaultSerializer()) }

    private fun evaluateLogEntry(loglevel: LogLevel, sagaPattern: String, patternHandlers: List<PatternHandler>, message: Any, serializers: List<SerializerConfig<*>>, defaultSerializer: (Any) -> String ): String {
        val serializedMessage = serialize(message, serializers, defaultSerializer)
        var logEntry: String = sagaPattern
        patternHandlers.forEach { logEntry = it.handle(logEntry,  PatternHandler.PatternHandlerData(loggerPath, serializedMessage, loglevel)) }
        return logEntry
    }

    @Suppress("UNCHECKED_CAST")
    private fun serialize(message: Any, serializers: List<SerializerConfig<*>>, defaultSerializer: (Any) -> String): String =
            serializers.find { it.typeToken == message::class.java }?.let {
                (it.serializer as (Any) -> String)(message)
            } ?: defaultSerializer(message)

    private data class LogEntryConfig(val sagaPattern: String,
                                      val patternHandlers: List<PatternHandler>,
                                      val appenders: List<SkaldAppender>,
                                      val serializers: List<SerializerConfig<*>>,
                                      val defaultSerializer: (Any) -> String)


}