import kotlinx.browser.window
import kotlinx.css.*
import kotlinx.css.properties.TextDecoration
import kotlinx.html.js.onClickFunction
import org.w3c.fetch.Request
import org.w3c.xhr.XMLHttpRequest
import react.*
import styled.*

@JsExport
class GeneralBody : RComponent<RProps, RState>() {
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
            styledDiv { +"Aahahaha" }
            styledDiv { +"Aahahaha" }
            styledDiv { +"Aahahaha" }
            styledDiv { +"Aahahaha" }
            styledDiv { +"Aahahaha" }
            styledDiv { +"Aahahaha" }
            styledDiv { +"Aahahaha" }
            styledDiv { +"Aahahaha" }
            styledDiv { +"Aahahaha" }
            styledDiv { +"Aahahaha" }
            styledDiv {
                attrs {
                    onClickFunction = {

                    }
                }
                +"Aahahaha"
            }
        }
    }
}

fun RBuilder.generalBody(): ReactElement {
    return child(GeneralBody::class) {

    }
}