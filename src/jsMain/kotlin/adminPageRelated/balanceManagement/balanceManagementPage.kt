package adminPageRelated.balanceManagement

import BankHistory
import Storages
import kotlinx.browser.window
import kotlinx.css.*
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

data class BalanceManagementPageState(
    var entriesLoaded: Boolean,
    var storagesLoaded: Boolean,
    var inProcess: Boolean,
    var editing: Int = -1,

    var storageInput: String,
    var idItemInput: String,
    var amountInput: String,
    var dateInput: Date?
) : RState

external interface BalanceManagementPageProps : RProps {
    var entries: List<BankHistory>
    var storages: List<Storages>
}

@JsExport
class BalanceManagementPage : RComponent<BalanceManagementPageProps, BalanceManagementPageState>() {
    override fun componentDidMount() {
        setState {
            storageInput = ""
            idItemInput = ""
            amountInput = ""
            dateInput = null
        }
        val getStoragesRequest = XMLHttpRequest()
        getStoragesRequest.open("get", "/storages")
        getStoragesRequest.onload = {

            props.storages = Json.decodeFromString(getStoragesRequest.responseText)

            if (props.storages.isNotEmpty())
                setState {
                    storagesLoaded = true
                }
        }
        getStoragesRequest.send()

        val getEntriesRequest = XMLHttpRequest()
        getEntriesRequest.open("get", "/bankHistory")
        getEntriesRequest.onload = {
            props.entries = Json.decodeFromString(getEntriesRequest.responseText)
            if (props.entries.isNotEmpty())
                setState {
                    entriesLoaded = true
                }
        }
        getEntriesRequest.send()
    }

    override fun RBuilder.render() {
        styledDiv {
            css {
                display = Display.flex
                flexDirection = FlexDirection.column
                button {
                    width = LinearDimension.fillAvailable
                    height = 30.px
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

            styledTable {
                css {
                    alignSelf = Align.center
                    width = LinearDimension("60%")
                    borderCollapse = BorderCollapse.collapse
                    button {
                        display = Display.inlineBlock
                        fontSize = 14.px
                        width = LinearDimension.auto
                        margin(left = 5.px, right = 5.px)
                    }
                    tr {
                        td {
                            borderStyle = BorderStyle.solid
                            borderColor = Color("#777777")
                        }
                        th {
                            textAlign = TextAlign.center
                            borderStyle = BorderStyle.solid
                            borderColor = Color("#777777")
                        }
                    }
                }

                styledColGroup {
                    styledCol {
                        css { width = LinearDimension("10%") }
                    }
                    styledCol {
                        css { width = LinearDimension("10%") }
                    }
                    styledCol {
                        css { width = LinearDimension("10%") }
                    }
                    styledCol { }
                    styledCol { }
                }

                styledTr {
                    styledTh { +"id книги" }
                    styledTh { +"id хранилища" }
                    styledTh { +"Дата" }
                    styledTh { +"Количество" }
                    styledTh { +"Управление" }
                }

                if (state.storagesLoaded) {

                    styledTr {
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
                                        setState { dateInput = Date((it.target as HTMLInputElement).value) }
                                    }
                                }
                            }
                        }
                        styledTd {
                            styledInput {
                                attrs {
                                    value = state.amountInput
                                    type = InputType.number
                                    onChangeFunction = {
                                        setState { amountInput = (it.target as HTMLInputElement).value }
                                    }
                                }
                            }
                        }
                        styledTd {
                            styledButton {
                                attrs {
                                    disabled = state.amountInput.isEmpty() || state.idItemInput.isEmpty() ||
                                            state.storageInput.isEmpty() || state.inProcess
                                    onClickFunction = {
                                        setState {
                                            entriesLoaded = false
                                            inProcess = true
                                        }
                                        val insertRequest = XMLHttpRequest()
                                        insertRequest.open("post", "/bankHistory/insert")
                                        insertRequest.onload = {
                                            if (insertRequest.status == 200.toShort()) {
                                                val getEntriesRequest = XMLHttpRequest()
                                                getEntriesRequest.open("get", "/bankHistory")
                                                getEntriesRequest.onload = {
                                                    props.entries =
                                                        Json.decodeFromString(getEntriesRequest.responseText)
                                                    if (props.entries.isNotEmpty())
                                                        setState {
                                                            entriesLoaded = true
                                                        }
                                                }
                                                getEntriesRequest.send()
                                            } else {
                                                window.alert("${insertRequest.statusText} : ${insertRequest.responseText}")
                                            }
                                            setState { inProcess = false }
                                        }
                                        val data = FormData()
                                        data.append("id_item", state.idItemInput)
                                        data.append("id_storage", state.storageInput)
                                        data.append("date", state.dateInput?.toISOString() ?: Date().toISOString())
                                        data.append("change", state.amountInput)
                                        insertRequest.send(data)
                                    }
                                }
                                +"Добавить"
                            }
                        }
                    }
                }

                if (state.entriesLoaded) {
                    for (i in 0 until props.entries.size) {
                        balanceManagementListElement {
                            entry = props.entries[i]
                            changeEntriesLoaded = {
                                setState { entriesLoaded = it }
                            }
                            fetchAddress = { id ->
                                props.storages.find {
                                    it.id_storage == id
                                }?.address
                            }
                            update = {
                                val getEntriesRequest = XMLHttpRequest()
                                getEntriesRequest.open("get", "/bankHistory")
                                getEntriesRequest.onload = {
                                    props.entries = Json.decodeFromString(getEntriesRequest.responseText)
                                    if (props.entries.isNotEmpty())
                                        setState {
                                            entriesLoaded = true
                                        }
                                }
                                getEntriesRequest.send()
                            }
                        }
                    }
                } else {
//                    styledP { +"Mmmm, no, this is unwise" }
                }
            }
        }
    }
}
