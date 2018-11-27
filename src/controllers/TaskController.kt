package com.trainer.controllers

import apache.cayenne.mappings.*
import com.trainer.OrmManager
import com.trainer.utils.initWithJson
import io.ktor.http.HttpStatusCode
import org.apache.cayenne.Cayenne
import org.apache.cayenne.CayenneRuntimeException
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
        val ctx = OrmManager.runtime.newContext()
        val solution = ctx.newObject(Solution::class.java)
        solution.actualAnswer = actualAnswer
        solution.task = Cayenne.objectForPK(ctx, Task::class.java, taskId)
        solution.solutionDateTime = LocalDateTime.now()
        solution.user1 = Cayenne.objectForPK(ctx, User::class.java, userId)
        ctx.commitChanges()
        return solution.task.rightAnswer == solution.actualAnswer
    }

    fun store(json: String, userId: Int): Pair<HttpStatusCode, Any>  {
        return try {
            val ctx = OrmManager.runtime.newContext()
            val task = ctx.newObject(Task::class.java)
            task.initWithJson(json)

            val user = UserController.get(userId)
            user.addToTasks(task)

            ctx.commitChanges()
            Pair(HttpStatusCode(200, ""), task)


        } catch (e: CayenneRuntimeException) {
            Pair(HttpStatusCode(422, ""), "Полученные данные невалидны")
        } catch (e: Exception) {
            Pair(HttpStatusCode(500, ""), "Произошла ошибка во время добавления. Попробуйте позже")
        }
    }
}