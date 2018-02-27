package com.wafel.skald.internals.config

import com.wafel.skald.api.Saga
import com.wafel.skald.api.Skald

internal class SimpleSkald : Skald {
    companion object {
        private val sagas = mutableListOf<Saga>()
        fun getConfiguredSagas() = sagas.toList()
    }

    override fun writeSaga(init: Saga.()->Unit) {
        val saga = SimpleSaga()
        saga.init()
        sagas.add(saga)
    }
}