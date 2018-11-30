package com.trainer

import org.apache.cayenne.ObjectContext
import org.apache.cayenne.configuration.server.ServerRuntime

object OrmManager {

    val runtime: ServerRuntime = ServerRuntime.builder()
        .addConfig("cayenne-project.xml")
        .build()

    fun newContext(): ObjectContext = runtime.newContext()

}