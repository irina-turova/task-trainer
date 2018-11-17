package com.trainer.controllers

import apache.cayenne.mappings.Task
import com.trainer.OrmManager
import org.apache.cayenne.query.ObjectSelect
import org.apache.cayenne.query.Ordering

object TaskController {

    fun getRandom() {
        val tasks = ObjectSelect.query(Task::class.java).select(OrmManager.context)
        val task = if (tasks.isNotEmpty()) tasks.random() else null
        print(task)
    }

}