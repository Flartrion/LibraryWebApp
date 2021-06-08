package adminPageRelated.userManagement

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
import org.w3c.xhr.FormData
import org.w3c.xhr.XMLHttpRequest
import react.*
import styled.*
import kotlin.js.Date

external interface UserAddMenuState : RState {
    var isAddMenuVisible: Boolean

    var roleInput: String
    var fullNameInput: String
    var dobInput: Date?
    var phoneNumberInput: String
    var emailInput: String
    var cardNumInput: String
}

external interface UserAddMenuProps : RProps {
    var changeInProcess: (Boolean) -> Unit
    var update: () -> Unit
}

@JsExport
class UserAddMenu : RComponent<UserAddMenuProps, UserAddMenuState>() {
    override fun componentDidMount() {
        setState {
            roleInput = ""
            fullNameInput = ""
            dobInput = null
            phoneNumberInput = ""
            emailInput = ""
            cardNumInput = ""
        }
    }

    override fun RBuilder.render() {
        styledButton {
            css { margin(top = 14.px) }
            attrs {
                onClickFunction = {
                    setState { isAddMenuVisible = !isAddMenuVisible }
                }
            }
            +"Добавить пользователя"
        }

        styledForm {
            attrs {
                method = FormMethod.post
                onSubmitFunction = {
                    it.preventDefault()
                    setState {
                        props.changeInProcess(true)
                    }
                    val insertRequest = XMLHttpRequest()
                    insertRequest.open("post", "/users/insert")
                    insertRequest.onload = {
                        if (insertRequest.status != 500.toShort()) {
                            props.update()
                        } else {
                            console.error(insertRequest.responseText)
                        }
                    }
                    val data = FormData()
                    if (state.roleInput.isNotBlank())
                        data.append("role", state.roleInput)
                    if (state.fullNameInput.isNotBlank())
                        data.append("full_name", state.fullNameInput)
                    if (state.emailInput.isNotBlank())
                        data.append("email", state.emailInput)
                    if (state.phoneNumberInput.isNotBlank())
                        data.append("phone_number", state.phoneNumberInput)
                    if (state.dobInput != null)
                        data.append("date_of_birth", (state.dobInput)!!.toISOString())
                    if (state.cardNumInput.isNotBlank())
                        data.append("card_num", state.cardNumInput)
                    insertRequest.send(data)
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
                        styledLabel { +"Роль: " }
                    }
                    styledTd {
                        styledInput {
                            attrs {
                                onChangeFunction = {
                                    setState { roleInput = ((it.target) as HTMLInputElement).value }
                                }
                                type = InputType.text
                            }
                        }
                    }
                }

                styledTr {
                    styledTd {
                        styledLabel { +"Полное имя: " }
                    }
                    styledTd {
                        styledInput {
                            attrs {
                                onChangeFunction = {
                                    setState { fullNameInput = ((it.target) as HTMLInputElement).value }
                                }
                                type = InputType.text
                            }
                        }
                    }
                }

                styledTr {
                    styledTd {
                        styledLabel { +"Дата рождения: " }
                    }
                    styledTd {
                        styledInput {
                            attrs {
                                onChangeFunction = {
                                    setState { dobInput = Date(((it.target) as HTMLInputElement).value) }
                                }
                                type = InputType.date
                            }
                        }
                    }
                }

                styledTr {
                    styledTd {
                        styledLabel { +"Номер телефона: " }
                    }
                    styledTd {
                        styledInput {
                            attrs {
                                onChangeFunction = {
                                    setState { phoneNumberInput = ((it.target) as HTMLInputElement).value }
                                }
                                type = InputType.tel
                            }
                        }
                    }
                }

                styledTr {
                    styledTd {
                        styledLabel { +"Электронная почта: " }
                    }
                    styledTd {
                        styledInput {
                            attrs {
                                onChangeFunction = {
                                    setState { emailInput = ((it.target) as HTMLInputElement).value }
                                }
                                type = InputType.text
                            }
                        }
                    }
                }

                styledTr {
                    styledTd {
                        styledLabel { +"Номер билета: " }
                    }
                    styledTd {
                        styledInput {
                            attrs {
                                onChangeFunction = {
                                    setState { cardNumInput = ((it.target) as HTMLInputElement).value }
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

fun RBuilder.userAddMenu(handler: UserAddMenuProps.() -> Unit): ReactElement {
    return child(UserAddMenu::class) {
        attrs.handler()
    }
}