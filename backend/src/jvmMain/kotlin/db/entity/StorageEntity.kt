package db.entity

import dataType.Storage
import db.model.Storages
import org.jetbrains.exposed.dao.Entity
import org.jetbrains.exposed.dao.EntityClass
import org.jetbrains.exposed.dao.id.EntityID
import java.util.*

class StorageEntity(id: EntityID<UUID>) : Entity<UUID>(id) {
    companion object : EntityClass<UUID, StorageEntity>(Storages)

    var address by Storages.address

    fun entityToStorage(): Storage = Storage(id.value.toString(), address)
}