package db.model

import org.jetbrains.exposed.dao.id.UUIDTable
import org.jetbrains.exposed.sql.kotlin.datetime.date

object BankHistory : UUIDTable() {
    val item = reference("id_item", Items)
    val storage = reference("id_storage", Storages)
    val change = integer("change")
    val date = date("date")
}