package com.trainer

import org.apache.cayenne.ObjectContext
import org.apache.cayenne.configuration.server.ServerRuntime

object OrmManager {

    val context: ObjectContext
    val runtime: ServerRuntime

    init {
        runtime = ServerRuntime.builder()
            .addConfig("cayenne-project.xml")
            .build()

        context = runtime.newContext()
    }

}