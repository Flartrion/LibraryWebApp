import react.Component
import react.Props
import react.ReactNode
import react.State
import react.dom.html.ReactHTML.div


@JsExport
class AboutPage : Component<Props, State>() {
    override fun render(): ReactNode? {
        div {
            css {
                textAlign = TextAlign.center
                width = LinearDimension.fillAvailable
                minHeight = (window.outerHeight - 200).px
                height = LinearDimension.maxContent

                padding = Padding(30.px)
                backgroundColor = Color("#aaaaaa")
                display = Display.inlineBlock
                fontFamily = "Arial"
            }

        }
    }
}

data class ContactsPageState(val storages: String) : State

@JsExport
class ContactsPage : Component<Props, ContactsPageState>() {

    override fun componentDidMount() {
        val xml = XMLHttpRequest()
        xml.open("get", "/storages")
        xml.onload = {
            setState(ContactsPageState(xml.responseText))
        }
        xml.send()
    }

    override fun render(): ReactNode? {
        div {
            css {
                textAlign = TextAlign.center
                width = LinearDimension.fillAvailable
                minHeight = (window.outerHeight - 200).px
                height = LinearDimension.maxContent

                padding = Padding(30.px)
                backgroundColor = Color("#aaaaaa")
                display = Display.inlineBlock
                fontFamily = "Arial"
            }
            styledDiv {
                styledB {
                    +"Здание A: "
                }
                +"+7999xxxxx"
            }
            styledDiv {
                styledB {
                    +"Здание B: "
                }
                +"+7999xxxxy"
            }
            styledDiv {
                styledB {
                    +"Здание C: "
                }
                +"+7999xxxxz"
            }
//            in-page request example

            styledButton {
                css {
                    borderRadius = 0.px
                }

                onClick = {
                    val xml = XMLHttpRequest()
                    xml.addEventListener("load", {
                        console.log(xml.responseText)
                    })
                    xml.open("post", document.URL + "/post")
                    xml.send()
                }

                +"Button"
            }
        }
    }
}

@JsExport
class SchedulePage : RComponent<Props, State>() {
    override fun RBuilder.render() {
        styledDiv {
            css {
                textAlign = TextAlign.center
                width = LinearDimension.fillAvailable
                minHeight = (window.outerHeight - 200).px
                height = LinearDimension.maxContent

                padding = Padding(30.px)
                backgroundColor = Color("#aaaaaa")
                display = Display.inlineBlock
                fontFamily = "Arial"
            }

        }
    }
}

fun RBuilder.aboutPage(): ReactElement<Props> {
    return child(AboutPage::class) {

    }
}

fun RBuilder.schedulePage(): ReactElement<Props> {
    return child(SchedulePage::class) {

    }
}

fun RBuilder.contactsPage(): ReactElement<Props> {
    return child(ContactsPage::class) {

    }
}