package db.model

import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IdTable
import org.jetbrains.exposed.sql.Column
import java.util.*

object ItemLocations : IdTable<UUID>() {
    override val id = uuid("id").uniqueIndex().entityId()
    val item = reference("item", Items)
    val storage = reference("storage", Storages)
    val amount = integer("amount")
}