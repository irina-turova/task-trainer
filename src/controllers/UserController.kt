package com.trainer.controllers

import apache.cayenne.mappings.User
import com.trainer.OrmManager
import com.trainer.SecurityManager
import io.ktor.auth.UserIdPrincipal
import io.ktor.auth.UserPasswordCredential
import org.apache.cayenne.Cayenne
import org.apache.cayenne.query.ObjectSelect

object UserController {

    fun find(credentials: UserPasswordCredential): UserIdPrincipal? {
        val user = ObjectSelect.query(User::class.java).where(User.LOGIN.eq(credentials.name)).selectFirst(OrmManager.context)
            ?: return null
        val passwordHash = SecurityManager.getPasswordHash(user, credentials.password)
        return if (passwordHash == user.password)
            UserIdPrincipal(Cayenne.longPKForObject(user).toString())
        else
            null
    }

}