import kotlinx.css.*
import kotlinx.css.properties.TextDecoration
import react.*
import styled.*

@JsExport
class Footer : RComponent<HeaderProps, RState>() {
    override fun RBuilder.render() {
        styledDiv {
            css {
                textAlign = TextAlign.center
                position = Position.relative
                width = LinearDimension.fillAvailable

//                padding(top = 30.px, bottom = 30.px)
                backgroundColor = Color("#999999")
                display = Display.flex
                justifyContent = JustifyContent.spaceAround
                alignContent = Align.center
                a {
                    padding(top = 30.px, bottom = 30.px, left = 15.px, right = 15.px)
                    borderColor = Color.black
                    textDecoration = TextDecoration.none
                    fontFamily = "Arial"

                    color = Color.black
                    link {

                    }
                    visited {

                    }
                    hover {
                        backgroundColor = Color("#aaaaaa")
                        color = Color.darkRed
                    }
                    active {

                    }
                }
            }
            styledA {
                attrs {
                    href = "localhost:8080"
                }
                +"Главная"
            }
            styledA {
                attrs {
                    href = "localhost:8080"
                }
                +"О библиотеке"
            }
            styledA {
                attrs {
                    href = "localhost:8080"
                }
                +"Читателям"
            }
            styledA {
                attrs {
                    href = "localhost:8080"
                }
                +"Контакты"
            }
        }
    }
}

fun RBuilder.footer(handler: HeaderProps.() -> Unit): ReactElement {
    return child(Footer::class) {
        attrs.handler()
    }
}