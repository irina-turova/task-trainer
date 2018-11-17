package com.trainer

import org.apache.cayenne.ObjectContext
import org.apache.cayenne.configuration.server.ServerRuntime

object OrmManager {

    val context: ObjectContext?

    init {
        val cayenneRuntime = ServerRuntime.builder()
            .addConfig("cayenne-project.xml")
            .build()

        context = cayenneRuntime.newContext()
    }

}