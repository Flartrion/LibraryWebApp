import kotlinx.serialization.Serializable

@Serializable
data class Rents(
    val id_rent: String,
    val id_user: String,
    val id_copy: String,
    val from_date: String,
    val until_date: String
)
