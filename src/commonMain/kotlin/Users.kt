import kotlinx.serialization.Serializable

@Serializable
data class Users(
    val id_user: String,
    val role: String,
    val full_name: String,
    val date_of_birth: String,
    val phone_number: String,
    val email: String
)