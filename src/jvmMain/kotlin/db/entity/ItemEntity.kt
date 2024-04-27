package db.entity

import db.model.Items
import org.jetbrains.exposed.dao.Entity
import org.jetbrains.exposed.dao.EntityClass
import org.jetbrains.exposed.dao.id.EntityID
import java.util.*

class ItemEntity(id: EntityID<UUID>) : Entity<UUID>(id) {
    companion object : EntityClass<UUID, ItemEntity>(Items)

    var isbn by Items.isbn
    var rlbc by Items.rlbc
    var type by Items.type
    var title by Items.title
    var details by Items.details
    var authors by Items.authors
    var language by Items.language
}