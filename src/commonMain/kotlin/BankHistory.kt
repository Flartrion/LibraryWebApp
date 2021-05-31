import kotlinx.serialization.Serializable

@Serializable
data class BankHistory(
    val id_entry: String,
    val id_copy: String,
    val change: String,
    val date: String
)