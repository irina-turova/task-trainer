package com.trainer.controllers

import apache.cayenne.mappings.Theme
import com.trainer.OrmManager
import org.apache.cayenne.query.ObjectSelect

object ThemeController {

    fun get(): List<Theme> {
        val themes = ObjectSelect.query(Theme::class.java).select(OrmManager.context)
        return themes?.toList() ?: emptyList()
    }

}