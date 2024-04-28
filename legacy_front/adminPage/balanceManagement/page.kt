package adminPage.balanceManagement

import BankHistoryEntry
import Storage
import emotion.react.css
import kotlinx.browser.window
import kotlinx.serialization.json.Json
import org.w3c.xhr.FormData
import org.w3c.xhr.XMLHttpRequest
import react.*
import react.dom.html.ReactHTML.button
import react.dom.html.ReactHTML.col
import react.dom.html.ReactHTML.colgroup
import react.dom.html.ReactHTML.div
import react.dom.html.ReactHTML.input
import react.dom.html.ReactHTML.option
import react.dom.html.ReactHTML.select
import react.dom.html.ReactHTML.table
import react.dom.html.ReactHTML.td
import react.dom.html.ReactHTML.th
import react.dom.html.ReactHTML.tr
import web.cssom.*
import web.html.InputType
import kotlin.js.Date

external interface BalanceManagementPageProps : Props {
    var entries: List<BankHistoryEntry>
    var storages: List<Storage>
}

val BalanceManagementPage = FC<BalanceManagementPageProps> { props ->
    var entriesLoaded by useState<Boolean>(false)
    var storagesLoaded by useState<Boolean>(false)
    var inProcess by useState<Boolean>(false)

    var storageInput by useState<String>("")
    var idItemInput by useState<String>("")
    var amountInput by useState<String>("")
    var dateInput by useState<Double>(Date.now())

    val getStoragesRequest = XMLHttpRequest()
    getStoragesRequest.open("get", "/storages")
    getStoragesRequest.onload = {

        props.storages = Json.decodeFromString(getStoragesRequest.responseText)

        if (props.storages.isNotEmpty())
            storagesLoaded = true

        val getEntriesRequest = XMLHttpRequest()
        getEntriesRequest.open("get", "/bankHistory")
        getEntriesRequest.onload = {
            props.entries = Json.decodeFromString(getEntriesRequest.responseText)
            if (props.entries.isNotEmpty())
                entriesLoaded = true
        }
        getEntriesRequest.send()
    }
    getStoragesRequest.send()


    div {
        css {
            display = Display.flex
            flexDirection = FlexDirection.column
            button {
                width = 100.pc
                height = 30.px
            }
        }

        table {
            css {
                alignSelf = AlignSelf.center
                width = 60.pc
                borderCollapse = BorderCollapse.collapse
                button {
                    display = Display.inlineBlock
                    fontSize = 14.px
                    width = Auto.auto
                    marginLeft = 5.px
                    marginRight = 5.px
                }
                tr {
                    td {
                        borderColor = Color("#777777")
                    }
                    th {
                        textAlign = TextAlign.center
                        borderColor = Color("#777777")
                    }
                }
            }

            colgroup {
                col {
                    css { width = 10.pc }
                }
                col {
                    css { width = 10.pc }
                }
                col {
                    css { width = 10.pc }
                }
                col { }
                col { }
            }

            tr {
                th { +"id книги" }
                th { +"id хранилища" }
                th { +"Дата" }
                th { +"Количество" }
                th { +"Управление" }
            }

            if (storagesLoaded) {

                tr {
                    td {
                        input {

                            value = idItemInput
                            type = InputType.number
                            onChange = {
                                idItemInput = it.target.value
                            }

                        }
                    }
                    td {
                        select {

                            value = storageInput
                            onChange = {
                                storageInput = it.target.value
                            }

                            option {
                                value = ""
                            }
                            for (i in 0 until props.storages.size) {
                                option {
                                    value = props.storages[i].id_storage
                                    +props.storages[i].address
                                }
                            }
                        }
                    }
                    td {
                        input {
                            type = InputType.date
                            onChange = {
                                dateInput = it.target.value.toDouble()
                            }
                        }
                    }
                    td {
                        input {

                            value = amountInput
                            type = InputType.number
                            onChange = {
                                amountInput = it.target.value
                            }

                        }
                    }
                    td {
                        button {

                            disabled = amountInput.isEmpty() || idItemInput.isEmpty() ||
                                    storageInput.isEmpty() || inProcess
                            onClick = {
                                TODO("Move away logic")
                                entriesLoaded = false
                                inProcess = true

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

                                                entriesLoaded = true

                                        }
                                        getEntriesRequest.send()
                                    } else {
                                        window.alert("${insertRequest.statusText} : ${insertRequest.responseText}")

                                        entriesLoaded = true

                                    }
                                    inProcess = false
                                }

                                val data = FormData()
                                data.append("id_item", idItemInput)
                                data.append("id_storage", storageInput)
                                data.append("date", Date(dateInput).toISOString())
                                data.append("change", amountInput)
                                insertRequest.send(data)
                            }
                        }
                    }
                    +"Добавить"

                }
            }
        }

        if (entriesLoaded) {
            TODO("Solve this mystery")
            for (i in 0 until props.entries.size) {
                balanceManagementListElement {
                    entry = props.entries[i]
                    changeEntriesLoaded = {
                        entriesLoaded = it
                    }
                }
                fun fetchAddress() = { id: String ->
                    props.storages.find {
                        it.id_storage == id
                    }?.address
                }

                fun update() = {
                    val getEntriesRequest = XMLHttpRequest()
                    getEntriesRequest.open("get", "/bankHistory")
                    getEntriesRequest.onload = {
                        props.entries = Json.decodeFromString(getEntriesRequest.responseText)
                        if (props.entries.isNotEmpty())

                            entriesLoaded = true

                    }
                    getEntriesRequest.send()
                }
            }
        }
    }
}


