import kotlinx.serialization.Serializable

@Serializable
data class BankHistory(
    val id_entry: String,
    val id_item: String,
    val change: String,
    val date: String,
    val id_storage: String
)