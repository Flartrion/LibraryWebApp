package db.model

import org.jetbrains.exposed.sql.Table

object ItemLocations : Table("itemLocations") {
    val item = reference("item", Items)
//    val idItem = uuid("id_item").references(Items.id).entityId()

    val storage = reference("storage", Storages)
//    val idStorage = uuid("id_storage").references(Storages.id).entityId()

    val amount = integer("amount")
    override val primaryKey = PrimaryKey(item, storage)
}