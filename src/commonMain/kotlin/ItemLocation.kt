import kotlinx.serialization.Serializable

@Serializable
data class ItemLocation(
    val id_item: String,
    val id_storage: String,
    val amount: String
)