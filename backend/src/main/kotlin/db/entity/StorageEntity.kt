package db.entity

import dataType.Storage
import db.model.Storages
import org.jetbrains.exposed.dao.UUIDEntity
import org.jetbrains.exposed.dao.UUIDEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import java.util.*

class StorageEntity(id: EntityID<UUID>) : UUIDEntity(id), DataClassable<Storage> {
    companion object : UUIDEntityClass<StorageEntity>(Storages)

    var address by Storages.address

    override fun toDataclass(): Storage = Storage(id.value.toString(), address)
}