package com.trainer.utils

import apache.cayenne.mappings.Subtheme
import apache.cayenne.mappings.Theme
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.trainer.OrmManager
import org.apache.cayenne.query.ObjectSelect

fun Subtheme.initWithJson(json: String) {
    val jsonAsMap: Map<String, String> = Gson().fromJson(json, object : TypeToken<Map<String, String>>() {}.type)

    this.name = jsonAsMap["alias"]
    this.description = jsonAsMap["name"]
    this.theme = ObjectSelect.query(Theme::class.java).where(Theme.NAME.eq(jsonAsMap["theme"])).selectFirst(OrmManager.context)
}