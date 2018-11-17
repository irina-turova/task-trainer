package controllers

import apache.cayenne.mappings.Subtheme
import apache.cayenne.mappings.Theme
import com.trainer.OrmManager
import org.apache.cayenne.query.ObjectSelect

object SubthemeController {
    fun get(themeName: String?): List<Subtheme> {
        val subthemes = ObjectSelect.query(Subtheme::class.java).where(Subtheme.THEME.dot(Theme.NAME).eq(themeName)).select(OrmManager.context)
        return subthemes
    }

}
