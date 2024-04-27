package db.entity

import db.model.BankHistory
import db.model.Items
import db.model.Rents.references
import db.model.Storages
import org.jetbrains.exposed.dao.Entity
import org.jetbrains.exposed.dao.EntityClass
import org.jetbrains.exposed.dao.id.EntityID
import java.util.*

class BankHistoryEntryEntity(id: EntityID<UUID>) : Entity<UUID>(id) {
    companion object : EntityClass<UUID, BankHistoryEntryEntity>(BankHistory)

    var item by ItemEntity referencedOn BankHistory.item
    var storage by StorageEntity referencedOn BankHistory.storage
    var date by BankHistory.date
    var change by BankHistory.change
}