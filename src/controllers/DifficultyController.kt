package controllers

import apache.cayenne.mappings.Difficulty
import apache.cayenne.mappings.Subtheme
import apache.cayenne.mappings.Task
import com.trainer.OrmManager
import org.apache.cayenne.query.ObjectSelect
import javax.management.Query.eq

object DifficultyController {
    fun get(subtheme: String?): List<Difficulty> {
        //val difficulties = ObjectSelect.query(Difficulty::class.java).where(Difficulty.TASKS.dot(Task.SUBTHEME).dot(Subtheme.NAME).eq(subtheme)).select(OrmManager.context)
        //return difficulties ?: emptyList()
        return emptyList()
    }

}
