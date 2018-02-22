package com.wafel.skald.demo

import android.app.Application
import com.wafel.skald.api.LogLevel.DEBUG
import com.wafel.skald.api.LogLevel.INFO
import com.wafel.skald.api.skald

class SkaldDemoApplication: Application() {
    override fun onCreate() {
        super.onCreate()

        skald {
            writeSaga {
                toLogcat { withTag { "SKALD-DEMO-PKG" } }
                withLevel { DEBUG }
                withPath { "com.wafel.skald.demo" }
                withPattern { "${it.simplePath} -> ${it.message}" }
            }

            writeSaga {
                toLogcat { withTag { "SKALD-DEMO-CLS" } }
                withLevel { DEBUG }
                withPath { "com.wafel.skald.demo.MainActivity" }
                withPattern { "[ THREAD: ${it.threadName} ] :: ${it.message}" }
            }
        }
    }
}