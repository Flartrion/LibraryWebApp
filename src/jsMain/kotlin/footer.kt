import kotlinx.css.*
import kotlinx.css.properties.TextDecoration
import kotlinx.html.js.onClickFunction
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

                b {
                    padding(top = 30.px, bottom = 30.px, left = 15.px, right = 15.px)
                    borderColor = Color.black
                    textDecoration = TextDecoration.none
                    fontFamily = "Arial"
                    cursor = Cursor.pointer
                    userSelect = UserSelect.none

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

fun RBuilder.footer(handler: HeaderProps.() -> Unit): ReactElement {
    return child(Footer::class) {
        attrs.handler()
    }
}