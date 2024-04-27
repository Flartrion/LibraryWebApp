package db.model

import org.jetbrains.exposed.dao.id.IdTable
import org.jetbrains.exposed.sql.kotlin.datetime.date
import java.util.UUID

object Users : IdTable<UUID>() {
    override val id = uuid("id_user").uniqueIndex().entityId()
    val role = integer("role")
    val fullName = varchar("full_name", 50)
    val dob = date("dob")
    val phoneNumber = varchar("phone_number", 15)
    val email = varchar("email", 40)
}