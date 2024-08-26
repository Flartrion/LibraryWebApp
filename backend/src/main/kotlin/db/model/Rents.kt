package db.model

import org.jetbrains.exposed.dao.id.UUIDTable
import org.jetbrains.exposed.sql.kotlin.datetime.date

object Rents : UUIDTable() {
    val idUser = reference("id_user", Users.id)
    val idItem = reference("id_item", Items.id)
    val idStorage = reference("id_storage", Storages.id)
    val dateFrom = date("date_from")
    val dateUntil = date("date_until")
    val dateStatus = date("date_status")
    val status = integer("status")
}