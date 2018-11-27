package com.trainer.utils

import apache.cayenne.mappings.Theme
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

fun Theme.initWithJson(json: String) {
    val jsonAsMap: Map<String, String> = Gson().fromJson(json, object : TypeToken<Map<String, String>>() {}.type)

    this.name = jsonAsMap["alias"]
    this.description = jsonAsMap["name"]
}