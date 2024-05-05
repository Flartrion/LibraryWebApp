package db.entity

import dataType.ItemLocation
import db.model.ItemLocations
import org.jetbrains.exposed.dao.EntityClass
import org.jetbrains.exposed.dao.UUIDEntity
import org.jetbrains.exposed.dao.id.EntityID
import java.util.*

// TODO: Think again about the app structure. What point is there sending sad single
//       entries when DAO opens up ability to find and transfer entire maps of values?
class ItemLocationEntity(id: EntityID<UUID>) : UUIDEntity(id) {
    companion object : EntityClass<UUID, ItemLocationEntity>(ItemLocations)

    var item by ItemEntity referencedOn ItemLocations.item
    var storage by StorageEntity referencedOn ItemLocations.storage
    var amount by ItemLocations.amount

    fun entityToItemLocation(): ItemLocation = ItemLocation(
        id_item = item.id.value.toString(),
        id_storage = storage.id.value.toString(),
        amount = amount
    )
}