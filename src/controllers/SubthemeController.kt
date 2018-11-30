package controllers

import apache.cayenne.mappings.Subtheme
import apache.cayenne.mappings.Theme
import com.trainer.OrmManager
import com.trainer.utils.initWithJson
import io.ktor.http.HttpStatusCode
import org.apache.cayenne.CayenneRuntimeException
import org.apache.cayenne.query.ObjectSelect

object SubthemeController {
    fun get(themeName: String?): List<Subtheme> {
        val subthemes = ObjectSelect.query(Subtheme::class.java).where(Subtheme.THEME.dot(Theme.NAME).eq(themeName)).select(OrmManager.newContext())
        return subthemes
    }

    fun store(json: String): Pair<HttpStatusCode, Any>  {
        return try {
            val ctx = OrmManager.runtime.newContext()
            val subtheme = ctx.newObject(Subtheme::class.java)
            subtheme.initWithJson(json, ctx)

            val nameUnique = ObjectSelect.query(Subtheme::class.java).where(Subtheme.NAME.eq(subtheme.name))
                .selectFirst(ctx) == null
            val descriptionUnique = ObjectSelect.query(Subtheme::class.java).where(Subtheme.DESCRIPTION.eq(subtheme.description))
                .selectFirst(ctx) == null

            if (!descriptionUnique)
                Pair(HttpStatusCode(422, ""), "Подтема с таким названием уже существует")
            else if (!nameUnique)
                Pair(HttpStatusCode(422, ""), "Подтема с таким служебным названием уже существует")
            else {
                ctx.commitChanges()
                Pair(HttpStatusCode(200, ""), subtheme)
            }

        } catch (e: CayenneRuntimeException) {
            Pair(HttpStatusCode(422, ""), "Полученные данные невалидны")
        } catch (e: Exception) {
            Pair(HttpStatusCode(500, ""), "Произошла ошибка во время добавления. Попробуйте позже")
        }
    }
}
