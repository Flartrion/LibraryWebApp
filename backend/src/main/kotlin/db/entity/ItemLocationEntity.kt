package db.entity

import dataType.ItemLocation
import db.model.ItemLocations
import org.jetbrains.exposed.dao.CompositeEntity
import org.jetbrains.exposed.dao.CompositeEntityClass
import org.jetbrains.exposed.dao.id.CompositeID
import org.jetbrains.exposed.dao.id.EntityID

// TODO: Think again about the app structure. What point is there sending sad single
//       entries when DAO opens up ability to find and transfer entire maps of values?
class ItemLocationEntity(id: EntityID<CompositeID>) : CompositeEntity(id), DataClassable<ItemLocation> {
    companion object : CompositeEntityClass<ItemLocationEntity>(ItemLocations)

    var item by ItemEntity referencedOn ItemLocations.item
    var storage by StorageEntity referencedOn ItemLocations.storage
    var amount by ItemLocations.amount

    override fun toDataclass(): ItemLocation = ItemLocation(
        id_item = item.id.value.toString(),
        id_storage = storage.id.value.toString(),
        amount = amount
    )
}