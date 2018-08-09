package com.wafel.skald.demo

import android.app.Application
import com.wafel.skald.api.LogLevel.DEBUG
import com.wafel.skald.api.LogLevel.TRACE
import com.wafel.skald.api.serializeTo
import com.wafel.skald.api.skald
import com.wafel.skald.plugins.logcat.toLogcat

class SkaldDemoApplication : Application() {

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

            writeSaga {
                toLogcat { withTag { "SKALD-DEMO-SERIALIZE" } }
                withLevel { TRACE }
                withPath { "com.wafel.skald.demo" }
                withSerializers(
                        Integer::class.java serializeTo { "{CUSTOM INTEGER: $it}" },
                        MyCustomClass::class.java serializeTo { "{CUSTOM CLASS: ${it.message} -> ${it.someValue}}" }
                )
            }

            writeSaga {
                toLogcat {
                    withTag { "SKALD-DEMO-TIMESTAMP" }
                    withLevel { TRACE }
                    withTimestampFormat { "YYY-MM-dd HH:mm:ss.SSS" }
                    withPattern { "${it.timestamp} - ${it.message}" }
                }
            }

            writeSaga {
                toLogcat {
                    withTag { "SKALD-DEMO-LOG-TAG" }
                    withLevel { TRACE }
                    withTimestampFormat { "YYY-MM-dd HH:mm:ss.SSS" }
                    withPattern { "${it.levelTag} - ${it.message}" }
                }
            }
        }
    }
}