package support

fun filterWrap(string: String): String = if (string == "") "%" else "%$string%"