import kotlinx.browser.document
import kotlinx.browser.window
import kotlinx.css.*
import kotlinx.css.properties.TextDecoration
import kotlinx.css.properties.TextDecorationLine
import kotlinx.css.properties.TextDecorationStyle
import kotlinx.html.*
import kotlinx.html.js.onClickFunction
import kotlinx.html.js.onSubmitFunction
import org.w3c.fetch.Request
import react.*
import react.dom.div
import react.dom.head
import react.dom.title
import styled.*

@JsExport
class LoginMenu : RComponent<RProps, RState>() {
    override fun RBuilder.render() {
        styledForm {
            attrs {
                action = "/auth"
                method = FormMethod.post
                encType = FormEncType.applicationXWwwFormUrlEncoded
            }
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
                width = 150.px
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
            styledDiv {
                css {
                    display = Display.flex
                    flexDirection = FlexDirection.column
                    flexBasis = FlexBasis.maxContent
                    justifyContent = JustifyContent.spaceEvenly
                    alignContent = Align.flexStart
                    maxHeight = 150.px
                    height = window.innerHeight.px
                }
                styledDiv {
                    styledLabel {
                        +"Логин:"
                    }
                    styledInput {
                        attrs {
                            name = "login"
                            id = "loginTextArea"
                        }
                    }
                }

                styledDiv {
                    styledLabel {
                        +"Пароль:"
                    }

                    styledInput {
                        attrs {
                            type = InputType.password
                            name = "pass"
                            id = "passTextArea"
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

fun RBuilder.loginMenu(): ReactElement {
    return child(LoginMenu::class) {

    }
}