package db.model

import org.jetbrains.exposed.dao.id.UUIDTable
import org.jetbrains.exposed.sql.kotlin.datetime.date

object Users : UUIDTable() {
    val role = integer("role")
    val fullName = varchar("full_name", 50)
    val dob = date("dob")
    val phoneNumber = varchar("phone_number", 15)
    val email = varchar("email", 40)
}