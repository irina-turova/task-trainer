package com.trainer.routers

import com.trainer.LoginSession
import com.trainer.controllers.UploadController
import io.ktor.application.call
import io.ktor.http.HttpStatusCode
import io.ktor.http.content.PartData
import io.ktor.http.content.forEachPart
import io.ktor.http.content.streamProvider
import io.ktor.request.receiveMultipart
import io.ktor.response.respond
import io.ktor.routing.Route
import io.ktor.routing.post
import io.ktor.sessions.get
import io.ktor.sessions.sessions
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.coroutines.yield
import java.io.File
import java.io.InputStream
import java.io.OutputStream

fun Route.upload() {

    val uploadDir = "client/public/userdata"

    post("uploads") {
        val session = call.sessions.get<LoginSession>()
        if (session == null) {
            call.respond(HttpStatusCode.Forbidden.description("Not logged in"))
            return@post
        }

        val multipart = call.receiveMultipart()
        var title = ""
        var imageFile: File? = null

        // Processes each part of the multipart input content of the user
        multipart.forEachPart { part ->
            if (part is PartData.FormItem) {
                if (part.name == "image") {
                    title = part.value
                }
            } else if (part is PartData.FileItem) {
                val ext = File(part.originalFileName).extension
                val file = File(
                    uploadDir,
                    "upload-${System.currentTimeMillis()}-${session.id.hashCode()}-${title.hashCode()}.$ext"
                ).apply {
                    createNewFile()
                }

                part.streamProvider().use { its -> file.outputStream().buffered().use { its.copyToSuspend(it) } }
                imageFile = file
            }

            part.dispose()
        }

        val result = UploadController.store(imageFile?.name ?: "")

        println(result)
        call.respond(result.first, result.second ?: "")
    }
}

suspend fun InputStream.copyToSuspend(
    out: OutputStream,
    bufferSize: Int = DEFAULT_BUFFER_SIZE,
    yieldSize: Int = 4 * 1024 * 1024,
    dispatcher: CoroutineDispatcher = Dispatchers.IO
): Long {
    return withContext(dispatcher) {
        val buffer = ByteArray(bufferSize)
        var bytesCopied = 0L
        var bytesAfterYield = 0L
        while (true) {
            val bytes = read(buffer).takeIf { it >= 0 } ?: break
            out.write(buffer, 0, bytes)
            if (bytesAfterYield >= yieldSize) {
                yield()
                bytesAfterYield %= yieldSize
            }
            bytesCopied += bytes
            bytesAfterYield += bytes
        }
        return@withContext bytesCopied
    }
}