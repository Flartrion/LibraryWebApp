import kotlinx.serialization.Serializable

@Serializable
data class CopyLocation(
    val id_copy: String,
    val id_storage: String,
    val amount: String
)