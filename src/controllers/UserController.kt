package com.trainer.controllers

import apache.cayenne.mappings.Role
import apache.cayenne.mappings.User
import com.trainer.OrmManager
import com.trainer.SecurityManager
import com.trainer.UserData
import com.trainer.utils.initWithJson
import io.ktor.auth.UserIdPrincipal
import io.ktor.auth.UserPasswordCredential
import io.ktor.http.HttpStatusCode
import org.apache.cayenne.Cayenne
import org.apache.cayenne.CayenneRuntimeException
import org.apache.cayenne.query.ObjectSelect
import org.apache.cayenne.access.DataContext



object UserController {

    fun find(credentials: UserPasswordCredential): UserIdPrincipal? {
        val user = ObjectSelect.query(User::class.java).where(User.LOGIN.eq(credentials.name)).selectFirst(OrmManager.newContext())
            ?: return null
        val passwordHash = SecurityManager.getPasswordHash(credentials.password, user.salt)
        return if (passwordHash == user.password)
            UserIdPrincipal(Cayenne.longPKForObject(user).toString())
        else
            null
    }

    fun get(userId: Int): User {
        val user = Cayenne.objectForPK(OrmManager.newContext(), User::class.java, userId)
        return user
    }

    fun store(json: String): Pair<HttpStatusCode, Any> {
        return try {
            val context = OrmManager.newContext()
            val user = context.newObject(User::class.java)
            user.initWithJson(json, context)

            val emailUnique =
                ObjectSelect
                    .query(User::class.java)
                    .where(User.LOGIN.eq(user.login))
                    .select(context).count() == 0

            if (emailUnique) {
                context.commitChanges()
                Pair(HttpStatusCode(200, ""), user)
            } else
                Pair(HttpStatusCode(422, ""), "Пользователь с таким email уже зарегистрирован")

        } catch (e: CayenneRuntimeException) {
            Pair(HttpStatusCode(422, ""), "Полученные данные невалидны")
        } catch (e: Exception) {
            Pair(HttpStatusCode(500, ""), "Произошла ошибка во время регистрации. Попробуйте позже")
        }
    }

}