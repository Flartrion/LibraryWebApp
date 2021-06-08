package itemRelated

import Items
import kotlinx.browser.window
import kotlinx.css.*
import kotlinx.html.InputType
import kotlinx.html.hidden
import kotlinx.html.js.onChangeFunction
import kotlinx.html.js.onClickFunction
import org.w3c.dom.HTMLInputElement
import org.w3c.dom.HTMLTextAreaElement
import org.w3c.xhr.FormData
import org.w3c.xhr.XMLHttpRequest
import react.*
import react.dom.defaultValue
import styled.*

external interface ItemListElementState : RState {
    var detailsOpened: Boolean
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
    var changeEditing: (Boolean) -> Unit
    var inProcess: Boolean
    var changeInProcess: (Boolean) -> Unit
    var update: () -> Unit
}

@JsExport
class ItemListElement : RComponent<ItemListElementProps, ItemListElementState>() {
    override fun componentDidMount() {
        setState {
            detailsOpened = false
            titleInput = props.item.title
            authorsInput = props.item.authors
            typeInput = props.item.type
            isbnInput = props.item.isbn
            rlbcInput = props.item.rlbc
            languageInput = props.item.language
            detailsInput = props.item.details
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
                        borderTopStyle = BorderStyle.solid
                        borderBottomStyle = BorderStyle.solid
                    }
                    attrs {
                        onClickFunction = {
                            setState { detailsOpened = !detailsOpened }
                        }
                    }
                    +"Подробнее: "
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
            styledDiv {
                css {
                    display = Display.flex
                    flexDirection = FlexDirection.row
                    justifyContent = JustifyContent.spaceAround
                }
                styledButton {
                    attrs {
                        disabled = props.inProcess
                    }
                    +"Бронь"
                }
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
                                    props.update()
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
                                    if (updateRequest.status != 500.toShort()) {
                                        props.changeEditing(false)
                                        props.update()
                                    } else {
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

fun RBuilder.itemListElement(handler: ItemListElementProps.() -> Unit): ReactElement {
    return child(ItemListElement::class) {
        attrs.handler()
    }
}