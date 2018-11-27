package com.trainer.utils

import apache.cayenne.mappings.Difficulty
import apache.cayenne.mappings.Image
import apache.cayenne.mappings.Subtheme
import apache.cayenne.mappings.Task
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.trainer.OrmManager
import org.apache.cayenne.Cayenne
import org.apache.cayenne.ObjectContext

fun Task.initWithJson(json: String, ctx: ObjectContext) {
    val jsonAsMap: Map<String, String> = Gson().fromJson(json, object : TypeToken<Map<String, String>>() {}.type)

    this.name = jsonAsMap["name"]
    this.text = jsonAsMap["text"]
    this.rightAnswer = jsonAsMap["rightAnswer"]
    this.explanation = jsonAsMap["explanation"]

    this.subtheme1 = Cayenne.objectForPK(ctx, Subtheme::class.java, jsonAsMap["subtheme"]?.toIntOrNull() ?: 0)
    this.difficulty = Cayenne.objectForPK(ctx, Difficulty::class.java, jsonAsMap["difficulty"]?.toIntOrNull() ?: 0)
    this.image1 = Cayenne.objectForPK(ctx, Image::class.java, jsonAsMap["taskImage"]?.toIntOrNull() ?: 0)
    this.image = Cayenne.objectForPK(ctx, Image::class.java, jsonAsMap["solutionImage"]?.toIntOrNull() ?: 0)
}