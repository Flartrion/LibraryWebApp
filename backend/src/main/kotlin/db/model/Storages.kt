package db.model

import org.jetbrains.exposed.dao.id.UUIDTable

object Storages: UUIDTable() {
    val address = varchar("address", 50)
}