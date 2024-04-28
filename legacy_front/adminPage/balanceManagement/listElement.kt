package adminPage.balanceManagement

import BankHistoryEntry
import kotlinx.browser.window
import kotlinx.html.js.onClickFunction
import org.w3c.xhr.XMLHttpRequest
import react.*
import styled.styledButton
import styled.styledTd
import styled.styledTr

external interface BalanceManagementListElementProps : RProps {
    var entry: BankHistoryEntry
    var changeEntriesLoaded: (Boolean) -> Unit
    var fetchAddress: (String) -> String?
    var update: () -> Unit
}

@JsExport
class BalanceManagementListElement : RComponent<BalanceManagementListElementProps, RState>() {
    override fun RBuilder.render() {
        styledTr {
            styledTd {
                +props.entry.id_item
            }
            styledTd {
                +(props.fetchAddress(props.entry.id_storage) ?: "Неизвестное хранилище")
            }
            styledTd {
                +props.entry.date
            }
            styledTd {
                +props.entry.change
            }
            styledTd {
                styledButton {
                    attrs {
                        onClickFunction = {
                            if (window.confirm("Удалить эту запись? Это действие нельзя будет отменить.")) {
                                props.changeEntriesLoaded(false)
                                val getEntriesRequest = XMLHttpRequest()
                                getEntriesRequest.open("delete", "/bankHistory/${props.entry.id_entry}")
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

fun RBuilder.balanceManagementListElement(handler: BalanceManagementListElementProps.() -> Unit): ReactElement {
    return child(BalanceManagementListElement::class) {
        attrs.handler()
    }
}