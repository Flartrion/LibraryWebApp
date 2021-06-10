package adminPageRelated.rentManagement

import Storages
import kotlinx.browser.window
import kotlinx.html.InputType
import kotlinx.html.js.onChangeFunction
import kotlinx.html.js.onClickFunction
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import org.w3c.dom.HTMLInputElement
import org.w3c.dom.HTMLSelectElement
import org.w3c.xhr.FormData
import org.w3c.xhr.XMLHttpRequest
import react.*
import styled.*
import kotlin.js.Date

external interface AddRentState : RState {
    var userInput: String
    var storageInput: String
    var idItemInput: String
    var fromDateInput: Date?
    var untilDateInput: Date?
}

external interface AddRentProps : RProps {
    var storages: List<Storages>
    var inProcess: Boolean
    var changeInProcess: (Boolean) -> Unit
    var update: () -> Unit
}

class AddRent : RComponent<AddRentProps, AddRentState>() {
    override fun componentWillMount() {
        setState {
            userInput = ""
            storageInput = ""
            idItemInput = ""
            fromDateInput = null
            untilDateInput = null
        }
    }

    override fun RBuilder.render() {
        styledTr {
            styledTd {
                styledInput {
                    attrs {
                        value = state.userInput
                        type = InputType.number
                        onChangeFunction = {
                            setState { userInput = (it.target as HTMLInputElement).value }
                        }
                    }
                }
            }
            styledTd {
                styledInput {
                    attrs {
                        value = state.idItemInput
                        type = InputType.number
                        onChangeFunction = {
                            setState { idItemInput = (it.target as HTMLInputElement).value }
                        }
                    }
                }
            }
            styledTd {
                styledSelect {
                    attrs {
                        value = state.storageInput
                        onChangeFunction = {
                            setState { storageInput = (it.target as HTMLSelectElement).value }
                        }
                    }
                    styledOption {
                        attrs { value = "" }
                    }
                    for (i in 0 until props.storages.size) {
                        styledOption {
                            attrs {
                                value = props.storages[i].id_storage
                            }
                            +props.storages[i].address
                        }
                    }
                }
            }
            styledTd {
                styledInput {
                    attrs {
                        type = InputType.date
                        onChangeFunction = {
                            setState { fromDateInput = Date((it.target as HTMLInputElement).value) }
                        }
                    }
                }
            }
            styledTd {
                styledInput {
                    attrs {
                        type = InputType.date
                        onChangeFunction = {
                            setState { untilDateInput = Date((it.target as HTMLInputElement).value) }
                        }
                    }
                }
            }
            styledTd {
                styledButton {
                    attrs {
                        disabled = state.idItemInput.isBlank() || state.userInput.isBlank() ||
                                state.fromDateInput == null || state.fromDateInput == null ||
                                state.storageInput.isBlank() || props.inProcess
                        onClickFunction = {
                            props.changeInProcess(true)
                            val insertRequest = XMLHttpRequest()
                            insertRequest.open("post", "/rents/insert")
                            insertRequest.onload = {
                                if (insertRequest.status == 200.toShort()) {
                                    props.update()
                                } else {
                                    window.alert("${insertRequest.statusText} : ${insertRequest.responseText}")
                                }
                                props.changeInProcess(false)
                            }
                            val data = FormData()
                            data.append("id_item", state.idItemInput)
                            data.append("id_storage", state.storageInput)
                            data.append("id_user", state.userInput)
                            data.append("from_date", state.fromDateInput!!.toISOString())
                            data.append("until_date", state.untilDateInput!!.toISOString())
                            insertRequest.send(data)
                        }
                    }
                    +"Добавить"
                }
            }
        }
    }
}

fun RBuilder.addRent(handler: AddRentProps.() -> Unit): ReactElement {
    return child(AddRent::class) {
        attrs.handler()
    }
}