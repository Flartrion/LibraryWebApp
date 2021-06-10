package adminPageRelated.rentManagement

import Rents
import kotlinx.browser.window
import kotlinx.css.Color
import kotlinx.css.backgroundColor
import kotlinx.html.js.onClickFunction
import org.w3c.xhr.XMLHttpRequest
import react.*
import styled.css
import styled.styledButton
import styled.styledTd
import styled.styledTr
import kotlin.js.Date

external interface RentManagementListElementProps : RProps {
    var entry: Rents
    var changeEntriesLoaded: (Boolean) -> Unit
    var fetchAddress: (String) -> String?
    var update: () -> Unit
}

@JsExport
class RentManagementListElement : RComponent<RentManagementListElementProps, RState>() {
    override fun RBuilder.render() {
        styledTr {
            styledTd {
                +props.entry.id_user
            }
            styledTd {
                +props.entry.id_item
            }
            styledTd {
                +(props.fetchAddress(props.entry.id_storage) ?: "Неизвестное хранилище")
            }
            styledTd {
                +props.entry.from_date
            }
            styledTd {
                css {
                    if (Date(props.entry.until_date).getTime() < Date().getTime())
                        backgroundColor = Color("#CCAAAA")
                }
                +props.entry.until_date
            }
            styledTd {
                styledButton {
                    attrs {
                        onClickFunction = {
                            if (window.confirm("Удалить эту запись? Это действие нельзя будет отменить.")) {
                                props.changeEntriesLoaded(false)
                                val getEntriesRequest = XMLHttpRequest()
                                getEntriesRequest.open("delete", "/rents/${props.entry.id_rent}")
                                getEntriesRequest.onload = {
                                    props.update()
                                }
                                getEntriesRequest.send()
                            }
                        }
                    }
                    +"Удалить"
                }
            }
        }
    }
}

fun RBuilder.rentManagementListElement(handler: RentManagementListElementProps.() -> Unit): ReactElement {
    return child(RentManagementListElement::class) {
        attrs.handler()
    }
}