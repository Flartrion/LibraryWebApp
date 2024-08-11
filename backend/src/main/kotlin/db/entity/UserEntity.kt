package db.entity

import dataType.User
import db.model.Users
import org.jetbrains.exposed.dao.EntityClass
import org.jetbrains.exposed.dao.UUIDEntity
import org.jetbrains.exposed.dao.id.EntityID
import java.util.*

class UserEntity(id: EntityID<UUID>) : UUIDEntity(id), DataClassable<User> {
    companion object : EntityClass<UUID, UserEntity>(Users)

    var role by Users.role
    var fullName by Users.fullName
    var dob by Users.dob
    var phoneNumber by Users.phoneNumber
    var email by Users.email

    override fun toDataclass(): User = User(
        id = id.value.toString(),
        role = role.toString(),
        fullName = fullName,
        dob = dob.toString(),
        phoneNumber = phoneNumber,
        email = email
    )
}