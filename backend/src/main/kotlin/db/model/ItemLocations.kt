package db.model

import org.jetbrains.exposed.dao.id.CompositeIdTable

object ItemLocations : CompositeIdTable("itemLocations") {
    val item = reference("item", Items)
    val storage = reference("storage", Storages)
    val amount = integer("amount")
    override val primaryKey = PrimaryKey(item, storage)
}