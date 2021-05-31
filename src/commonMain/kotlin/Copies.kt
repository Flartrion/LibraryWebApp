import kotlinx.serialization.Serializable

@Serializable
data class Copies(
    val id_copy: String,
    val id_item: String,
    val tome: String,
    val language: String,
    val bank: String
)