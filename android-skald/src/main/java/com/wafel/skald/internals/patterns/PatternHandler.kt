package com.wafel.skald.internals.patterns

internal class PatternHandler(private val tag: String, private val value: (path: String, input: String)->String) {
    fun handle(sagaPattern: String, loggerPath: String, input: String) =
            sagaPattern.replace(tag, value(loggerPath, input))
}