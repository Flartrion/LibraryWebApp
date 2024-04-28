package adminPage.storageManagement

import Storage
import kotlinx.browser.window
import kotlinx.css.*
import kotlinx.html.InputType
import kotlinx.html.js.onChangeFunction
import kotlinx.html.js.onClickFunction
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import org.w3c.dom.HTMLInputElement
import org.w3c.xhr.FormData
import org.w3c.xhr.XMLHttpRequest
import react.*
import react.dom.defaultValue
import styled.*

external interface StorageListElementState : RState {
    var newAddressInput: String
}

external interface StorageListElementProps : RProps {
    var editState: Boolean
    var processState: Boolean
    var storage: Storage

    var changeEditing: (Boolean) -> Unit
    var changeInProcess: (Boolean) -> Unit
    var onUpdateFun: (List<Storage>) -> Unit
}

@JsExport
class StorageListElement : RComponent<StorageListElementProps, StorageListElementState>() {
    override fun componentDidMount() {
        setState { newAddressInput = props.storage.address }
    }

    override fun RBuilder.render() {
        styledDiv {
            css {
                button {
                    display = Display.inlineBlock
                    fontSize = 14.px
                    width = 100.px
                }
            }
            styledP {
                if (!props.editState)
                    +"${props.storage.id_storage}: ${props.storage.address}"
                else {
                    +"${props.storage.id_storage}: "
                    styledInput {
                        attrs {
                            type = InputType.text
                            defaultValue = props.storage.address
                            onChangeFunction = {
                                val newVal = (it.target as HTMLInputElement).value
                                setState { newAddressInput = newVal }
                            }
                        }
                    }
                }
            }
            styledButton {
                attrs {
                    disabled = props.processState
                    onClickFunction = {
                        if (window.confirm("Удалить хранилище №${props.storage.id_storage}? Это действие нельзя будет отменить.")) {
                            props.changeEditing(false)
                            props.changeInProcess(true)
                            val deleteRequest = XMLHttpRequest()
                            deleteRequest.open("delete", "/storages/${props.storage.id_storage}")
                            deleteRequest.onload = {
                                val updateRequest = XMLHttpRequest()
                                updateRequest.open("get", "/storages")
                                updateRequest.onload = {
                                    props.changeInProcess(false)
                                    props.onUpdateFun(Json.decodeFromString(updateRequest.responseText))
                                }
                                updateRequest.send()
                            }
                            deleteRequest.send()
                        }
                    }
                }
                +"Удалить"
            }
            if (!props.editState) {
                styledButton {
                    attrs {
                        disabled = props.processState
                        onClickFunction = {
                            props.changeEditing(true)
                            setState { newAddressInput = props.storage.address }
                        }
                    }
                    +"Изменить"
                }
            } else {
                styledButton {
                    attrs {
                        disabled =
                            props.processState || state.newAddressInput == props.storage.address
                        onClickFunction = {
                            props.changeInProcess(true)

                            val updateRequest = XMLHttpRequest()
                            updateRequest.open(
                                "post",
                                "/storages/update/${props.storage.id_storage}"
                            )
                            updateRequest.onload = {
                                val updateAfterRequest = XMLHttpRequest()
                                updateAfterRequest.open("get", "/storages")
                                updateAfterRequest.onload = {
                                    props.changeEditing(false)
                                    props.changeInProcess(false)
                                    props.onUpdateFun(Json.decodeFromString(updateAfterRequest.responseText))
                                }
                                updateAfterRequest.send()
                            }
                            val data = FormData()
                            data.append("address", state.newAddressInput)
                            updateRequest.send(data)
                        }

                    }
                    +"Подтвердить"
                }
                styledButton {
                    attrs {
                        disabled = props.processState
                        onClickFunction = {
                            props.changeEditing(false)
                        }
                    }
                    +"Отменить"
                }
            }
        }
    }
}

fun RBuilder.storageListElement(handler: StorageListElementProps.() -> Unit): ReactElement {
    return child(StorageListElement::class) {
        attrs.handler()
    }
}