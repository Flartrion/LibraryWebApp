package db.entity

import db.model.BankHistory
import db.model.Items
import db.model.Rents.references
import db.model.Storages
import org.jetbrains.exposed.dao.Entity
import org.jetbrains.exposed.dao.EntityClass
import org.jetbrains.exposed.dao.UUIDEntity
import org.jetbrains.exposed.dao.id.EntityID
import java.util.*

// TODO: Think again about the app structure. What point is there sending sad single
//       entries when DAO opens up ability to find and transfer entire maps of values?
class BankHistoryEntryEntity(id: EntityID<UUID>) : UUIDEntity(id) {
    companion object : EntityClass<UUID, BankHistoryEntryEntity>(BankHistory)

    var item by ItemEntity referencedOn BankHistory.item
    var storage by StorageEntity referencedOn BankHistory.storage
    var date by BankHistory.date
    var change by BankHistory.change
}