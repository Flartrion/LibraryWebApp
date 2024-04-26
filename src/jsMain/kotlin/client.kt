import react.create
import react.dom.client.createRoot
import web.dom.document


fun main() {
    val container = document.getElementById("root") ?: error("No root?")
    createRoot(container).render(App.create())
}
