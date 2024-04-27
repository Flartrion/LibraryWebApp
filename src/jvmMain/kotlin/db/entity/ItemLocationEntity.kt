package db.entity

import Item
import db.entity.ItemLocationEntity.Companion.referrersOn
import db.model.ItemLocations
import db.model.Items
import org.jetbrains.exposed.dao.Entity
import org.jetbrains.exposed.dao.EntityClass
import org.jetbrains.exposed.dao.id.EntityID
import java.util.UUID

class ItemLocationEntity(id: EntityID<UUID>) : Entity<UUID>(id) {
    companion object : EntityClass<UUID, ItemLocationEntity>(ItemLocations)

    var item by ItemEntity referencedOn ItemLocations.item
    var storage by StorageEntity referencedOn ItemLocations.storage
    var amount by ItemLocations.amount
}