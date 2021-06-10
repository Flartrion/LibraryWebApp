package itemRelated

import ItemLocation
import Items
import kotlinx.browser.document
import kotlinx.browser.window
import kotlinx.css.*
import kotlinx.html.FormMethod
import kotlinx.html.InputType
import kotlinx.html.hidden
import kotlinx.html.js.onChangeFunction
import kotlinx.html.js.onClickFunction
import kotlinx.html.js.onSubmitFunction
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import org.w3c.dom.HTMLInputElement
import org.w3c.dom.HTMLSelectElement
import org.w3c.dom.HTMLTextAreaElement
import org.w3c.xhr.FormData
import org.w3c.xhr.XMLHttpRequest
import react.*
import react.dom.defaultValue
import styled.*
import kotlin.js.Date

external interface ItemListElementState : RState {
    var detailsOpened: Boolean
    var availabilityLoaded: Boolean

    var storageInput: String
    var untilInput: Date?

    var titleInput: String
    var authorsInput: String
    var typeInput: String
    var isbnInput: String
    var rlbcInput: String
    var languageInput: String
    var detailsInput: String
}

external interface ItemListElementProps : RProps {
    var item: Items
    var editing: Boolean
    var locations: List<ItemLocation>

    var changeEditing: (Boolean) -> Unit
    var renting: Boolean
    var changeRenting: (Boolean) -> Unit
    var inProcess: Boolean
    var changeInProcess: (Boolean) -> Unit
    var fetchAddress: (String) -> String?
    var update: () -> Unit
}

@JsExport
class ItemListElement : RComponent<ItemListElementProps, ItemListElementState>() {
    override fun componentDidMount() {
        setState {
            titleInput = props.item.title
            authorsInput = props.item.authors
            typeInput = props.item.type
            isbnInput = props.item.isbn
            rlbcInput = props.item.rlbc
            languageInput = props.item.language
            detailsInput = props.item.details
            storageInput = ""
            untilInput = null
        }
    }

