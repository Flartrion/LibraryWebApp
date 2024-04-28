package db.model

import org.jetbrains.exposed.dao.id.IdTable
import java.util.*

object Storages: IdTable<UUID>() {
    override val id = uuid("id_storage").uniqueIndex().entityId()
    val address = varchar("address", 50)
}