package com.trainer.utils

import apache.cayenne.mappings.Role
import apache.cayenne.mappings.User
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.trainer.OrmManager
import com.trainer.SecurityManager
import org.apache.cayenne.Cayenne
import org.apache.commons.codec.digest.DigestUtils
import java.util.UUID

fun User.initWithJson(json: String) {
    val jsonAsMap: Map<String, String> = Gson().fromJson(json, object : TypeToken<Map<String, String>>() {}.type)

    this.lastName = jsonAsMap["lastName"]
    this.firstName = jsonAsMap["firstName"]
    this.middleName = jsonAsMap["middleName"]
    this.login = jsonAsMap["login"]
    this.rating = 0.0
    this.role = Cayenne.objectForPK(OrmManager.context, Role::class.java,3)

    this.salt = DigestUtils.md5Hex(UUID.randomUUID().toString())
    this.password = SecurityManager.getPasswordHash(jsonAsMap["password"] ?: "", this.salt)
}