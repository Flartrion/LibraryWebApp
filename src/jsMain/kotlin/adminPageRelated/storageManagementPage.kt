package adminPageRelated

import Storages
import kotlinx.browser.window
import kotlinx.css.*
import kotlinx.html.FormMethod
import kotlinx.html.InputType
import kotlinx.html.hidden
import kotlinx.html.id
import kotlinx.html.js.onChangeFunction
import kotlinx.html.js.onClickFunction
import kotlinx.html.js.onSubmitFunction
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import org.w3c.dom.HTMLInputElement
import org.w3c.xhr.FormData
import org.w3c.xhr.XMLHttpRequest
import react.*
import react.dom.defaultValue
import styled.*

data class StorageManagementPageState(
    var isAddMenuVisible: Boolean,
    var isLoaded: Boolean,
    var inProcess: Boolean,
    var editing: Int = -1,
    var storages: List<Storages>
) : RState

external interface StorageManagementPageProps : RProps {
    var addressInput: String?
    var newAddressInput: String?
}

class StorageManagementPage : RComponent<StorageManagementPageProps, StorageManagementPageState>() {

    override fun componentDidMount() {
        val xml = XMLHttpRequest()
        xml.open("get", "/storages")
        xml.onload = {
            setState {
                isLoaded = true
                storages = Json.decodeFromString(xml.responseText)
            }
        }
        xml.send()
    }

