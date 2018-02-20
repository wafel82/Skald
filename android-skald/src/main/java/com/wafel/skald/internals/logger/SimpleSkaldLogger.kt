package com.wafel.skald.internals.logger

import com.wafel.skald.api.LogLevel
import com.wafel.skald.api.Saga
import com.wafel.skald.api.SkaldLogger
import com.wafel.skald.internals.config.SimpleSkald

internal class SimpleSkaldLogger(path: String) : SkaldLogger {
    private val sagas: List<Saga> = SimpleSkald.getConfiguredSagas().filterApplicableSagas(path)
    private val wtfAppenders    = sagas.toAppendersByLogLevel(LogLevel.WTF)
    private val errorAppenders  = sagas.toAppendersByLogLevel(LogLevel.ERROR)
    private val warnAppenders   = sagas.toAppendersByLogLevel(LogLevel.WARN)
    private val infoAppenders   = sagas.toAppendersByLogLevel(LogLevel.INFO)
    private val debugAppenders  = sagas.toAppendersByLogLevel(LogLevel.DEBUG)
    private val traceAppenders  = sagas.toAppendersByLogLevel(LogLevel.TRACE)

    override fun wtf(message: String)   = wtfAppenders.forEach { it.wtf(message) }

    override fun error(message: String) = errorAppenders.forEach { it.error(message) }

    override fun warn(message: String)  = warnAppenders.forEach { it.warn(message) }

    override fun info(message: String)  = infoAppenders.forEach { it.info(message) }

    override fun debug(message: String) = debugAppenders.forEach { it.debug(message) }

    override fun trace(message: String) = traceAppenders.forEach { it.trace(message) }

    private fun List<Saga>.filterApplicableSagas(path: String) = this
            .filter { path.startsWith(it.getPath()) }

    private fun List<Saga>.toAppendersByLogLevel(logLevel: LogLevel) = this
            .filter { it.getLevel() >= logLevel }
            .map { it.getAppenders() }
            .flatMap { it.toList() }
}