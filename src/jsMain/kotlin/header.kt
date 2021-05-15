import kotlinx.css.*
import kotlinx.css.properties.TextDecoration
import kotlinx.html.classes
import kotlinx.html.js.onClickFunction
import react.*
import react.dom.img
import styled.*

external interface HeaderProps : RProps {
    var onChangeLocation: (SiteLocation) -> Unit
}

@JsExport
class Header : RComponent<HeaderProps, RState>() {

    override fun RBuilder.render() {
        styledDiv {
            css {
                textAlign = TextAlign.right
                position = Position.relative

//                top = (-16).px
//                left = (-8).px
//                width = LinearDimension.auto + LinearDimension("32px")
                padding(top = 30.px, bottom = 30.px)
                backgroundColor = Color("#999999")
                display = Display.block
                b {
                    padding(top = 30.px, bottom = 30.px, left = 15.px, right = 15.px)
                    borderColor = Color.black
                    textDecoration = TextDecoration.none
                    fontFamily = "Arial"

                    color = Color.black
                    hover {
                        backgroundColor = Color("#aaaaaa")
                        color = Color.darkRed
                    }
                }
            }
            styledB {
                attrs {
                    onClickFunction = {
                        props.onChangeLocation(SiteLocation.Main)
                    }
                }
                +"Главная"
            }
            styledB {
                attrs {
                    onClickFunction = {
                        props.onChangeLocation(SiteLocation.About)
                    }
                }
                +"О библиотеке"
            }
            styledB {
                attrs {
                    onClickFunction = {
                        props.onChangeLocation(SiteLocation.Schedule)
                    }
                }
                +"Читателям"
            }
            styledB {
                attrs {
                    onClickFunction = {
                        props.onChangeLocation(SiteLocation.Contacts)
                    }
                }
                +"Контакты"
            }
        }
    }
}

fun RBuilder.header(handler: HeaderProps.() -> Unit): ReactElement {
    return child(Header::class) {
        attrs.handler()
    }
}