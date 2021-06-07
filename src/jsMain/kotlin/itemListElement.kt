import react.*

external interface ItemListElementProps : RProps {
    var item: Items
}

class ItemListElement : RComponent<ItemListElementProps, RState>() {
    override fun RBuilder.render() {

    }
}

fun RBuilder.itemListElement(handler: ItemListElementProps.() -> Unit): ReactElement {
    return child(ItemListElement::class) {
        attrs.handler()
    }
}