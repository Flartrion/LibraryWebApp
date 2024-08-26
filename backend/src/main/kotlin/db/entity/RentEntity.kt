package db.entity

import dataType.Rent
import db.model.Rents
import org.jetbrains.exposed.dao.EntityClass
import org.jetbrains.exposed.dao.UUIDEntity
import org.jetbrains.exposed.dao.id.EntityID
import java.util.*

// TODO: Think again about the app structure. What point is there sending sad single
//       entries when DAO opens up ability to find and transfer entire maps of values?
class RentEntity(id: EntityID<UUID>) : UUIDEntity(id), DataClassable<Rent> {
    companion object : EntityClass<UUID, RentEntity>(Rents)

    val user by UserEntity referencedOn Rents.idUser
    val item by ItemEntity referencedOn Rents.idItem
    val storage by StorageEntity referencedOn Rents.idStorage
    val dateFrom by Rents.dateFrom
    val dateUntil by Rents.dateUntil
    val dateStatus by Rents.dateStatus
    val status by Rents.status
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