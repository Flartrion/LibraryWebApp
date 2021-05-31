import kotlinx.serialization.Serializable

@Serializable
data class Storages(
    val id_storage: String,
    val address: String
)