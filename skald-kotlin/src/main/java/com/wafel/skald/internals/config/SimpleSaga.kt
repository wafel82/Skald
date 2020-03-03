package com.wafel.skald.internals.config

import com.wafel.skald.api.LogLevel
import com.wafel.skald.api.Saga
import com.wafel.skald.api.SerializerConfig
import com.wafel.skald.api.SkaldAppender
import com.wafel.skald.internals.patterns.AvailablePatterns

internal class SimpleSaga : Saga() {
    private val appenders = mutableListOf<SkaldAppender>()
    private var logLevel = LogLevel.NONE
    private var path = ""
    private var pattern = "{message}"
    private var enablePredicate: () -> Boolean = { true }
    private var serializers = emptyList<SerializerConfig<*>>()
    private var defaultSerializer: (Any) -> String = { it.toString() }

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

    override fun enableWhen(predicate: () -> Boolean) {
        this.enablePredicate = predicate
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

    override fun getEnabledPredicate() = enablePredicate
}