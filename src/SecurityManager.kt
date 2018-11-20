package com.trainer

import apache.cayenne.mappings.User
import org.apache.commons.codec.digest.DigestUtils

object SecurityManager {

    fun getPasswordHash(user: User, password: String): String? {

        val salt = user.salt
        val hashed = DigestUtils.md5Hex(password + salt)

        return hashed
    }

}