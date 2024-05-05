package db.model

import org.jetbrains.exposed.dao.id.UUIDTable

object ItemLocations : UUIDTable() {
    val item = reference("item", Items)
    val storage = reference("storage", Storages)
    val amount = integer("amount")
}