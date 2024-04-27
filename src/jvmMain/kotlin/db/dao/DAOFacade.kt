package db.dao

import Item
import java.util.UUID

interface DAOFacade<T> {
    suspend fun getAll(): List<T>
    suspend fun get(id: UUID): T?
    suspend fun add(obj: T): Item?
    suspend fun edit(obj: T): Boolean
    suspend fun delete(id: UUID): Boolean
}