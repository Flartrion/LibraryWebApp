package db.dao

import Item
import db.DatabaseSingleton.dbQuery
import db.entity.ItemEntity
import db.model.Items
import io.ktor.server.sessions.*
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import java.util.*

class DAOItemsImpl : DAOFacade<Item> {
    private fun rowToItem(row: ResultRow): Item {
        return Item(
            idItem = row[Items.id].toString(),
            isbn = row[Items.isbn],
            type = row[Items.type],
            title = row[Items.title],
            rlbc = row[Items.rlbc],
            language = row[Items.language],
            authors = row[Items.authors],
            details = row[Items.details]
        )
    }

    private fun entityToItem(row: ItemEntity): Item {
        return Item(
            idItem = row.id.value.toString(),
            isbn = row.isbn,
            type = row.type,
            title = row.title,
            rlbc = row.rlbc,
            language = row.language,
            authors = row.language,
            details = row.details
        )
    }

    override suspend fun getAll(): List<Item> = dbQuery {
        ItemEntity.all().map(::entityToItem)
    }

    override suspend fun get(id: UUID): Item? = dbQuery {
        ItemEntity.findById(id)?.let(::entityToItem)
    }

    override suspend fun add(obj: Item): Item? = dbQuery {
        val insertStatement = ItemEntity.new {
            isbn = obj.isbn
            rlbc = obj.rlbc
            title = obj.title
            authors = obj.authors
            type = obj.type
            details = obj.details
            language = obj.language
        }
        insertStatement.readValues.let(::rowToItem)
    }

    override suspend fun edit(obj: Item): Boolean = dbQuery {
        ItemEntity.findById(UUID.fromString(obj.idItem))?.let {
            it.isbn = obj.isbn
            it.rlbc = obj.rlbc
            it.title = obj.title
            it.authors = obj.authors
            it.type = obj.type
            it.details = obj.details
            it.language = obj.language
            true
        } ?: false
    }

    override suspend fun delete(id: UUID): Boolean = dbQuery {
        Items.deleteWhere { Items.id eq id } > 0
    }

}

val daoItems = DAOItemsImpl()