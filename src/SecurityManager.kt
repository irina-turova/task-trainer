package com.trainer

import org.apache.commons.codec.digest.DigestUtils

object SecurityManager {

    fun getPasswordHash(password: String, salt: String): String? {

        val hashed = DigestUtils.md5Hex(password + salt)
        return hashed
    }

}