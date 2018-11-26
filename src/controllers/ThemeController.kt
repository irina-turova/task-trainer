package com.trainer.controllers

import apache.cayenne.mappings.Theme
import com.trainer.OrmManager
import com.trainer.utils.initWithJson
import io.ktor.http.HttpStatusCode
import org.apache.cayenne.CayenneRuntimeException
import org.apache.cayenne.query.ObjectSelect

object ThemeController {

    fun get(): List<Theme> {
        val themes = ObjectSelect.query(Theme::class.java).select(OrmManager.context)
        return themes?.toList() ?: emptyList()
    }

    fun store(json: String): Pair<HttpStatusCode, Any>  {
        return try {
            val theme = OrmManager.context.newObject(Theme::class.java)
            theme.initWithJson(json)

            val nameUnique = ObjectSelect.query(Theme::class.java).where(Theme.NAME.eq(theme.name))
                .selectFirst(OrmManager.context) == null
            val descriptionUnique = ObjectSelect.query(Theme::class.java).where(Theme.DESCRIPTION.eq(theme.description))
                .selectFirst(OrmManager.context) == null

            if (!descriptionUnique)
                Pair(HttpStatusCode(422, ""), "Тема с таким названием уже существует")
            else if (!nameUnique)
                Pair(HttpStatusCode(422, ""), "Тема с таким служебным названием уже существует")
            else {
                OrmManager.context.commitChanges()
                Pair(HttpStatusCode(200, ""), theme)
            }

        } catch (e: CayenneRuntimeException) {
            Pair(HttpStatusCode(422, ""), "Полученные данные невалидны")
        } catch (e: Exception) {
            Pair(HttpStatusCode(500, ""), "Произошла ошибка во время добавления. Попробуйте позже")
        }
    }
}