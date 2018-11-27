package com.trainer.controllers

import apache.cayenne.mappings.*
import com.trainer.LoginSession
import com.trainer.OrmManager
import com.trainer.UserData
import io.ktor.application.call
import io.ktor.sessions.get
import io.ktor.sessions.sessions
import org.apache.cayenne.Cayenne
import org.apache.cayenne.query.ObjectSelect
import java.time.LocalDateTime

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

    fun check(taskId: Int, userId: Int, actualAnswer: String): Boolean {
        val solution = OrmManager.context.newObject(Solution::class.java)
        solution.actualAnswer = actualAnswer
        solution.task = Cayenne.objectForPK(OrmManager.context, Task::class.java, taskId)
        solution.solutionDateTime = LocalDateTime.now()
        solution.user1 = Cayenne.objectForPK(OrmManager.context, User::class.java, userId)
        OrmManager.context.commitChanges()
        return solution.task.rightAnswer == solution.actualAnswer
                //&&
                //ObjectSelect
                //   .query(Solution::class.java)
                //   .where(Solution.TASK.eq(solution.task))
                //   .selectFirst(OrmManager.context) == null
    }
}