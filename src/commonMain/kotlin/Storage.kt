import kotlinx.serialization.Serializable

@Serializable
data class Storage(
    val id_storage: String,
    val address: String
)