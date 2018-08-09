package com.wafel.skald.internals.patterns

import com.wafel.skald.api.Saga

object DefaultLogTags: Saga.LogLevelTags {
    override val wtfTag     = "WTF"
    override val errorTag   = "ERROR"
    override val warnTag    = "WARNING"
    override val infoTag    = "INFO"
    override val debugTag   = "DEBUG"
    override val traceTag   = "TRACE"
}