    override fun RBuilder.render() {
        styledDiv {
            css {
                borderBottomStyle = BorderStyle.solid
                borderColor = Color("#777777")
                children {
                    borderColor = Color("#777777")
                }
                p {
                    margin(0.px)
                }
                button {
                    width = 100.px
                    height = 50.px
                }
            }
            if (!props.editing) {
                styledB {
                    css {
                        fontSize = 16.px
                    }
                    +props.item.title
                    styledBr { }
                }
                styledB {
                    css {
                        fontSize = 15.px
                    }
                    +props.item.authors
                    styledBr { }
                }
                +"id: ${props.item.id_item}"
                styledBr { }
                +"Тип: ${props.item.type}"
                styledBr { }
                +"Язык: ${props.item.language}"
                styledBr { }
                +"ISBN: ${props.item.isbn}"
                styledBr { }
                +"ББК: ${props.item.rlbc}"
                styledBr { }
                styledP {
                    css {
                        userSelect = UserSelect.none
                        borderTopStyle = BorderStyle.solid
                        borderBottomStyle = BorderStyle.solid
                    }
                    attrs {
                        onClickFunction = {
                            setState { detailsOpened = !detailsOpened }
                        }
                    }
                    +"Подробнее ${if (state.detailsOpened) "-" else "+"}"
                }
                styledDiv {
                    css {
                        borderBottomStyle = BorderStyle.solid
                    }
                    attrs {
                        hidden = !state.detailsOpened
                    }
                    styledPre {
                        css {
                            padding(left = LinearDimension("2%"), right = LinearDimension("2%"))
                            maxHeight = 300.px
                            overflow = Overflow.auto
                        }
                        +props.item.details
                    }
                }
                if (props.renting && state.availabilityLoaded) {
                    styledForm {
                        attrs {
                            method = FormMethod.post
                            onSubmitFunction = {
                                it.preventDefault()
                                props.changeInProcess(true)
                                props.changeRenting(false)
                                val xmlHttpRequest = XMLHttpRequest()
                                xmlHttpRequest.open("post", "/rents/insert")
                                xmlHttpRequest.onload = {
                                    if (xmlHttpRequest.status == 200.toShort()) {
                                        window.alert("Книга \"${props.item.title}\" успешно забронирована.")
                                    } else {
                                        window.alert("${xmlHttpRequest.statusText} : ${xmlHttpRequest.responseText}")
                                    }
                                    props.changeInProcess(false)
                                }
                                val data = FormData()
                                data.append("id_user", document.cookie.substringAfter("id=").substringBefore(";"))
                                data.append("id_item", props.item.id_item)
                                data.append("from_date", Date().toISOString())
                                data.append("until_date", state.untilInput!!.toISOString())
                                data.append("id_storage", state.storageInput)
                                xmlHttpRequest.send(data)
                            }
                        }
                        styledP {
                            +"Бронирование книги"
                        }
                        styledDiv {
                            css {
                                display = Display.flex
                                flexDirection = FlexDirection.column
                                alignItems = Align.center
                                width = LinearDimension("100%")
                                borderBottomStyle = BorderStyle.solid
                            }
                            styledTable {
                                css {
                                    borderCollapse = BorderCollapse.collapse
                                    tr {
                                        td {
                                            borderStyle = BorderStyle.solid
                                        }
                                        th {
                                            borderStyle = BorderStyle.solid
                                        }
                                    }
                                    width = LinearDimension("50%")
                                }
                                styledTr {
                                    styledTh { +"Адрес" }
                                    styledTh { +"Доступно" }
                                }
                                for (i in 0 until props.locations.size)
                                    if (props.locations[i].amount.toInt() > 0)
                                        styledTr {
                                            styledTd {
                                                +(props.fetchAddress(props.locations[i].id_storage)
                                                    ?: "Неизвестное хранилище ${props.locations[i].id_storage}")
                                            }
                                            styledTd { +props.locations[i].amount }
                                        }
                            }
                            styledP {
                                styledLabel { +"Хранилище: " }
                                styledSelect {
                                    attrs {
                                        onChangeFunction = {
                                            setState {
                                                storageInput = (it.target as HTMLSelectElement).value
                                            }
                                        }
                                    }
                                    styledOption { attrs { value = "" } }
                                    for (i in 0 until props.locations.size)
                                        if (props.locations[i].amount.toInt() > 0)
                                            styledOption {
                                                attrs { value = props.locations[i].id_storage }
                                                +(props.fetchAddress(props.locations[i].id_storage)
                                                    ?: "Неизвестное хранилище ${props.locations[i].id_storage}")
                                            }
                                }
                            }
                            styledP {
                                styledLabel { +"До: " }
                                styledInput {
                                    attrs {
                                        type = InputType.date
                                        min = Date().toISOString()
                                        onChangeFunction = {
                                            setState {
                                                untilInput = Date((it.target as HTMLInputElement).value)
                                            }
                                        }
                                    }
                                }
                            }
                            styledP {
                                styledInput {
                                    css {
                                        width = LinearDimension.auto
                                        height = 50.px
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
                                    attrs {
                                        value = "Забронировать"
                                        type = InputType.submit
                                        disabled =
                                            props.inProcess || state.untilInput == null || state.storageInput == ""
                                    }
                                }
                                styledButton {
                                    css {
                                        width = LinearDimension.auto
                                    }
                                    attrs {
                                        disabled = props.inProcess
                                        onClickFunction = {
                                            props.changeRenting(false)
                                            setState { availabilityLoaded = false }
                                        }
                                    }
                                    +"Отмена"
                                }
                            }
                        }
                    }
                }
            } else {
                styledP {
                    +"Название: "
                    styledInput {
                        attrs {
                            defaultValue = props.item.title
                            type = InputType.text
                            onChangeFunction = {
                                setState { titleInput = (it.target as HTMLInputElement).value }
                            }
                        }
                    }
                }
                styledP {
                    +"Авторы: "
                    styledInput {
                        attrs {
                            defaultValue = props.item.authors
                            type = InputType.text
                            onChangeFunction = {
                                setState { authorsInput = (it.target as HTMLInputElement).value }
                            }
                        }
                    }
                }
                +"id: ${props.item.id_item}"
                styledP {
                    +"Тип: "
                    styledInput {
                        attrs {
                            defaultValue = props.item.type
                            type = InputType.text
                            onChangeFunction = {
                                setState { typeInput = (it.target as HTMLInputElement).value }
                            }
                        }
                    }
                }
                styledP {
                    +"Язык: "
                    styledInput {
                        attrs {
                            defaultValue = props.item.language
                            type = InputType.text
                            onChangeFunction = {
                                setState { languageInput = (it.target as HTMLInputElement).value }
                            }
                        }
                    }
                }
                styledP {
                    +"ISBN: "
                    styledInput {
                        attrs {
                            defaultValue = props.item.isbn
                            type = InputType.text
                            onChangeFunction = {
                                setState { isbnInput = (it.target as HTMLInputElement).value }
                            }
                        }
                    }
                }
                styledP {
                    +"ББК: "
                    styledInput {
                        attrs {
                            defaultValue = props.item.rlbc
                            type = InputType.text
                            onChangeFunction = {
                                setState { rlbcInput = (it.target as HTMLInputElement).value }
                            }
                        }
                    }
                }
                styledP {
                    +"Подробнее: "
                    styledTextArea {
                        attrs {
                            defaultValue = props.item.details
                            onChangeFunction = {
                                setState { detailsInput = (it.target as HTMLTextAreaElement).value }
                            }
                        }
                    }
                }
            }

            if (document.cookie.contains("role=admin") || (document.cookie.contains("role=user")))
                styledDiv {
                    css {
                        display = Display.flex
                        flexDirection = FlexDirection.row
                        justifyContent = JustifyContent.spaceAround
                    }
                    if (!props.renting)
                        styledButton {
                            attrs {
                                disabled = props.inProcess
                                onClickFunction = {
                                    val getAvailabilityRequest = XMLHttpRequest()
                                    getAvailabilityRequest.open("get", "/itemLocation?id_item=${props.item.id_item}")
                                    getAvailabilityRequest.onload = {
                                        if (getAvailabilityRequest.status == 200.toShort()) {
                                            props.locations = Json.decodeFromString(getAvailabilityRequest.responseText)
                                            setState { availabilityLoaded = true }
                                        } else {
                                            window.alert(
                                                "${getAvailabilityRequest.statusText} :" +
                                                        " ${getAvailabilityRequest.responseText}"
                                            )
                                            props.changeRenting(false)
                                        }
                                    }
                                    getAvailabilityRequest.send()
                                    props.changeEditing(false)
                                    props.changeRenting(true)
                                }
                            }
                            +"Бронь"
                        }
                    if (document.cookie.contains("role=admin")) {
                        styledButton {
                            attrs {
                                disabled = props.inProcess
                                onClickFunction = {
                                    if (window.confirm("Удалить книгу ${props.item.title}? Это действие нельзя будет отменить.")) {
                                        props.changeEditing(false)
                                        props.changeInProcess(true)
                                        val deleteRequest = XMLHttpRequest()
                                        deleteRequest.open("delete", "/items/${props.item.id_item}")
                                        deleteRequest.onload = {
                                            if (deleteRequest.status == 200.toShort()) {
                                                props.update()
                                            } else {
                                                props.changeInProcess(false)
                                                window.alert("Something went wrong!\nReason: ${deleteRequest.responseText}")
                                            }
                                        }
                                        deleteRequest.send()
                                    }
                                }
                            }
                            +"Удалить"
                        }
                        if (!props.editing) {
                            styledButton {
                                attrs {
                                    disabled = props.inProcess
                                    onClickFunction = {
                                        props.changeEditing(true)
                                        props.changeRenting(false)
                                        setState { availabilityLoaded = false }
                                    }
                                }
                                +"Изменить"
                            }
                        } else {
                            styledButton {
                                attrs {
                                    disabled = props.inProcess
                                    onClickFunction = {
                                        props.changeInProcess(true)

                                        val updateRequest = XMLHttpRequest()
                                        updateRequest.open(
                                            "post",
                                            "/items/update/${props.item.id_item}"
                                        )
                                        updateRequest.onload = {
                                            if (updateRequest.status == 200.toShort()) {
                                                props.changeEditing(false)
                                                props.update()
                                            } else {
                                                props.changeInProcess(false)
                                                window.alert("Something went wrong!\nReason: ${updateRequest.responseText}")
                                            }
                                        }
                                        val data = FormData()
                                        if (state.titleInput.isNotBlank())
                                            data.append("title", state.titleInput)
                                        if (state.authorsInput.isNotBlank())
                                            data.append("authors", state.authorsInput)
                                        if (state.detailsInput.isNotBlank())
                                            data.append("details", state.detailsInput)
                                        if (state.isbnInput.isNotBlank())
                                            data.append("isbn", state.isbnInput)
                                        if (state.languageInput.isNotBlank())
                                            data.append("language", state.languageInput)
                                        if (state.typeInput.isNotBlank())
                                            data.append("type", state.typeInput)
                                        if (state.rlbcInput.isNotBlank())
                                            data.append("rlbc", state.rlbcInput)
                                        updateRequest.send(data)
                                    }
                                }
                                +"Подтвердить"
                            }
                            styledButton {
                                attrs {
                                    disabled = props.inProcess
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
    }
}

fun RBuilder.itemListElement(handler: ItemListElementProps.() -> Unit): ReactElement {
    return child(ItemListElement::class) {
        attrs.handler()
    }
}