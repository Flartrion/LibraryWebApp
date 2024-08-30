package db.entity

import dataType.Rent
import db.model.Rents
import org.jetbrains.exposed.dao.UUIDEntity
import org.jetbrains.exposed.dao.UUIDEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import java.util.*

// TODO: Think again about the app structure. What point is there sending sad single
//       entries when DAO opens up ability to find and transfer entire maps of values?
class RentEntity(id: EntityID<UUID>) : UUIDEntity(id), DataClassable<Rent> {
    companion object : UUIDEntityClass<RentEntity>(Rents)

    var user by UserEntity referencedOn Rents.user
    var item by ItemEntity referencedOn Rents.item
    var storage by StorageEntity referencedOn Rents.storage
    var dateFrom by Rents.dateFrom
    var dateUntil by Rents.dateUntil
    var dateStatus by Rents.dateStatus
    var status by Rents.status
    override fun toDataclass(): Rent = Rent(
        id = id.value.toString(),
        idUser = user.id.value.toString(),
        idItem = item.id.value.toString(),
        idStorage = storage.id.value.toString(),
        dateFrom = dateFrom.toString(),
        dateUntil = dateUntil.toString(),
        dateStatus = dateStatus.toString(),
        status = status.toString()
    )


}