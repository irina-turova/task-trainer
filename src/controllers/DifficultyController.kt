package controllers

import apache.cayenne.mappings.Difficulty
import apache.cayenne.mappings.Subtheme
import apache.cayenne.mappings.Task
import com.trainer.OrmManager
import org.apache.cayenne.query.ObjectSelect

object DifficultyController {
    fun get(subtheme: String?): List<Difficulty> {
        val difficulties = ObjectSelect.query(Difficulty::class.java).where(Difficulty.TASKS.dot(Task.SUBTHEME1).dot(Subtheme.NAME).eq(subtheme)).select(OrmManager.newContext())
        return difficulties
    }

    fun get(): List<Difficulty> {
        val difficulties = ObjectSelect.query(Difficulty::class.java).select(OrmManager.newContext())
        return difficulties
    }

}
