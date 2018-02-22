package com.wafel.skald.internals.patterns

import com.wafel.skald.api.Saga

internal object AvailablePatterns: Saga.Patterns {
    override val threadName: String     = "{thread}"
    override val message: String        = "{message}"
    override val fullPath: String     = "{full-path}"
    override val simplePath: String     = "{simple-path}"
}

internal val patternHandlers = listOf(
        PatternHandler(AvailablePatterns.message, { _, input -> input }),
        PatternHandler(AvailablePatterns.threadName, { _, _ ->Thread.currentThread().name }),
        PatternHandler(AvailablePatterns.fullPath, { path, _ ->  path }),
        PatternHandler(AvailablePatterns.simplePath, { path, _ ->  path.split(".").last() }))
