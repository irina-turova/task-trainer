package com.trainer.controllers

import apache.cayenne.mappings.Difficulty
import apache.cayenne.mappings.Subtheme
import apache.cayenne.mappings.Task
import com.trainer.OrmManager
import org.apache.cayenne.Cayenne
import org.apache.cayenne.query.ObjectSelect

object TaskController {

    fun getRandom(): Task? {
        val tasks = ObjectSelect.query(Task::class.java).select(OrmManager.context)
        val task = if (tasks.isNotEmpty()) tasks.random() else null
        return task
    }

    fun get(subthemeName: String?, difficultyName: String?, taskId: String?): Task? {
        val task = Cayenne.objectForPK(OrmManager.context, Task::class.java, taskId?.toIntOrNull() ?: 0)
        return if (task.subtheme1.name == subthemeName && task.difficulty.name == difficultyName) task else null
    }

}