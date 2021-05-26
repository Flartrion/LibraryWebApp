import kotlinx.browser.document
import kotlinx.browser.window
import kotlinx.css.*
import kotlinx.html.js.onClickFunction
import org.w3c.xhr.XMLHttpRequest
import react.*
import styled.css
import styled.styledB
import styled.styledButton
import styled.styledDiv

@JsExport
class AboutPage : RComponent<RProps, RState>() {
    override fun RBuilder.render() {
        styledDiv {
            css {
                textAlign = TextAlign.center
                width = LinearDimension.fillAvailable
                minHeight = (window.outerHeight - 200).px
                height = LinearDimension.maxContent

                padding(30.px)
                backgroundColor = Color("#aaaaaa")
                display = Display.inlineBlock
                fontFamily = "Arial"
            }

        }
    }
}

data class ContactsPageState(val storages: String) : RState

@JsExport
class ContactsPage : RComponent<RProps, ContactsPageState>() {

    override fun componentDidMount() {
        val xml = XMLHttpRequest()
        xml.open("post", "/storages")
        xml.onload = {
            setState(ContactsPageState(xml.responseText))
        }
        xml.send()
    }

    override fun RBuilder.render() {
        styledDiv {
            css {
                textAlign = TextAlign.center
                width = LinearDimension.fillAvailable
                minHeight = (window.outerHeight - 200).px
                height = LinearDimension.maxContent

                padding(30.px)
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
                attrs {
                    onClickFunction = {
                        val xml = XMLHttpRequest()
                        xml.addEventListener("load", {
                            console.log(xml.responseText)
                        })
                        xml.open("post", document.URL + "/post")
                        xml.send()
                    }
                }
                +"Button"
            }
        }
    }
}

@JsExport
class SchedulePage : RComponent<RProps, RState>() {
    override fun RBuilder.render() {
        styledDiv {
            css {
                textAlign = TextAlign.center
                width = LinearDimension.fillAvailable
                minHeight = (window.outerHeight - 200).px
                height = LinearDimension.maxContent

                padding(30.px)
                backgroundColor = Color("#aaaaaa")
                display = Display.inlineBlock
                fontFamily = "Arial"
            }

        }
    }
}

fun RBuilder.aboutPage(): ReactElement {
    return child(AboutPage::class) {

    }
}

fun RBuilder.schedulePage(): ReactElement {
    return child(SchedulePage::class) {

    }
}

fun RBuilder.contactsPage(): ReactElement {
    return child(ContactsPage::class) {

    }
}