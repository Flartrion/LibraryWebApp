package db.entity

import db.model.Rents
import org.jetbrains.exposed.dao.Entity
import org.jetbrains.exposed.dao.EntityClass
import org.jetbrains.exposed.dao.id.EntityID
import java.util.UUID

class RentEntity(id: EntityID<UUID>) : Entity<UUID>(id) {
    companion object : EntityClass<UUID, RentEntity>(Rents)

    val idUser by Rents.idUser
    val idItem by Rents.idItem
    val idStorage by Rents.idStorage
    val dateFrom by Rents.dateFrom
    val dateUntil by Rents.dateUntil
}