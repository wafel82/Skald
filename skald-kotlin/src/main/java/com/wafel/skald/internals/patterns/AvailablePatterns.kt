package com.wafel.skald.internals.patterns

import com.wafel.skald.api.Saga

internal object AvailablePatterns: Saga.Patterns {
    override val threadName: String     = "{thread}"
    override val message: String        = "{message}"
    override val fullPath: String       = "{full-path}"
    override val simplePath: String     = "{simple-path}"
    override val timestamp: String      = "{timestamp}"
    override val levelTag: String       = "{level-tag}"
}