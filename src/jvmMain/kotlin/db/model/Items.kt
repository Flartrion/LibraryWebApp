package db.model

import org.jetbrains.exposed.dao.Entity
import org.jetbrains.exposed.dao.EntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IdTable
import java.util.*

object Items : IdTable<UUID>() {
    override val id = uuid("id_item").uniqueIndex().entityId()
    val isbn = varchar("isbn", 20)
    val rlbc = varchar("rlbc", 20)
    val title = varchar("title", 40)
    val authors = varchar("authors", 60)
    val type = varchar("type", 20)
    val details = text("details")
    val language = varchar("language", 20)
}
