package db.entity

import dataType.Item
import db.model.Items
import org.jetbrains.exposed.dao.UUIDEntity
import org.jetbrains.exposed.dao.UUIDEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import java.util.*

class ItemEntity(id: EntityID<UUID>) : UUIDEntity(id), DataClassable<Item> {
    companion object : UUIDEntityClass<ItemEntity>(Items)

    var isbn by Items.isbn
    var rlbc by Items.rlbc
    var type by Items.type
    var title by Items.title
    var details by Items.details
    var authors by Items.authors
    var language by Items.language

    override fun toDataclass(): Item = Item(
        id = id.value.toString(),
        isbn = isbn,
        rlbc = rlbc,
        title = title,
        authors = authors,
        type = type,
        details = details,
        language = language
    )
}