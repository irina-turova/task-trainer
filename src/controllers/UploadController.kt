package com.trainer.controllers

import apache.cayenne.mappings.Image
import com.trainer.OrmManager
import io.ktor.http.HttpStatusCode
import kotlinx.html.InputType
import org.apache.cayenne.Cayenne
import org.apache.cayenne.query.ObjectSelect

data class ImageData(val id: Int, val name: String)

object UploadController {

    fun store(fileName: String): Pair<HttpStatusCode, ImageData?> {
        return try {
            val ctx = OrmManager.runtime.newContext()
            val image = ctx.newObject(Image::class.java)
            image.name = fileName
            image.description = "default"
            ctx.commitChanges()
            Pair(HttpStatusCode(200, ""), ImageData(Cayenne.intPKForObject(image), fileName))
        } catch (e: Exception) {
            Pair(HttpStatusCode(500, ""), null)
        }
    }
}