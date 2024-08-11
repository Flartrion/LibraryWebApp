package db.entity

import dataType.BankHistoryEntry
import db.model.BankHistory
import org.jetbrains.exposed.dao.UUIDEntity
import org.jetbrains.exposed.dao.UUIDEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import java.util.*

// TODO: Think again about the app structure. What point is there sending sad single
//       entries when DAO opens up ability to find and transfer entire maps of values?
class BankHistoryEntryEntity(id: EntityID<UUID>) : UUIDEntity(id), DataClassable<BankHistoryEntry> {
    companion object : UUIDEntityClass<BankHistoryEntryEntity>(BankHistory)

    var item by ItemEntity referencedOn BankHistory.item
    var storage by StorageEntity referencedOn BankHistory.storage
    var date by BankHistory.date
    var change by BankHistory.change

    override fun toDataclass(): BankHistoryEntry = BankHistoryEntry(
        id.value.toString(),
        item.id.value.toString(),
        storage.id.value.toString(),
        change.toString(),
        date.toString(),
    )
}