package db.model

import org.jetbrains.exposed.dao.id.IdTable
import org.jetbrains.exposed.sql.kotlin.datetime.date
import java.util.*

object Rents : IdTable<UUID>() {
    override val id = uuid("id_rent").uniqueIndex().entityId()
    val idUser = reference("id_user", Users.id)
    val idItem = reference("id_item", Items.id)
    val idStorage = reference("id_storage", Storages.id)
    val dateFrom = date("date_from")
    val dateUntil = date("date_until")
}