package db.model

import org.jetbrains.exposed.dao.id.UUIDTable
import org.jetbrains.exposed.sql.kotlin.datetime.date

object Rents : UUIDTable() {
    val user = reference("user", Users)
    val item = reference("item", Items)
    val storage = reference("storage", Storages)
    val dateFrom = date("date_from")
    val dateUntil = date("date_until")
    val dateStatus = date("date_status")
    val status = integer("status")
}