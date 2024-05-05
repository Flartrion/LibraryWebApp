package db.model

import org.jetbrains.exposed.dao.id.UUIDTable

object Items : UUIDTable() {
    val isbn = varchar("isbn", 20)
    val rlbc = varchar("rlbc", 20)
    val title = varchar("title", 40)
    val authors = varchar("authors", 60)
    val type = varchar("type", 20)
    val details = text("details")
    val language = varchar("language", 20)
}
