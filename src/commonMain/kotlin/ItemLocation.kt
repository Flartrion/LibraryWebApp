import kotlinx.serialization.Serializable

@Serializable
data class ItemLocation(
    val id_copy: String,
    val id_storage: String,
    val amount: String
)