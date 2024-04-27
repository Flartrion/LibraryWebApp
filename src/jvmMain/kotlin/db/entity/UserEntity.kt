package db.entity

import db.model.Users
import org.jetbrains.exposed.dao.Entity
import org.jetbrains.exposed.dao.EntityClass
import org.jetbrains.exposed.dao.id.EntityID
import java.util.*

class UserEntity(id: EntityID<UUID>): Entity<UUID>(id) {
    companion object : EntityClass<UUID, UserEntity>(Users)
    val role by Users.role
    val fullName by Users.fullName
    val dob by Users.dob
    val email by Users.email
    val phoneNumber by Users.phoneNumber
}