package com.wafel.skald.internals.config

import com.wafel.skald.api.LogLevel
import com.wafel.skald.api.LogLevel.*
import com.wafel.skald.api.Saga
import com.wafel.skald.api.SerializerConfig
import com.wafel.skald.api.SkaldAppender
import com.wafel.skald.internals.patterns.AvailablePatterns
import com.wafel.skald.internals.patterns.DefaultLogTags
import com.wafel.skald.internals.patterns.PatternHandler
import java.text.SimpleDateFormat
import java.util.*

internal class SimpleSaga : Saga() {
    private val appenders = mutableListOf<SkaldAppender>()
    private var logLevel = NONE
    private var path = ""
    private var pattern = "{message}"
    private var serializers = emptyList<SerializerConfig<*>>()
    private var defaultSerializer: (Any) -> String = { it.toString() }
    private var timestampFormatter: SimpleDateFormat = SimpleDateFormat("HH:mm:ss.SSS")
    private var logLevelTags: LogLevelTags = DefaultLogTags
    private val patternHandlers = listOf(
            PatternHandler(AvailablePatterns.message, { data -> data.userInput }),
            PatternHandler(AvailablePatterns.threadName, { _ ->Thread.currentThread().name }),
            PatternHandler(AvailablePatterns.fullPath, { data ->  data.sagaPath }),
            PatternHandler(AvailablePatterns.simplePath, { data->  data.sagaPath.split(".").last() }),
            PatternHandler(AvailablePatterns.timestamp, {_ -> timestampFormatter.format(Date())}),
            PatternHandler(AvailablePatterns.levelTag, {data -> when(data.logLevel) {
                WTF -> logLevelTags.wtfTag
                ERROR -> logLevelTags.errorTag
                WARN -> logLevelTags.warnTag
                INFO -> logLevelTags.infoTag
                DEBUG -> logLevelTags.debugTag
                TRACE -> logLevelTags.traceTag
                else -> ""
            }
            })
            )

    override fun <T : SkaldAppender> to(appender: T, init: T.() -> Unit) {
        appender.init()
        appenders.add(appender)
    }

    override fun withLevel(level: () -> LogLevel) {
        this.logLevel = level()
    }

    override fun withPath(path: () -> String) {
        this.path = path()
    }

    override fun withPattern(pattern: (Patterns) -> String) {
        this.pattern = pattern(AvailablePatterns)
    }

    override fun withTimestampFormat(timestampFormat: () -> String) {
        this.timestampFormatter = SimpleDateFormat(timestampFormat())
    }

    override fun getLevel(): LogLevel {
        return logLevel
    }

    override fun getPath(): String {
        return path
    }

    override fun getPattern(): String {
        return pattern
    }

    override fun getAppenders(): List<SkaldAppender> {
        return appenders
    }

    override fun withSerializers(vararg serializers: SerializerConfig<*>) {
        this.serializers = serializers.asList()
    }

    override fun getSerializers(): List<SerializerConfig<*>> = serializers

    override fun getDefaultSerializer(): (Any) -> String = defaultSerializer

    override fun getPatternHandlers(): List<PatternHandler> = patternHandlers
}