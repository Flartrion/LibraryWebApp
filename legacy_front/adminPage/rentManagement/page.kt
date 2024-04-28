package adminPage.rentManagement

import Rent
import Storage
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
import org.w3c.dom.HTMLSelectElement
import org.w3c.xhr.XMLHttpRequest
import react.*
import styled.*

external interface RentManagementPageState : RState {
    var entriesLoaded: Boolean
    var storagesLoaded: Boolean
    var inProcess: Boolean
    var filterOpened: Boolean

    var userFilter: String
    var storageFilter: String
    var idItemFilter: String
}

external interface RentManagementPageProps : RProps {
    var entries: List<Rent>
    var storages: List<Storage>
}

@JsExport
class RentManagementPage : RComponent<RentManagementPageProps, RentManagementPageState>() {
    override fun componentWillMount() {
        setState {
            userFilter = ""
            storageFilter = ""
            idItemFilter = ""
        }

        val getStoragesRequest = XMLHttpRequest()
        getStoragesRequest.open("get", "/storages")
        getStoragesRequest.onload = {

            props.storages = Json.decodeFromString(getStoragesRequest.responseText)

            if (props.storages.isNotEmpty())
                setState {
                    storagesLoaded = true
                }

            val getEntriesRequest = XMLHttpRequest()
            getEntriesRequest.open("get", "/rents")
            getEntriesRequest.onload = {
                props.entries = Json.decodeFromString(getEntriesRequest.responseText)
                if (props.entries.isNotEmpty())
                    setState {
                        entriesLoaded = true
                    }
            }
            getEntriesRequest.send()
        }
        getStoragesRequest.send()
    }

    override fun RBuilder.render() {
        styledDiv {
            css {
                display = Display.flex
                flexDirection = FlexDirection.column
                button {
                    width = LinearDimension.fillAvailable
                    height = 30.px
                }
            }

            styledDiv {
                css {
                    padding(top = 10.px, bottom = 10.px)
                    backgroundColor = Color("#cccccc")
                    textAlign = TextAlign.center
                    cursor = Cursor.pointer
                    userSelect = UserSelect.none
                }
                attrs {
                    onClickFunction = {
                        setState {
                            filterOpened = !filterOpened
                        }
                    }
                }
                +"Поиск"
            }

            if (state.storagesLoaded) {
                styledForm {
                    attrs {
                        method = FormMethod.post
                        id = "searchForm"
                        onSubmitFunction = {
                            it.preventDefault()
                            setState {
                                entriesLoaded = false
                            }
                            val getRequest = XMLHttpRequest()
                            var queryString = "/rents?"
                            if (!state.idItemFilter.isBlank())
                                queryString += "&id_item=${state.idItemFilter}"
                            if (!state.storageFilter.isBlank())
                                queryString += "&id_storage=${state.storageFilter}"
                            if (!state.userFilter.isBlank())
                                queryString += "&id_user=${state.userFilter}"
                            getRequest.open("get", queryString)
                            getRequest.onload = {
                                props.entries = Json.decodeFromString(getRequest.responseText)
                                setState {
                                    if (props.entries.isNotEmpty())
                                        entriesLoaded = true
                                }
                            }
                            getRequest.send()
                        }
                    }
                    styledTable {
                        attrs {
                            hidden = !state.filterOpened
                        }
                        css {
                            width = LinearDimension("100%")
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
                                styledLabel { +"id пользователя: " }
                            }
                            styledTd {
                                styledInput {
                                    attrs {
                                        form = "searchForm"
                                        name = "name"
                                        type = InputType.number
                                        min = "0"
                                        value = state.userFilter
                                        onChangeFunction = {
                                            setState { userFilter = (it.target as HTMLInputElement).value }
                                        }
                                    }
                                }
                            }
                        }
                        styledTr {
                            styledTd {
                                styledLabel { +"Хранилище: " }
                            }
                            styledTd {
                                styledSelect {
                                    attrs {
                                        form = "searchForm"
                                        name = "authors"
                                        onChangeFunction = {
                                            setState { storageFilter = (it.target as HTMLSelectElement).value }
                                        }
                                    }
                                    styledOption {
                                        attrs { value = "" }
                                    }
                                    for (i in 0 until props.storages.size) {
                                        styledOption {
                                            attrs { value = props.storages[i].id_storage }
                                            +props.storages[i].address
                                        }
                                    }
                                }
                            }
                        }

                        styledTr {
                            styledTd {
                                styledLabel { +"id книги: " }
                            }
                            styledTd {
                                styledInput {
                                    attrs {
                                        form = "searchForm"
                                        name = "type"
                                        type = InputType.number
                                        min = "0"
                                        value = state.idItemFilter
                                        onChangeFunction = {
                                            setState { idItemFilter = (it.target as HTMLInputElement).value }
                                        }
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
//                                        disabled = state.userFilter.isBlank() &&
//                                                state.storageFilter.isBlank() &&
//                                                state.idItemFilter.isBlank()
                                        form = "searchForm"
                                        name = "submit"
                                        value = "Поиск"
                                        type = InputType.submit
                                    }
                                    css {
                                        fontFamily = "Arial"
                                        backgroundColor = Color("#999999")
                                        color = Color.white
                                        hover {
                                            backgroundColor = Color("#cccccc")
                                            color = Color.darkRed
                                        }
                                        disabled {
                                            color = Color.white
                                            backgroundColor = Color("#aaaaaa")
                                        }
                                    }
                                }
                            }
                        }
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
                        css { width = LinearDimension("15%") }
                    }
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
                    styledTh { +"id пользователя" }
                    styledTh { +"id книги" }
                    styledTh { +"id хранилища" }
                    styledTh { +"Дата аренды" }
                    styledTh { +"Дата сдачи" }
                    styledTh { +"Управление" }
                }

                if (state.storagesLoaded) {
                    addRent {
                        update = {
                            val getEntriesRequest = XMLHttpRequest()
                            var queryString = "/rents?"
                            if (!state.idItemFilter.isBlank())
                                queryString += "&id_item=${state.idItemFilter}"
                            if (!state.storageFilter.isBlank())
                                queryString += "&id_storage=${state.storageFilter}"
                            if (!state.userFilter.isBlank())
                                queryString += "&id_user=${state.userFilter}"
                            getEntriesRequest.open("get", queryString)
                            getEntriesRequest.onload = {
                                props.entries =
                                    Json.decodeFromString(getEntriesRequest.responseText)
                                if (props.entries.isNotEmpty())
                                    setState {
                                        entriesLoaded = true
                                        inProcess = false
                                    }
                            }
                            getEntriesRequest.send()
                        }
                        changeInProcess = {
                            setState { inProcess = it }
                        }
                        storages = props.storages
                    }
                }

                if (state.entriesLoaded) {
                    for (i in 0 until props.entries.size) {
                        rentManagementListElement {
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
                                getEntriesRequest.open("get", "/rents")
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
