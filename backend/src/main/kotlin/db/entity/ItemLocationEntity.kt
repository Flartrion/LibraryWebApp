package db.entity

// TODO: To hell with this entity. Since Exposed DAO as of now is incapable of using primary key of the table
//       as an entity id (why?), I'm switching to using DSL for this one use-case and anything else related

//class ItemLocationEntity(id: EntityID<UUID>) : Entity<UUID>(id), DataClassable<ItemLocation> {
//    companion object : EntityClass<UUID, ItemLocationEntity>(ItemLocations)
//
//    var item by ItemEntity referencedOn ItemLocations.item
//    var storage by StorageEntity referencedOn ItemLocations.storage
//    var amount by ItemLocations.amount
//
//    override fun toDataclass(): ItemLocation = ItemLocation(
//        idItem = item.id.value.toString(),
//        idStorage = storage.id.value.toString(),
//        amount = amount
//    )
//}