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

    fun getRandom(userId: Int, subthemeName: String?, difficultyName: String?): Task? {
        val user = UserController.get(userId)
        val userTasksId = user.solutions.map { Cayenne.intPKForObject(it.task) }

        val tasks = ObjectSelect.query(Task::class.java).select(OrmManager.newContext())
        val filtered = tasks.filter { Cayenne.pkForObject(it) !in userTasksId &&
                it.subtheme1.name == subthemeName && it.difficulty.name == difficultyName }
        val task = if (filtered.isNotEmpty()) filtered.random() else null
        return task
    }

    fun get(userId: Int, subthemeName: String?, difficultyName: String?, taskId: String?): Task? {
        val task = Cayenne.objectForPK(OrmManager.newContext(), Task::class.java, taskId?.toIntOrNull() ?: 0)
        val userSolved = task.solutions.any { Cayenne.pkForObject(it.user1) == userId }
        return if (!userSolved && task.subtheme1.name == subthemeName && task.difficulty.name == difficultyName) task else null
    }

    fun getTaskImage(imageType: String?, taskId: String?): Pair<HttpStatusCode, String> {
        return try {
            val ctx = OrmManager.newContext()
            val task = Cayenne.objectForPK(ctx, Task::class.java, taskId?.toIntOrNull() ?: "")
                ?: throw java.lang.Exception("The task with the given id does not exists")

            Pair(HttpStatusCode(200, ""),
                when (imageType) {
                    "task" -> task.image?.name ?: ""
                    "solution" -> task.image1?.name ?: ""
                    else -> ""
                }
            )

        } catch (e: Exception) {
            Pair(HttpStatusCode(500, ""), e.message ?: "")
        }
    }

    fun check(taskId: Int, userId: Int, actualAnswer: String): Boolean {
        val ctx = OrmManager.newContext()
        val solution = ctx.newObject(Solution::class.java)
        solution.actualAnswer = actualAnswer
        solution.task = Cayenne.objectForPK(ctx, Task::class.java, taskId)
        solution.solutionDateTime = LocalDateTime.now()
        solution.user1 = Cayenne.objectForPK(ctx, User::class.java, userId)
        ctx.commitChanges()
        return solution.task.rightAnswer == solution.actualAnswer
                //&&
                //ObjectSelect
                //   .query(Solution::class.java)
                //   .where(Solution.TASK.eq(solution.task))
                //   .selectFirst(OrmManager.context) == null
    }

    fun store(json: String, userId: Int): Pair<HttpStatusCode, Any>  {
        return try {
            val ctx = OrmManager.newContext()
            val task = ctx.newObject(Task::class.java)
            task.initWithJson(json, ctx)

            val user = Cayenne.objectForPK(ctx, User::class.java, userId)
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