package com.wafel.skald.internals.patterns

import com.wafel.skald.api.LogLevel

internal class PatternHandler(private val tag: String, private val value: (PatternHandlerData)->String) {
    fun handle(sagaPattern: String, patterHandlerData: PatternHandlerData) =
            sagaPattern.replace(tag, value(patterHandlerData))

    data class PatternHandlerData(val sagaPath: String,
                                  val userInput: String,
                                  val logLevel: LogLevel)
}