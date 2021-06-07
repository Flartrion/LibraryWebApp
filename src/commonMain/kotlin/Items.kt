import kotlinx.serialization.Serializable

@Serializable
data class Items(
    val id_item: String,
    val isbn: String,
    val rlbc: String,
    val title: String,
    val authors: String,
    val type: String,
    val details: String,
    val language: String,
)