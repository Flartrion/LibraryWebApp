package db.model

import org.jetbrains.exposed.dao.id.IdTable
import org.jetbrains.exposed.sql.kotlin.datetime.date
import java.util.*

object Rents : IdTable<UUID>() {
    override val id = uuid("id_rent").uniqueIndex().entityId()
    val idUser = uuid("id_user").references(Users.id)
    val idItem = uuid("id_item").references(Items.id)
    val idStorage = uuid("id_storage").references(Storages.id)
    val dateFrom = date("date_from")
    val dateUntil = date("date_until")
}