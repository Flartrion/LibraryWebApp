package db.entity

interface DataClassable<T> {
    fun toDataclass(): T
}