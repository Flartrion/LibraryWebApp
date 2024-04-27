import kotlinx.browser.document
import kotlinx.browser.window
import kotlinx.css.*
import kotlinx.html.*
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

external interface LoginMenuProps : RProps {
    var loginInput: String
    var passwordInput: String
    var onLogin: () -> Unit
    var onExit: () -> Unit
}

@JsExport
class LoginMenu : RComponent<LoginMenuProps, RState>() {
    override fun componentDidMount() {
        props.loginInput = ""
        props.passwordInput = ""
    }

    override fun RBuilder.render() {
        styledDiv {
            css {
                padding(30.px)
                display = Display.flex
                flexDirection = FlexDirection.column
                flexBasis = FlexBasis.maxContent
                justifyContent = JustifyContent.spaceAround
                alignContent = Align.flexStart
                paddingLeft = 10.px
                paddingRight = 10.px
                backgroundColor = Color.darkRed
                width = LinearDimension.fillAvailable
                maxWidth = LinearDimension("10%")
//                height = LinearDimension.maxContent
                label {
                    color = Color.white
                    fontFamily = "Arial"
                }
                input {
                    fontFamily = "Arial"
                    width = LinearDimension.fillAvailable
                }
            }
            styledForm {
                attrs {
                    action = "/login"
                    method = FormMethod.post
                    encType = FormEncType.applicationXWwwFormUrlEncoded
                    onSubmitFunction = {
                        it.preventDefault()
                        val loginRequest = XMLHttpRequest()
                        loginRequest.open("post", "/login")
                        loginRequest.onload = {
                            if (loginRequest.status == 200.toShort()) {
                                val user: User = Json.decodeFromString(loginRequest.responseText)
                                document.cookie = "role=${user.role}"
                                document.cookie = "id=${user.id_user}"
                                props.onLogin()
                            } else {
                                window.alert("${loginRequest.statusText}: ${loginRequest.responseText}")
                            }
                        }
                        val data = FormData()
                        data.append("username", props.loginInput)
                        data.append("password", props.passwordInput)
                        loginRequest.send(data)
                    }
                }
                styledDiv {
                    css {
                        display = Display.flex
                        flexDirection = FlexDirection.column
                        alignContent = Align.stretch
                        justifyContent = JustifyContent.spaceEvenly
                        width = LinearDimension.fillAvailable
                        maxHeight = 150.px
                        height = window.innerHeight.px
                    }

                    if (document.cookie.isNullOrBlank()) {
                        styledInput {
                            attrs {
                                placeholder = "Логин"
                                autoComplete = false
                                name = "username"
                                id = "loginTextArea"
                                onChangeFunction = {
                                    props.loginInput = (it.target as HTMLInputElement).value
                                }
                            }
                        }

                        styledInput {
                            attrs {
                                placeholder = "Пароль"
                                type = InputType.password
                                name = "password"
                                id = "passTextArea"
                                onChangeFunction = {
                                    props.passwordInput = (it.target as HTMLInputElement).value
                                }
                            }
                        }

                        styledInput {
                            css {
                                borderRadius = 0.px
                                borderStyle = BorderStyle.none
                                backgroundColor = Color("#999999")
                                color = Color.white
                                hover {
                                    color = Color("#aa0000")
                                    backgroundColor = Color("#aaaaaa")
                                }
                            }
                            attrs {
                                id = "loginButton"
                                type = InputType.submit
                                value = "Войти"
                            }
                        }
                    } else {
                        styledButton {
                            css {
                                width = LinearDimension.fillAvailable
                            }
                            attrs {
                                onClickFunction = {
                                    document.cookie = "role=; expires = Thu, 01 Jan 1970 00:00:00 UTC"
                                    document.cookie = "id=; expires = Thu, 01 Jan 1970 00:00:00 UTC"
                                    props.onLogin()
                                    props.onExit()
                                }
                            }
                            +"Выход"
                        }
                    }
                }
            }

            styledDiv {
                css {
//                    width = 150.px
//                    height = window.innerHeight.px
//                    maxHeight = 300.px
                    height = LinearDimension("100%")
                }
            }

        }
    }
}

fun RBuilder.loginMenu(handler: LoginMenuProps.() -> Unit): ReactElement {
    return child(LoginMenu::class) {
        attrs.handler()
    }
}