package db.model

import org.jetbrains.exposed.dao.id.IdTable
import org.jetbrains.exposed.sql.kotlin.datetime.date
import java.util.*

object BankHistory : IdTable<UUID>() {
    override val id = uuid("id_entry").uniqueIndex().entityId()

    val item = reference("id_item", Items)
    val storage = reference("id_storage", Storages)
    val change = integer("change")
    val date = date("date")
}