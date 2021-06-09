package adminPageRelated.userManagement

import Users
import kotlinx.browser.window
import kotlinx.css.*
import kotlinx.html.InputType
import kotlinx.html.defaultArea
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
import kotlin.js.Date

external interface UserListElementState : RState {
    var opened: Boolean

    var roleInput: String
    var fullNameInput: String
    var dobInput: Date
    var phoneNumberInput: String
    var emailInput: String
    var cardNumInput: String
}

external interface UserListElementProps : RProps {
    var editState: Boolean
    var processState: Boolean
    var user: Users

    var changeEditing: (Boolean) -> Unit
    var changeInProcess: (Boolean) -> Unit
    var updateFun: () -> Unit
}

@JsExport
class UserListElement : RComponent<UserListElementProps, UserListElementState>() {
    override fun componentDidMount() {
        setState {
            roleInput = props.user.role
            fullNameInput = props.user.full_name
            dobInput = Date(props.user.date_of_birth)
            phoneNumberInput = props.user.phone_number
            emailInput = props.user.email
            cardNumInput = props.user.card_num
        }
    }

    override fun RBuilder.render() {
        styledTr {
            attrs {
                onClickFunction = {
                    if (!props.editState)
                        setState { opened = !opened }
                }
            }
            css {
                cursor = Cursor.pointer
            }
            if (props.editState) {
                styledTd {
                    styledInput {
                        attrs {
                            defaultValue = props.user.card_num
                            onChangeFunction = {
                                setState { cardNumInput = (it.target as HTMLInputElement).value }
                            }
                        }
                    }
                }
                styledTd {
                    styledInput {
                        attrs {
                            defaultValue = props.user.full_name
                            onChangeFunction = {
                                setState { fullNameInput = (it.target as HTMLInputElement).value }
                            }
                        }
                    }
                }
            } else {
                styledTd {
                    styledB {
                        +props.user.card_num
                    }
                }
                styledTd {
                    styledB {
                        +props.user.full_name
                    }
                }
            }
        }

        if (state.opened) {
            styledTr {
                styledTd {
                    +"Роль: "
                }
                styledTd {
                    if (props.editState) {
                        styledInput {
                            attrs {
                                defaultValue = props.user.role
                                onChangeFunction = {
                                    setState { roleInput = (it.target as HTMLInputElement).value }
                                }
                            }
                        }
                    } else
                        +props.user.role
                }
            }
            styledTr {
                styledTd {
                    +"Дата рождения: "
                }
                styledTd {
                    if (props.editState) {
                        styledInput {
                            attrs {
                                defaultValue = props.user.date_of_birth
                                type = InputType.date
                                onChangeFunction = {
                                    setState { dobInput = Date((it.target as HTMLInputElement).value) }
                                }
                            }
                        }
                    } else
                        +props.user.date_of_birth
                }
            }
            styledTr {
                styledTd {
                    +"Телефон: "
                }
                styledTd {
                    if (props.editState) {
                        styledInput {
                            attrs {
                                defaultValue = props.user.phone_number
                                type = InputType.tel
                                onChangeFunction = {
                                    setState { phoneNumberInput = (it.target as HTMLInputElement).value }
                                }
                            }
                        }
                    } else
                        +props.user.phone_number
                }
            }
            styledTr {
                styledTd {
                    +"Адрес электронной почты: "
                }
                styledTd {
                    if (props.editState) {
                        styledInput {
                            attrs {
                                defaultValue = props.user.email
                                onChangeFunction = {
                                    setState { emailInput = (it.target as HTMLInputElement).value }
                                }
                            }
                        }
                    } else
                        +props.user.email
                }
            }
            styledTr {
                styledTd {
                    attrs {
                        colSpan = "2"
                    }
                    styledButton {
                        attrs {
                            disabled = props.processState
                            onClickFunction = {
                                if (window.confirm("Удалить пользователя №${props.user.id_user}? Это действие нельзя будет отменить.")) {
                                    props.changeEditing(false)
                                    props.changeInProcess(true)
                                    val deleteRequest = XMLHttpRequest()
                                    deleteRequest.open("delete", "/users/${props.user.id_user}")
                                    deleteRequest.onload = {
                                        props.updateFun()
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
                                }
                            }
                            +"Изменить"
                        }
                    } else {
                        styledButton {
                            attrs {
                                disabled =
                                    props.processState
                                onClickFunction = {
                                    props.changeInProcess(true)

                                    val updateRequest = XMLHttpRequest()
                                    updateRequest.open(
                                        "post",
                                        "/users/update/${props.user.id_user}"
                                    )
                                    updateRequest.onload = {
                                        if (updateRequest.status == 200.toShort())
                                            props.updateFun()
                                        else {
                                            window.alert(updateRequest.responseText)
                                        }
                                    }
                                    val data = FormData()
                                    data.append("card_num", state.cardNumInput)
                                    data.append("full_name", state.fullNameInput)
                                    data.append("phone_number", state.phoneNumberInput)
                                    data.append("role", state.roleInput)
                                    data.append("email", state.emailInput)
                                    data.append("date_of_birth", state.dobInput.toISOString())
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
    }
}

fun RBuilder.userListElement(handler: UserListElementProps.() -> Unit): ReactElement {
    return child(UserListElement::class) {
        attrs.handler()
    }
}