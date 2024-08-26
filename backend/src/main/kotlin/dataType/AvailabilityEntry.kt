package dataType

import kotlinx.serialization.Serializable

@Serializable
data class AvailabilityEntry(val idItem: String, val idStorage: String, val amount: Int, val address: String)