    override fun RBuilder.render() {
        styledDiv {
            css {
                display = Display.flex
                flexDirection = FlexDirection.column
                button {
                    width = LinearDimension.fillAvailable
                    height = 50.px
                    fontSize = 17.pt
                    borderRadius = 0.px
                    borderStyle = BorderStyle.none
                    color = Color.white
                    backgroundColor = Color("#999999")
                    hover {
                        backgroundColor = Color("#aaaaaa")
                        color = Color.darkRed
                    }
                    disabled {
                        backgroundColor = Color("#aaaaaa")
                        color = Color.white
                    }
                }
            }
            if (state.isLoaded) {
                for (i in 0 until state.storages.size) {
                    styledDiv {
                        css {
                            button {
                                display = Display.inlineBlock
                                fontSize = 14.px
                                width = 100.px
                            }
                        }
                        styledP {
                            if (state.editing != i)
                                +"${state.storages[i].id_storage}: ${state.storages[i].address}"
                            else {
                                +"${state.storages[i].id_storage}: "
                                styledInput {
                                    attrs {
                                        type = InputType.text
                                        defaultValue = state.storages[i].address
                                        onChangeFunction = {
                                            props.newAddressInput = (it.target as HTMLInputElement).value
                                        }
                                    }
                                }
                            }
                        }
                        styledButton {
                            attrs {
                                disabled = state.inProcess
                                onClickFunction = {
                                    if (window.confirm("Удалить хранилище №${state.storages[i].id_storage}? Это действие нельзя будет отменить.")) {
                                        setState {
                                            inProcess = true
                                            editing = -1
                                        }
                                        val deleteRequest = XMLHttpRequest()
                                        deleteRequest.open("delete", "/storages/${state.storages[i].id_storage}")
                                        deleteRequest.onload = {
                                            val updateRequest = XMLHttpRequest()
                                            updateRequest.open("get", "/storages")
                                            updateRequest.onload = {
                                                setState {
                                                    inProcess = false
                                                    storages = Json.decodeFromString(updateRequest.responseText)
                                                }
                                            }
                                            updateRequest.send()
                                        }
                                        deleteRequest.send()
                                    }
                                }
                            }
                            +"Удалить"
                        }
                        if (state.editing != i) {
                            styledButton {
                                attrs {
                                    disabled = state.inProcess
                                    onClickFunction = {
                                        setState { editing = i }
                                        props.newAddressInput = state.storages[i].address
                                    }
                                }
                                +"Изменить"
                            }
                        } else {
                            styledButton {
                                attrs {
                                    disabled = state.inProcess
                                    onClickFunction = {
                                        if (!props.newAddressInput.isNullOrBlank()) {
                                            setState {
                                                inProcess = true
                                            }
                                            val updateRequest = XMLHttpRequest()
                                            updateRequest.open(
                                                "post",
                                                "/storages/update/${state.storages[i].id_storage}"
                                            )
                                            updateRequest.onload = {
                                                val updateAfterRequest = XMLHttpRequest()
                                                updateAfterRequest.open("get", "/storages")
                                                updateAfterRequest.onload = {
                                                    setState {
                                                        inProcess = false
                                                        editing = -1
                                                        storages =
                                                            Json.decodeFromString(updateAfterRequest.responseText)
                                                    }
                                                }
                                                updateAfterRequest.send()
                                            }
                                            val data = FormData()
                                            data.append("address", props.newAddressInput ?: "")
                                            updateRequest.send(data)
                                        }
                                    }
                                }
                                +"Подтвердить"
                            }
                            styledButton {
                                attrs {
                                    disabled = state.inProcess
                                    onClickFunction = {
                                        setState { editing = -1 }
                                    }
                                }
                                +"Отменить"
                            }
                        }
                    }
                }
            } else {
                styledP { +"Mmmm, no, this is unwise" }
            }
            styledButton {
                css { margin(top = 14.px) }
                attrs {
                    onClickFunction = {
                        setState { isAddMenuVisible = !isAddMenuVisible }
                    }
                }
                +"Добавить хранилище"
            }
            styledForm {
                attrs {
                    method = FormMethod.post
                    id = "addStorageForm"
                    onSubmitFunction = {
                        it.preventDefault()
                        if (!props.addressInput.isNullOrBlank()) {
                            setState {
                                inProcess = true
                            }
                            val insertRequest = XMLHttpRequest()
                            insertRequest.open("post", "/storages/insert")
                            insertRequest.onload = {
                                if (insertRequest.status != 400.toShort()) {
                                    val updateRequest = XMLHttpRequest()
                                    updateRequest.open("get", "/storages")
                                    updateRequest.onload = {
                                        setState {
                                            inProcess = false
                                            storages = Json.decodeFromString(updateRequest.responseText)
                                        }
                                    }
                                    updateRequest.send()
                                } else {
                                    console.error(insertRequest.responseText)
                                }
                            }
                            val data = FormData()
                            data.append("address", props.addressInput ?: "")
                            props.addressInput = ""
                            insertRequest.send(data)
                        }
                    }
                }
                styledTable {
                    attrs {
                        hidden = !state.isAddMenuVisible
                    }
                    css {
                        width = LinearDimension.fillAvailable
                        backgroundColor = Color("#888888")
                        children {
                            children {
                                textAlign = TextAlign.left
                                firstChild {
                                    textAlign = TextAlign.right
                                }
                                input {
                                    width = 500.px
                                }
                            }
                            lastChild {
                                children {
                                    textAlign = TextAlign.center
                                }
                            }
                        }
                    }
                    styledColGroup {
                        styledCol {
                            css {
                                width = LinearDimension("30%")
                                textAlign = TextAlign.right
                            }
                        }
                        styledCol {
                            css {
                                textAlign = TextAlign.left
                            }
                        }
                    }
                    styledTr {
                        styledTd {
                            styledLabel { +"Адрес: " }
                        }
                        styledTd {
                            styledInput {
                                attrs {
                                    name = "address"
                                    onChangeFunction = {
                                        props.addressInput = ((it.target) as HTMLInputElement).value
                                    }
                                    type = InputType.text
                                }
                            }
                        }
                    }

                    styledTr {
                        styledTd {
                            attrs {
                                colSpan = "2"
                            }
                            styledInput {
                                attrs {
                                    value = "Подтвердить"
                                    type = InputType.submit
                                    name = "submit"
                                }
                                css {
                                    borderRadius = 0.px
                                    fontFamily = "Arial"
                                    borderStyle = BorderStyle.none
                                    backgroundColor = Color("#999999")
                                    color = Color.white
                                    hover {
                                        backgroundColor = Color("#cccccc")
                                        color = Color.darkRed
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
